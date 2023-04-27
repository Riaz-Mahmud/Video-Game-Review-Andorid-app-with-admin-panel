package com.backdoor.vgr.View.Activity;

import static com.backdoor.vgr.View.Activity.MainActivity.perfConfig;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.text.LineBreaker;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.palette.graphics.Palette;
import androidx.recyclerview.widget.GridLayoutManager;

import com.backdoor.vgr.R;
import com.backdoor.vgr.View.Model.Game.GameReviews;
import com.backdoor.vgr.View.Model.Game.ReviewAdapter;
import com.backdoor.vgr.databinding.ActivityGameDetailsBinding;
import com.backdoor.vgr.network.ApiClient;
import com.backdoor.vgr.viewmodel.GameViewModel;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.squareup.picasso.Picasso;

import java.util.List;

public class GameDetailsActivity extends AppCompatActivity {

    Animation fromBottom, fromRight;

    private List<GameReviews> gameReviewsList;
    private ReviewAdapter reviewAdapter;

    private FusedLocationProviderClient fusedLocationClient;
    private String lat;
    private String lon;

    ActivityGameDetailsBinding binding;
    private GameViewModel viewModel;
    private String gameId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_game_details);
        viewModel = ViewModelProviders.of(this).get(GameViewModel.class);
        viewModel.setActivity(this);
        binding.setGameViewModel(viewModel);
        binding.setLifecycleOwner(this);
        setContentView(binding.getRoot());
        viewModel.setView(binding.getRoot());

        if (getIntent().hasExtra(MainActivity.GAME_ID)) {
            gameId = getIntent().getStringExtra(MainActivity.GAME_ID);

            init();
            viewModel.getSingleGameDetails(gameId);

            listChange();

        } else {
            perfConfig.displayToast("Something going wrong! \nPlease try again!");
        }

    }


    private void listChange() {
        viewModel.getSingleGameContact().observe(this, singleGameContact -> {
            this.gameReviewsList = singleGameContact.getGameDetailsContact().getGameReviewsList();

            if (singleGameContact.isSuccess() && this.gameReviewsList.size() > 0) {
                binding.reviewsRecyclerView.setVisibility(View.VISIBLE);
                binding.noReviewFound.setVisibility(View.GONE);
            } else {
                binding.reviewsRecyclerView.setVisibility(View.GONE);
                binding.noReviewFound.setVisibility(View.VISIBLE);
            }
            reviewAdapter.notifyChangeData(gameReviewsList);
            binding.swipeRefreshGameDetails.setRefreshing(false);
        });

        viewModel.getGameImage().observe(this, value -> {
            try {
                Picasso.get().load(ApiClient.getImageUrl(this) + value)
                        .error(R.drawable.no_image_found)
                        .into(binding.gameImageGameDetails);
                updateBGColorPalette();

                binding.gameImageGameDetails.setOnClickListener(view -> {
                    Intent intent = new Intent(GameDetailsActivity.this, ImageFullViewActivity.class);
                    intent.putExtra(MainActivity.IMAGE_LINK, value);
                    startActivity(intent);
                });

            } catch (Exception e) {
                binding.gameImageGameDetails.setBackgroundResource(R.drawable.no_image_found);
            }
        });

        viewModel.getIsMineReview().observe(this, value -> {
            if (!value) {
                binding.writeReviewLayout.setAnimation(fromBottom);
            }
        });

        viewModel.getRating().observe(this, value -> {
            binding.ratingGameDetails.setAnimation(fromRight);
            binding.ratingCountGameDetails.setAnimation(fromRight);
        });

        viewModel.getDeleteSingleReviewContact().observe(this, data -> {
            if (data.isSuccess()) {
                viewModel.getSingleGameDetails(gameId);
            }
        });

        viewModel.getSubmitReviewContact().observe(this, data -> {
            if (data.isSuccess()) {
                viewModel.getSingleGameDetails(gameId);
            }
        });
    }

    public void init() {
        fromBottom = AnimationUtils.loadAnimation(GameDetailsActivity.this, R.anim.from_bottom);
        fromRight = AnimationUtils.loadAnimation(GameDetailsActivity.this, R.anim.from_right);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            binding.gameDescTxt.setJustificationMode(LineBreaker.JUSTIFICATION_MODE_INTER_WORD);
        }

        recyclerViewInit();
        showMoreTextBtn();
        refresh();

        binding.backBtnGameDetails.setOnClickListener(v -> GameDetailsActivity.super.onBackPressed());

        binding.writeReviewBtn.setOnClickListener(view -> {
            fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
            checkLocationPermission();
        });
    }

    private void refresh() {
        binding.swipeRefreshGameDetails.setOnRefreshListener(() -> viewModel.getSingleGameDetails(gameId));
    }

    private void recyclerViewInit() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1);
        binding.reviewsRecyclerView.setLayoutManager(gridLayoutManager);
        reviewAdapter = new ReviewAdapter(gameReviewsList, this, position -> {
            if (gameReviewsList.get(position).is_mine()) {
                deleteReview(gameReviewsList.get(position));
            }
        });
        binding.reviewsRecyclerView.setAdapter(reviewAdapter);
    }

    private void deleteReview(GameReviews review) {
        DialogInterface.OnClickListener dialogClickListener = (dialog, which) -> {
            switch (which) {
                case DialogInterface.BUTTON_POSITIVE:
                    //Yes button clicked
                    viewModel.deleteSingleReview(review.getGameId(), review.getId());
                    dialog.dismiss();
                    break;

                case DialogInterface.BUTTON_NEGATIVE:
                    //No button clicked
                    dialog.dismiss();
                    break;
            }
        };

        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        builder.setMessage("Are you sure?").setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();
    }

    private void showMoreTextBtn() {
        binding.txtBtnShowMore.setOnClickListener(v -> {
            if (binding.txtBtnShowMore.getText().toString().equalsIgnoreCase("Show More..")) {
                binding.gameDescTxt.setMaxLines(Integer.MAX_VALUE);
                binding.txtBtnShowMore.setText("Show less");
            } else {
                binding.gameDescTxt.setMaxLines(5);
                binding.txtBtnShowMore.setText("Show More..");
            }
        });
    }

    private void updateBGColorPalette() {

        Bitmap bitmap = ((BitmapDrawable) binding.gameImageGameDetails.getDrawable()).getBitmap();

        Palette.from(bitmap).generate(palette -> {
            Window window = GameDetailsActivity.this.getWindow();

            // clear FLAG_TRANSLUCENT_STATUS flag:
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

            // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

            // finally change the color
            assert palette != null;
            window.setStatusBarColor(palette.getDominantColor(getResources().getColor(R.color.white)));
        });
    }

    private void checkLocationPermission() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            resultLauncher.launch(android.Manifest.permission.ACCESS_FINE_LOCATION);
            return;
        }

        fusedLocationClient.getLastLocation().addOnSuccessListener(this, location -> {
            if (location != null) {
                lat = String.valueOf(location.getLatitude());
                lon = String.valueOf(location.getLongitude());

                showBottomDialog();
            } else {
                showSettingsAlert();
            }
        });
    }

    public void showSettingsAlert() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(GameDetailsActivity.this);

        alertDialog.setTitle("GPS is settings");

        // Setting Dialog Message
        alertDialog.setMessage("GPS is not enabled. Do you want to go to settings menu?");

        // On pressing the Settings button.
        alertDialog.setPositiveButton("Settings", (dialog, which) -> {
            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(intent);
        });

        // On pressing the cancel button
        alertDialog.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

        alertDialog.show();
    }

    private final ActivityResultLauncher<String> resultLauncher = registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
        if (isGranted) {
            showBottomDialog();
        } else {
            perfConfig.displayToast("Location permission not granted");
        }
    });

    private void showBottomDialog() {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(GameDetailsActivity.this, R.style.DialogStyle);
        bottomSheetDialog.setContentView(R.layout.item_bottom_new_review);
        bottomSheetDialog.setCanceledOnTouchOutside(true);

        Button submitReviewBtn = bottomSheetDialog.findViewById(R.id.submitReviewBtn);
        EditText messageBox = bottomSheetDialog.findViewById(R.id.messageBoxReview);
        RatingBar ratingBar = bottomSheetDialog.findViewById(R.id.ratingForNewReview);

        assert messageBox != null;
        assert submitReviewBtn != null;
        assert ratingBar != null;

        submitReviewBtn.setOnClickListener(view -> {
            if (messageBox.getText().toString().isEmpty()) {
                messageBox.setError("Can't Empty");
            } else if (!(ratingBar.getRating() > 0.0)) {
                perfConfig.displayToast("You don't select rating!");
            } else {
                submitReview(ratingBar.getRating(), messageBox.getText().toString(), bottomSheetDialog);
            }

        });

        bottomSheetDialog.show();
    }

    private void submitReview(float rating, String message, BottomSheetDialog bottomSheetDialog) {
        if (lat.isEmpty() && lon.isEmpty()) {
            perfConfig.displayToast("Location not working!");
            return;
        }
        viewModel.submitUserReview(gameId, rating, message, lat, lon);
        bottomSheetDialog.cancel();
    }

}