package com.backdoor.vgr.View.Activity;

import static com.backdoor.vgr.View.Activity.MainActivity.perfConfig;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.text.LineBreaker;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.palette.graphics.Palette;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.backdoor.vgr.R;
import com.backdoor.vgr.View.Model.Default_Contact;
import com.backdoor.vgr.View.Model.Game.GameReviews;
import com.backdoor.vgr.View.Model.Game.ReviewAdapter;
import com.backdoor.vgr.View.Model.Game.SingleGameContact;
import com.backdoor.vgr.network.ApiClient;
import com.backdoor.vgr.network.ApiInterface;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GameDetailsActivity extends AppCompatActivity {

    private String game_id;
    ImageView gameImage, backBtn;
    TextView gameName, gameDesc, ratingCount, txtBtnShowMore, noReviewFound;
    RatingBar rating;
    SwipeRefreshLayout swipeRefresh;
    LinearLayout writeReviewLayout;
    Button writeReviewBtn;
    Animation fromBottom, fromRight;

    private RecyclerView reviewsRecyclerView;
    private List<GameReviews> gameReviewsList;
    private ReviewAdapter reviewAdapter;

    private FusedLocationProviderClient fusedLocationClient;
    private String lat;
    private String lon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_details);

        if (getIntent().hasExtra(MainActivity.GAME_ID)) {
            game_id = getIntent().getStringExtra(MainActivity.GAME_ID);

            init();
            getGameData();

        } else {
            perfConfig.displayToast("Something going wrong! \nPlease try again!");
        }

    }

    public void init() {
        backBtn = findViewById(R.id.backBtn);
        gameImage = findViewById(R.id.gameImageGameDetails);
        gameName = findViewById(R.id.gameNameTxt);
        gameDesc = findViewById(R.id.gameDescTxt);
        ratingCount = findViewById(R.id.ratingCount);
        txtBtnShowMore = findViewById(R.id.txtBtnShowMore);
        rating = findViewById(R.id.rating);
        swipeRefresh = findViewById(R.id.swipeRefreshGameDetails);
        writeReviewLayout = findViewById(R.id.writeReviewLayout);
        writeReviewBtn = findViewById(R.id.writeReviewBtn);
        noReviewFound = findViewById(R.id.noReviewFound);
        fromBottom = AnimationUtils.loadAnimation(GameDetailsActivity.this, R.anim.from_bottom);
        fromRight = AnimationUtils.loadAnimation(GameDetailsActivity.this, R.anim.from_right);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            gameDesc.setJustificationMode(LineBreaker.JUSTIFICATION_MODE_INTER_WORD);
        }

        recyclerViewInit();
        showMoreTextBtn();
        refresh();

        backBtn.setOnClickListener(v -> GameDetailsActivity.super.onBackPressed());

        writeReviewBtn.setOnClickListener(view -> {
            fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
            checkLocationPermission();
        });
    }

    private void refresh() {
        swipeRefresh.setOnRefreshListener(this::getGameData);
    }

    private void recyclerViewInit() {
        reviewsRecyclerView = findViewById(R.id.reviewsRecyclerView);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1);
        reviewsRecyclerView.setLayoutManager(gridLayoutManager);
        reviewAdapter = new ReviewAdapter(gameReviewsList, this);
        reviewsRecyclerView.setAdapter(reviewAdapter);

    }

    private void showMoreTextBtn() {
        txtBtnShowMore.setOnClickListener(v -> {
            if (txtBtnShowMore.getText().toString().equalsIgnoreCase("Show More..")) {
                gameDesc.setMaxLines(Integer.MAX_VALUE);
                txtBtnShowMore.setText("Show less");
            } else {
                gameDesc.setMaxLines(5);
                txtBtnShowMore.setText("Show More..");
            }
        });
    }

    private void getGameData() {
        if (checkConnection()) {
            ApiInterface apiService = ApiClient.getClient(getApplicationContext()).create(ApiInterface.class);
            Call<SingleGameContact> call = apiService.getSingleGameData(game_id);

            call.enqueue(new Callback<SingleGameContact>() {
                @Override
                public void onResponse(Call<SingleGameContact> call, Response<SingleGameContact> response) {
                    if (response.isSuccessful()) {
                        responseSuccessful(response);
                        showReview(response);
                    } else {
                        Log.d("SingleRoom", "not success");
                        perfConfig.displayToast("Something going wrong! Please try again!");
                    }
                    swipeRefresh.setRefreshing(false);
                }

                @Override
                public void onFailure(Call<SingleGameContact> call, Throwable t) {
                    Log.d("SingleRoom", "OnFailure");
                    swipeRefresh.setRefreshing(false);
                }
            });

        }
    }

    private void showReview(Response<SingleGameContact> gameDetailsContact) {
        if (gameDetailsContact.body().isSuccess() && gameDetailsContact.body().getGameDetailsContact().getGameReviewsList().size() > 0) {

            reviewsRecyclerView.setVisibility(View.VISIBLE);
            noReviewFound.setVisibility(View.GONE);

            gameReviewsList = gameDetailsContact.body().getGameDetailsContact().getGameReviewsList();
            reviewAdapter = new ReviewAdapter(gameReviewsList, GameDetailsActivity.this);
            reviewsRecyclerView.setAdapter(reviewAdapter);
        } else {
            reviewsRecyclerView.setVisibility(View.GONE);
            noReviewFound.setVisibility(View.VISIBLE);
        }
    }

    private void responseSuccessful(Response<SingleGameContact> response) {

        if (!response.body().isSuccess()) {
            perfConfig.displayToast(response.body().getError());
            super.onBackPressed();
        }

        swipeRefresh.setVisibility(View.VISIBLE);

        rating.setRating(response.body().getGameDetailsContact().getRating());
        gameName.setText(response.body().getGameDetailsContact().getName());
        gameDesc.setText(response.body().getGameDetailsContact().getDesc());
        ratingCount.setText(response.body().getGameDetailsContact().getRating() + " (" +
                response.body().getGameDetailsContact().getRating_count() + ")");

        rating.setAnimation(fromRight);
        ratingCount.setAnimation(fromRight);

        if (response.body().getGameDetailsContact().getBanner() != null) {
            if (!response.body().getGameDetailsContact().getBanner().equals("")) {
                try {
                    Picasso.get().load(ApiClient.getImageUrl(this) + response.body().getGameDetailsContact().getBanner())
                            .error(R.drawable.no_image_found)
                            .into(gameImage);
                    updateBGColorPalette();
                } catch (Exception e) {
                    gameImage.setBackgroundResource(R.drawable.no_image_found);
                }
            }
        }

        gameImage.setOnClickListener(view -> {
            Intent intent = new Intent(GameDetailsActivity.this, ImageFullViewActivity.class);
            intent.putExtra(MainActivity.IMAGE_LINK, response.body().getGameDetailsContact().getBanner());
            startActivity(intent);
        });

        if (response.body().getGameDetailsContact().is_mine_review()) {
            writeReviewLayout.setVisibility(View.GONE);
        } else {
            writeReviewLayout.setVisibility(View.VISIBLE);
            writeReviewLayout.setAnimation(fromBottom);
        }

    }

    private void updateBGColorPalette() {

        Bitmap bitmap = ((BitmapDrawable) gameImage.getDrawable()).getBitmap();

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

    public boolean checkConnection() {
        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        assert manager != null;
        NetworkInfo activeNetwork = manager.getActiveNetworkInfo();
        return activeNetwork != null;
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

        // Setting Dialog Title
        alertDialog.setTitle("GPS is settings");

        // Setting Dialog Message
        alertDialog.setMessage("GPS is not enabled. Do you want to go to settings menu?");

        // On pressing the Settings button.
        alertDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        });

        // On pressing the cancel button
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        // Showing Alert Message
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
                submitReview(ratingBar.getRating(), messageBox.getText().toString(), bottomSheetDialog, view);
            }

        });

        bottomSheetDialog.show();
    }

    private void submitReview(float rating, String message, BottomSheetDialog bottomSheetDialog, View view) {
        if (lat.isEmpty() && lon.isEmpty()) {
            perfConfig.displayToast("Location not working!");
            return;
        }
        if (checkConnection()) {
            perfConfig.LoadingBar(GameDetailsActivity.this, "Please Wait...");
            ApiInterface apiService = ApiClient.getClient(getApplicationContext()).create(ApiInterface.class);
            Call<Default_Contact> call = apiService.submitNewReview(game_id, rating, message, lat, lon);

            call.enqueue(new Callback<Default_Contact>() {
                @Override
                public void onResponse(Call<Default_Contact> call, Response<Default_Contact> response) {
                    if (response.isSuccessful()) {
                        try {
                            bottomSheetDialog.cancel();
                            perfConfig.loadingBar.dismiss();
                            perfConfig.displaySnackBar(view, response.body().getError());
                            if (response.body().isSuccess()) {
                                getGameData();
                            }
                        } catch (Exception e) {
                            Log.d("SubmitReview", "Error " + e.getMessage());
                        }
                    }
                }

                @Override
                public void onFailure(Call<Default_Contact> call, Throwable t) {
                    Log.d("SubmitReview", "OnFailure" + t.getMessage());
                    perfConfig.loadingBar.dismiss();
                    perfConfig.displayToast("Something went Wrong!");
                }
            });
        }
    }

}