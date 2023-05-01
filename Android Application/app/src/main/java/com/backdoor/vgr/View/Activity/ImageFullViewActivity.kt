package com.backdoor.vgr.View.Activity

import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.palette.graphics.Palette
import com.backdoor.vgr.R
import com.backdoor.vgr.databinding.ActivityImageFullViewBinding
import com.backdoor.vgr.network.ApiClient
import com.squareup.picasso.Picasso

class ImageFullViewActivity : AppCompatActivity() {

    lateinit var binding: ActivityImageFullViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        binding = DataBindingUtil.setContentView(this, R.layout.activity_image_full_view)
        binding.lifecycleOwner = this
        setContentView(binding.root)

        binding.fullImageBackBtn.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        if (intent.hasExtra(MainActivity.IMAGE_LINK)) {
            val imageLink = intent.getStringExtra(MainActivity.IMAGE_LINK)
            if (imageLink!!.isNotEmpty()) {
                setImage(imageLink)
            } else {
                MainActivity.perfConfig.displayToast("Something going wrong!")
                onBackPressedDispatcher.onBackPressed()
            }
        } else if (intent.hasExtra(MainActivity.IMAGE_PATH)) {
            val imagePath = intent.getStringExtra(MainActivity.IMAGE_PATH)
            setImagePath(imagePath)
        } else {
            MainActivity.perfConfig.displayToast("Something going wrong!")
            onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun setImagePath(imagePath: String?) {
        // Load the image from the file
        val bitmap = BitmapFactory.decodeFile(imagePath)

        // Set the image to an ImageView
        binding.fullViewImage.setImageBitmap(bitmap)
        updateBGColorPalette()
    }

    private fun setImage(imageLink: String?) {
        if (checkConnection()) {
            if (imageLink != null) {
                try {
                    Picasso.get().load(ApiClient.getImageUrl(applicationContext) + imageLink)
                        .error(R.drawable.ic_no_image)
                        .into(binding.fullViewImage)
                    updateBGColorPalette()
                } catch (e: Exception) {
                    binding.fullViewImage.setBackgroundResource(R.drawable.ic_no_image)
                }
            } else {
                binding.fullViewImage.setBackgroundResource(R.drawable.ic_no_image)
            }
        }
    }

    private fun updateBGColorPalette() {
        val bitmap = (binding.fullViewImage.drawable as BitmapDrawable).bitmap
        Palette.from(bitmap).generate { palette: Palette? ->
            assert(palette != null)
            binding.fullImageViewLayout.setBackgroundColor(palette!!.getDominantColor(resources.getColor(R.color.black)))
        }
    }

    private fun checkConnection(): Boolean {
        val manager = (getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager)
        val activeNetwork = manager.activeNetworkInfo
        return if (activeNetwork == null) {
            MainActivity.perfConfig.displayToast("No Internet Connection")
            false
        } else {
            true
        }
    }
}