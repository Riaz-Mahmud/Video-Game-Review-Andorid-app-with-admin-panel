package com.backdoor.vgr.View.Activity;

import static com.backdoor.vgr.View.Activity.MainActivity.perfConfig;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.palette.graphics.Palette;

import com.backdoor.vgr.R;
import com.backdoor.vgr.network.ApiClient;
import com.github.chrisbanes.photoview.PhotoView;
import com.squareup.picasso.Picasso;

public class ImageFullViewActivity extends AppCompatActivity {

    private ImageView fullImageBackBtn;
    private PhotoView fullViewImage;
    private RelativeLayout fullImageViewLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_image_full_view);

        fullImageBackBtn = findViewById(R.id.fullImageBackBtn);
        fullViewImage = findViewById(R.id.fullViewImage);
        fullImageViewLayout = findViewById(R.id.fullImageViewLayout);

        fullImageBackBtn.setOnClickListener(v -> super.onBackPressed());

        if (getIntent().hasExtra(MainActivity.IMAGE_LINK)) {
            String imageLink = getIntent().getStringExtra(MainActivity.IMAGE_LINK);
            if (!imageLink.isEmpty()) {
                setImage(imageLink);
            } else {
                perfConfig.displayToast("Something going wrong!");
                super.onBackPressed();
            }
        } else {
            perfConfig.displayToast("Something going wrong!");
            super.onBackPressed();
        }
    }

    private void setImage(String imageLink) {
        if (checkConnection()) {
            if (imageLink != null) {
                try {
                    Picasso.get().load(ApiClient.getImageUrl(getApplicationContext()) + imageLink)
                            .error(R.drawable.ic_no_image)
                            .into(fullViewImage);
                    updateBGColorPalette();
                } catch (Exception e) {
                    fullViewImage.setBackgroundResource(R.drawable.ic_no_image);
                }
            } else {
                fullViewImage.setBackgroundResource(R.drawable.ic_no_image);
            }
        }
    }

    private void updateBGColorPalette() {
        Bitmap bitmap = ((BitmapDrawable) fullViewImage.getDrawable()).getBitmap();
        Palette.from(bitmap).generate(palette -> {
            assert palette != null;
            fullImageViewLayout.setBackgroundColor(palette.getDominantColor(getResources().getColor(R.color.black)));
        });
    }

    public boolean checkConnection() {
        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        assert manager != null;
        NetworkInfo activeNetwork = manager.getActiveNetworkInfo();
        if (activeNetwork == null) {
            perfConfig.displayToast("No Internet Connection");
            return false;
        } else {
            return true;
        }
    }
}