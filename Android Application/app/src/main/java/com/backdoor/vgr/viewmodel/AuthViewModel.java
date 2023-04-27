package com.backdoor.vgr.viewmodel;

import static com.backdoor.vgr.View.Activity.IntroActivity.perfConfig;
import static maes.tech.intentanim.CustomIntent.customType;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.view.View;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.backdoor.vgr.Model.DataRepository;
import com.backdoor.vgr.R;
import com.backdoor.vgr.View.Activity.Auth.LoginActivity;
import com.backdoor.vgr.View.Activity.MainActivity;
import com.backdoor.vgr.View.Model.StatusCheck.CheckStatusContact;

public class AuthViewModel extends ViewModel {

    Activity activity;
    private View view;
    DataRepository dataRepository;

    private CheckStatusContact checkStatusContact;
    MutableLiveData<String> errorMessage = new MutableLiveData<>();
    MutableLiveData<Integer> errorMessageColor = new MutableLiveData<>();
    MutableLiveData<Integer> errorMsgShow = new MutableLiveData<>();

    private int tryToConnectCount = 0;
    private boolean isThreadOpen = false;
    private final Handler mainHandler = new Handler();

    public void checkAlreadyLoggedIn() {
        if (perfConfig.readLoginStatus() && perfConfig.readAccessToken() != null) {
            checkUserStatus();
        } else {
            new Handler().postDelayed(this::openLoginActivity, 1500);
        }
    }

    private void openLoginActivity() {
        Intent intent = new Intent(activity, LoginActivity.class);
        activity.startActivity(intent);
        activity.finish();
        customType(activity, "left-to-right");
    }

    private void openMainActivity() {
        Intent intent = new Intent(activity, MainActivity.class);
        activity.startActivity(intent);
        activity.finish();
        customType(activity, "left-to-right");
    }

    private void checkUserStatus() {
        if (checkConnection()) {
            dataRepository = DataRepository.getInstance(activity.getApplicationContext());
            dataRepository = new DataRepository((DataRepository.IsCheckUserStatusTask) response -> {
                if (response.body() != null) {
                    checkStatusContact = (CheckStatusContact) response.body();
                    if (checkStatusContact.isSuccess()) {
                        tryToConnectCount = 5;
                        openMainActivity();
                    } else {
                        errorMessage.setValue(checkStatusContact.getError());
                        perfConfig.writeLoginStatus(false);
                        openLoginActivity();
                    }
                } else {
                    errorMessageColor.setValue(R.color.red);
                    if (response.code() == 401) {
                        errorMessage.setValue("User Unauthenticated!");
                        perfConfig.displayToast("User Unauthenticated!");
                        perfConfig.writeLoginStatus(false);
                        openLoginActivity();
                        return;
                    }
                    reConnectWithServer();
                    errorMessage.setValue("Somethings went wrong!");
                }
            }, activity.getApplicationContext());
            dataRepository.checkUserStatus();
        } else {
            errorMessage.setValue("No Internet Connection");
            errorMessageColor.setValue(R.color.red);
            errorMsgShow.setValue(1);
            if (!isThreadOpen) {
                isThreadOpen = true;
                startThread();
            }
        }
    }

    private void reConnectWithServer() {
        if (tryToConnectCount < 5) {
            if (tryToConnectCount > 1) {
                errorMessageColor.setValue(R.color.fiverBlue);
                errorMessage.setValue("Connecting with the server..");
            }
            checkUserStatus();
            tryToConnectCount++;
        } else {
            errorMessageColor.setValue(R.color.red);
            errorMessage.setValue("Server Not Responding");
            perfConfig.displayToast("Server Not Responding! Field to connect with server!");
            openLoginActivity();
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
                        errorMessage.setValue("Your internet connection was restored!");
                        errorMessageColor.setValue(R.color.fiverBlue);
                        isThreadOpen = false;
                        checkUserStatus();
                    } else {
                        errorMessageColor.setValue(R.color.red);
                        errorMessage.setValue("No Internet Connection");
                        errorMsgShow.setValue(1);
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

    boolean isEmailValid(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public void setActivity(FragmentActivity activity) {
        this.activity = activity;
    }

    public void onBackBtnPress() {
        activity.onBackPressed();
    }

    public MutableLiveData<String> getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(MutableLiveData<String> errorMessage) {
        this.errorMessage = errorMessage;
    }

    public MutableLiveData<Integer> getErrorMsgShow() {
        return errorMsgShow;
    }

    public void setErrorMsgShow(Integer value) {
        this.errorMsgShow.setValue(value);
    }

    public MutableLiveData<Integer> getErrorMessageColor() {
        errorMessageColor.setValue(R.color.black);
        return errorMessageColor;
    }

    public void setErrorMessageColor(Integer errorMessageColor) {
        this.errorMessageColor.setValue(errorMessageColor);
    }

    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }

    public boolean checkConnection() {
        ConnectivityManager manager = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        assert manager != null;
        NetworkInfo activeNetwork = manager.getActiveNetworkInfo();
        if (activeNetwork == null) {
            errorMessage.setValue("No Internet Connection");
            return false;
        } else {
            return true;
        }
    }

}
