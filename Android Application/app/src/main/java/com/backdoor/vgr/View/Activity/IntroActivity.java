package com.backdoor.vgr.View.Activity;

import static maes.tech.intentanim.CustomIntent.customType;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.backdoor.vgr.R;
import com.backdoor.vgr.View.Activity.Auth.LoginActivity;
import com.backdoor.vgr.View.Model.PerfConfig;
import com.backdoor.vgr.View.Model.StatusCheck.CheckStatusContact;
import com.backdoor.vgr.network.ApiClient;
import com.backdoor.vgr.network.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class IntroActivity extends AppCompatActivity {

    private TextView errorTxt;

    private boolean isThreadOpen = false;
    private final Handler mainHandler = new Handler();

    public static PerfConfig perfConfig;
    public static String PHONE_NUMBER = "phone_number";
    public static String OTHER = "others";
    public static String USER_ID = "user_id";
    public static String TYPE = "type";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        perfConfig = new PerfConfig(this);

        errorTxt = findViewById(R.id.errorTxt);

        if (perfConfig.readLoginStatus() && perfConfig.readAccessToken() != null) {
            checkUserStatus();
        } else {
            new Handler().postDelayed(this::openLoginActivity, 1500);
        }
    }

    private void openLoginActivity() {
        Intent intent = new Intent(IntroActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
        customType(IntroActivity.this, "left-to-right");
    }

    private int tryToConnectCount = 0;

    private void checkUserStatus() {

        if (checkConnection()) {

            ApiInterface apiService = ApiClient.getClient(getApplicationContext()).create(ApiInterface.class);
            Call<CheckStatusContact> call = apiService.checkUserStatus(perfConfig.readUserId());

            call.enqueue(new Callback<CheckStatusContact>() {
                @Override
                public void onResponse(Call<CheckStatusContact> call, Response<CheckStatusContact> response) {
                    if (response.isSuccessful()) {
                        tryToConnectCount = 5;
                        if (response.body().isSuccess()) {
                            Intent intent = new Intent(IntroActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                            customType(IntroActivity.this, "left-to-right");
                        } else {
                            perfConfig.displayToast("Please Contact with Admin");
                            perfConfig.writeLoginStatus(false);
                            openLoginActivity();
                        }
                    } else {
                        reConnectWithServer();
                    }
                }

                @Override
                public void onFailure(Call<CheckStatusContact> call, Throwable t) {
                    call.cancel();
                    reConnectWithServer();
                    Log.d("IntroStatus", "onFailure");
                }
            });
        } else {
            perfConfig.displayToast("No Internet Connection");
            errorTxt.setText("No Internet Connection");
            errorTxt.setVisibility(View.VISIBLE);
            if (!isThreadOpen) {
                isThreadOpen = true;
                startThread();
            }
        }
    }

    void startThread() {
        if (isThreadOpen) {
            CheckInternetConnection checkConnection = new CheckInternetConnection(10);
            new Thread(checkConnection).start();
        }
    }

    class CheckInternetConnection implements Runnable {
        int seconds;

        CheckInternetConnection(int sec) {
            this.seconds = sec;
        }

        @Override
        public void run() {

            while (isThreadOpen) {
                mainHandler.post(() -> {
                    if (checkConnection()) {
                        errorTxt.setText("Your internet connection was restored!");
                        errorTxt.setTextColor(Color.BLUE);
                        isThreadOpen = false;
                        checkUserStatus();
                    } else {
                        errorTxt.setText("No Internet Connection");
                        errorTxt.setVisibility(View.VISIBLE);
                    }
                });

                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    private void reConnectWithServer() {
        if (tryToConnectCount < 5) {
            if (tryToConnectCount > 1) {
                errorTxt.setText("Connecting with the server..");
            }
            checkUserStatus();
            tryToConnectCount++;
        } else {
            errorTxt.setText("Server Not Responding");
            perfConfig.displayToast("Server Not Responding! Field to connect with server!");
            openLoginActivity();
        }
    }

    public boolean checkConnection() {
        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        assert manager != null;
        NetworkInfo activeNetwork = manager.getActiveNetworkInfo();
        return activeNetwork != null;
    }
}