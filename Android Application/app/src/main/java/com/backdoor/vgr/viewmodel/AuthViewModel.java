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

import com.backdoor.vgr.Model.Repository.DataRepository;
import com.backdoor.vgr.R;
import com.backdoor.vgr.View.Activity.Auth.LoginActivity;
import com.backdoor.vgr.View.Activity.Auth.SignupActivity;
import com.backdoor.vgr.View.Activity.MainActivity;
import com.backdoor.vgr.View.Model.LoginSignup.ResultContact;
import com.backdoor.vgr.View.Model.StatusCheck.CheckStatusContact;

import net.yslibrary.android.keyboardvisibilityevent.util.UIUtil;

import java.util.regex.Pattern;

public class AuthViewModel extends ViewModel {

    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
//                    "(?=.*[0-9])" +     //at least 1 digit
//                    "(?=.*[a-z])" +     //at least 1 lower case
//                    "(?=.*[A-Z])" +     //at least 1 upper case
//                    "(?=.*[@#$%^&+=])"+ //at least 1 special case
                    "(?=\\S+$)" +       //no white spaces
                    ".{5,}" +           //at leaser 5 character
                    "$");

    Activity activity;
    private View view;
    DataRepository dataRepository;

    private CheckStatusContact checkStatusContact;
    MutableLiveData<String> errorMessage = new MutableLiveData<>();
    MutableLiveData<Integer> errorMessageColor = new MutableLiveData<>();
    MutableLiveData<Integer> errorMsgShow = new MutableLiveData<>();
    MutableLiveData<Boolean> signUpBtnEnable = new MutableLiveData<>(true);
    public String email = "", password = "", confirmPass = "";
    public String username = "", firstName = "", lastName = "";
    public boolean privacyCheck = false;

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

    public void onTapSignupBtn() {
        Intent intent = new Intent(activity, SignupActivity.class);
        activity.startActivity(intent);
        customType(activity, "left-to-right");
    }

    public void userLogin() {
        if (checkConnection()) {
            try {
                UIUtil.hideKeyboard(activity);

                perfConfig.displaySnackBarWithProgressBar(view, "Checking...");

                dataRepository = DataRepository.getInstance(activity.getApplicationContext());
                dataRepository = new DataRepository((DataRepository.GetLoginTask) body -> {
                    perfConfig.snackbar.dismiss();
                    if (body != null) {
                        perfConfig.displaySnackBar(view, body.getError());
                        if (body.isSuccess()) {
                            userResponseSuccessful(body.getResultContact());
                            openMainActivity();
                        }
                    } else {
                        perfConfig.displaySnackBar(view, "Something wrong! Please Try again!");
                    }
                }, activity.getApplicationContext());
                dataRepository.userLogin(email, password);
            } catch (Exception e) {
                perfConfig.displaySnackBar(view, "Something wrong! Please Try again!");
            }
        }
    }

    public void userSignup() {
        if (checkConnection()) {
            setSignUpBtnEnable(false);
            try {
                UIUtil.hideKeyboard(activity);

                perfConfig.displaySnackBarWithProgressBar(view, "Creating...");
                dataRepository = DataRepository.getInstance(activity.getApplicationContext());
                dataRepository = new DataRepository((DataRepository.GetSignupTask) body -> {
                    perfConfig.snackbar.dismiss();
                    if (body != null) {
                        perfConfig.displaySnackBar(view, body.getError());
                        if (body.isSuccess()) {
                            userResponseSuccessful(body.getResultContact());
                            resetData();
                            perfConfig.showSuccessDialog(activity);
                            new Handler().postDelayed(this::openMainActivityWithClearAllTree, 2000);
                        } else {
                            perfConfig.showErrorDialog(activity);
                            setSignUpBtnEnable(true);
                        }
                    } else {
                        perfConfig.showErrorDialog(activity);
                        setSignUpBtnEnable(true);
                        perfConfig.displaySnackBar(view, "Something wrong! Please Try again!");
                    }
                }, activity.getApplicationContext());
                dataRepository.userSignup(firstName, lastName, email, username, confirmPass);
            } catch (Exception e) {
                setSignUpBtnEnable(true);
                perfConfig.displaySnackBar(view, "Something wrong! Please Try again!");
                perfConfig.showErrorDialog(activity);
            }
        }
    }

    private void openMainActivityWithClearAllTree() {
        Intent intent = new Intent(activity, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        activity.startActivity(intent);
        activity.finish();
        customType(activity, "left-to-right");
    }

    private void resetData() {
        email = firstName = lastName = password = confirmPass = username = "";
        privacyCheck = false;
    }

    private void userResponseSuccessful(ResultContact body) {
        perfConfig.writeLoginStatus(true);
        perfConfig.writeUserId(body.getUserContact().getUser_id());
        perfConfig.writeUserName(body.getUserContact().getFirst_name() + " " + body.getUserContact().getLast_name());
        perfConfig.writeEmail(body.getUserContact().getEmail());
        perfConfig.writeAccessToken(body.getToken());
        perfConfig.writeTokenType(body.getTokenType());
        perfConfig.writeTokenExp(body.getExpires_in());
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
            new Handler().postDelayed(this::openMainActivity, 2000);
//            if (!isThreadOpen) {
//                isThreadOpen = true;
//                startThread();
//            }
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

    public MutableLiveData<Boolean> getSignUpBtnEnable() {
        return signUpBtnEnable;
    }

    public void setSignUpBtnEnable(Boolean signUpBtnEnable) {
        this.signUpBtnEnable.setValue(signUpBtnEnable);
    }

    public void onTapUserSignupBtn() {
        if (checkSignupEmptyField()) {
            userSignup();
        }
    }

    private boolean checkSignupEmptyField() {

        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || username.isEmpty() || password.isEmpty() || confirmPass.isEmpty()) {
            if (confirmPass.isEmpty()) {
                perfConfig.displaySnackBar(view, "Confirm Password is empty");
            }
            if (password.isEmpty()) {
                perfConfig.displaySnackBar(view, "Password is empty");
            }
            if (email.isEmpty()) {
                perfConfig.displaySnackBar(view, "Email is empty");
            }
            if (lastName.isEmpty()) {
                perfConfig.displaySnackBar(view, "Last Name is empty");
            }
            if (firstName.isEmpty()) {
                perfConfig.displaySnackBar(view, "First Name is empty");
            }
            if (username.isEmpty()) {
                perfConfig.displaySnackBar(view, "Username is empty");
            }
            return false;
        } else if (!PASSWORD_PATTERN.matcher(password.trim()).matches()) {
            perfConfig.displaySnackBarLong(view, "Password too week" +
                    "\n*at least 5 character");
            return false;
        } else if (!password.equals(confirmPass)) {
            perfConfig.displaySnackBarLong(view, "Confirm Password Not Match");
            return false;
        } else if (!privacyCheck) {
            perfConfig.displaySnackBarLong(view, "AGREE with Privacy & Policy");
            return false;
        } else if (!isEmailValid(email)) {
            perfConfig.displaySnackBar(view, "Email is not valid");
            return false;
        }
        return true;
    }


    public void onTapLoginBtn() {
        if (checkEmptyField()) {
            userLogin();
        }
    }

    private boolean checkEmptyField() {
        if (email.isEmpty() || password.isEmpty()) {
            if (password.isEmpty()) {
                perfConfig.displaySnackBar(view, "Password is empty");
            }
            if (email.isEmpty()) {
                perfConfig.displaySnackBar(view, "Email is empty");
            }
            return false;
        }
        return true;
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
            perfConfig.displayToast("No Internet Connection");
            return false;
        } else {
            return true;
        }
    }

}
