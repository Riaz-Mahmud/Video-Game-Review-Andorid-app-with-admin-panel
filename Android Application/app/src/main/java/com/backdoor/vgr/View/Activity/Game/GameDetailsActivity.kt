package com.backdoor.vgr.View.Activity.Game

import android.Manifest
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.text.LineBreaker
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.EditText
import android.widget.RatingBar
import androidx.activity.result.contract.ActivityResultContracts.RequestPermission
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.palette.graphics.Palette
import androidx.recyclerview.widget.GridLayoutManager
import com.backdoor.vgr.R
import com.backdoor.vgr.View.Activity.ImageFullViewActivity
import com.backdoor.vgr.View.Activity.MainActivity
import com.backdoor.vgr.View.Model.Default_Contact
import com.backdoor.vgr.View.Model.Game.GameReviews
import com.backdoor.vgr.View.Model.Game.ReviewAdapter
import com.backdoor.vgr.View.Model.Game.SingleGameContact
import com.backdoor.vgr.databinding.ActivityGameDetailsBinding
import com.backdoor.vgr.viewmodel.GameViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.material.bottomsheet.BottomSheetDialog
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class GameDetailsActivity : AppCompatActivity() {
    var fromBottom: Animation? = null
    private var fromRight: Animation? = null
    private var gameReviewsList: List<GameReviews>? = null
    private var reviewAdapter: ReviewAdapter? = null
    private var fusedLocationClient: FusedLocationProviderClient? = null
    private var lat: String? = null
    private var lon: String? = null
    lateinit var binding: ActivityGameDetailsBinding
    private var viewModel: GameViewModel? = null
    private var gameId: String? = null
    private var imageLink: String? = null

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_game_details)
        viewModel = ViewModelProviders.of(this)[GameViewModel::class.java]
        viewModel!!.activity = this
        binding.gameViewModel = viewModel
        binding.lifecycleOwner = this
        setContentView(binding.root)
        viewModel!!.view = binding.root
        if (intent.hasExtra(MainActivity.GAME_ID)) {
            gameId = intent.getStringExtra(MainActivity.GAME_ID)
            init()
            viewModel!!.getSingleGameDetails(gameId)
            listChange()
        } else {
            MainActivity.perfConfig.displayToast("Something going wrong!! \nPlease try again!")
        }
    }

    private fun listChange() {
        viewModel!!.singleGameContact.observe(this) { singleGameContact: SingleGameContact ->
            gameReviewsList = singleGameContact.gameDetailsContact.gameReviewsList
            if (singleGameContact.isSuccess && gameReviewsList != null && gameReviewsList!!.isNotEmpty()) {
                binding.reviewsRecyclerView.visibility = View.VISIBLE
                binding.noReviewFound.visibility = View.GONE
            } else {
                binding.reviewsRecyclerView.visibility = View.GONE
                binding.noReviewFound.visibility = View.VISIBLE
            }
            reviewAdapter!!.notifyChangeData(gameReviewsList)
            binding.swipeRefreshGameDetails.isRefreshing = false
        }
        viewModel!!.gameImage.observe(this) { value: String? ->
            imageLink = value
            updateBGColorPalette()
        }
        viewModel!!.isMineReview.observe(this) { value: Boolean? ->
            if (!value!!) {
                binding.writeReviewLayout.animation = fromBottom
            }
        }
        viewModel!!.rating.observe(this) {
            binding.ratingGameDetails.animation = fromRight
            binding.ratingCountGameDetails.animation = fromRight
        }
        viewModel!!.deleteSingleReviewContact.observe(this) { data: Default_Contact ->
            if (data.isSuccess) {
                viewModel!!.getSingleGameDetails(gameId)
            }
        }
        viewModel!!.submitReviewContact.observe(this) { data: Default_Contact ->
            if (data.isSuccess) {
                viewModel!!.getSingleGameDetails(gameId)
            }
        }
        binding.gameImageGameDetails.setOnClickListener {
            val intent = Intent(this@GameDetailsActivity, ImageFullViewActivity::class.java)
            if (viewModel!!.checkConnection() && imageLink != null) {
                intent.putExtra(MainActivity.IMAGE_LINK, imageLink)
            } else {
                // Pass the file path through the intent
                intent.putExtra(MainActivity.IMAGE_PATH, filePath)
            }
            startActivity(intent)
        }
    }

    // Get the drawable from the ImageView
    private val filePath: String
        get() {
            // Get the drawable from the ImageView
            val drawable = binding.gameImageGameDetails.drawable

            // Convert the drawable to a Bitmap
            val bitmap = (drawable as BitmapDrawable).bitmap

            // Save the bitmap to a file
            val file = File(cacheDir, "image.png")
            val fos: FileOutputStream
            try {
                fos = FileOutputStream(file)
                bitmap.compress(Bitmap.CompressFormat.PNG, 80, fos)
                fos.flush()
                fos.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }

            // Pass the file path through the intent
            return file.absolutePath
        }

    @RequiresApi(Build.VERSION_CODES.Q)
    private fun init() {
        fromBottom = AnimationUtils.loadAnimation(this@GameDetailsActivity, R.anim.from_bottom)
        fromRight = AnimationUtils.loadAnimation(this@GameDetailsActivity, R.anim.from_right)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            binding.gameDescTxt.justificationMode = LineBreaker.JUSTIFICATION_MODE_INTER_WORD
        }
        recyclerViewInit()
        refresh()
        binding.writeReviewBtn.setOnClickListener {
            fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
            checkLocationPermission()
        }
    }

    private fun refresh() {
        binding.swipeRefreshGameDetails.setOnRefreshListener {
            viewModel!!.getSingleGameDetails(
                gameId
            )
        }
    }

    private fun recyclerViewInit() {
        val gridLayoutManager = GridLayoutManager(this, 1)
        binding.reviewsRecyclerView.layoutManager = gridLayoutManager
        reviewAdapter = ReviewAdapter(
            gameReviewsList,
            this,
            object : ReviewAdapter.OnItemClickListener {
                override fun onItemClick(position: Int) {
                    if (gameReviewsList!![position].is_mine) {
                        deleteReview(gameReviewsList!![position])
                    }
                }
            })
        binding.reviewsRecyclerView.adapter = reviewAdapter
    }

    private fun deleteReview(review: GameReviews) {
        val dialogClickListener =
            DialogInterface.OnClickListener { dialog: DialogInterface, which: Int ->
                when (which) {
                    DialogInterface.BUTTON_POSITIVE -> {
                        //Yes button clicked
                        viewModel!!.deleteSingleReview(review.gameId, review.id)
                        dialog.dismiss()
                    }
                    DialogInterface.BUTTON_NEGATIVE ->                     //No button clicked
                        dialog.dismiss()
                }
            }
        val builder = AlertDialog.Builder(this)
        builder.setMessage("Are you sure?").setPositiveButton("Yes", dialogClickListener)
            .setNegativeButton("No", dialogClickListener).show()
    }

    private fun updateBGColorPalette() {
        try {
            binding.gameImageGameDetails.drawable ?: return
            val bitmap =
                (binding.gameImageGameDetails.drawable as BitmapDrawable).bitmap ?: return
            Palette.from(bitmap).generate { palette: Palette? ->
                val window = this@GameDetailsActivity.window

                // clear FLAG_TRANSLUCENT_STATUS flag:
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)

                // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                assert(palette != null)
                window.statusBarColor =
                    palette!!.getDominantColor(resources.getColor(R.color.white))
            }
        } catch (ignored: Exception) {
        }
    }

    private fun checkLocationPermission() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            resultLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
            return
        }
        fusedLocationClient!!.lastLocation.addOnSuccessListener(this) { location: Location? ->
            if (location != null) {
                lat = location.latitude.toString()
                lon = location.longitude.toString()
                showBottomDialog()
            } else {
                showSettingsAlert()
            }
        }
    }

    private fun showSettingsAlert() {
        val alertDialog = androidx.appcompat.app.AlertDialog.Builder(this@GameDetailsActivity)
        alertDialog.setTitle("GPS is settings")

        // Setting Dialog Message
        alertDialog.setMessage("GPS is not enabled. Do you want to go to settings menu?")

        // On pressing the Settings button.
        alertDialog.setPositiveButton("Settings") { _: DialogInterface?, _: Int ->
            val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
            startActivity(intent)
        }

        // On pressing the cancel button
        alertDialog.setNegativeButton("Cancel") { dialog: DialogInterface, _: Int -> dialog.cancel() }
        alertDialog.show()
    }

    private val resultLauncher =
        registerForActivityResult(RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                showBottomDialog()
            } else {
                MainActivity.perfConfig.displayToast("Location permission not granted")
            }
        }

    private fun showBottomDialog() {
        val bottomSheetDialog = BottomSheetDialog(this@GameDetailsActivity, R.style.DialogStyle)
        bottomSheetDialog.setContentView(R.layout.item_bottom_new_review)
        bottomSheetDialog.setCanceledOnTouchOutside(true)
        val submitReviewBtn = bottomSheetDialog.findViewById<Button>(R.id.submitReviewBtn)
        val messageBox = bottomSheetDialog.findViewById<EditText>(R.id.messageBoxReview)
        val ratingBar = bottomSheetDialog.findViewById<RatingBar>(R.id.ratingForNewReview)
        assert(messageBox != null)
        assert(submitReviewBtn != null)
        assert(ratingBar != null)
        submitReviewBtn!!.setOnClickListener {
            if (messageBox!!.text.toString().isEmpty()) {
                messageBox.error = "Can't Empty"
            } else if (ratingBar!!.rating <= 0.0) {
                MainActivity.perfConfig.displayToast("You don't select rating!")
            } else {
                submitReview(ratingBar.rating, messageBox.text.toString(), bottomSheetDialog)
            }
        }
        bottomSheetDialog.show()
    }

    private fun submitReview(rating: Float, message: String, bottomSheetDialog: BottomSheetDialog) {
        if (lat != null && lon != null && lat!!.isEmpty() && lon!!.isEmpty()) {
            MainActivity.perfConfig.displayToast("Location not working!")
            return
        }
        viewModel!!.submitUserReview(gameId, rating, message, lat, lon)
        bottomSheetDialog.cancel()
    }
}