package com.backdoor.vgr.viewmodel;

//import static com.backdoor.vgr.View.Activity.IntroActivity.perfConfig;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.view.View;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.backdoor.vgr.Model.UserRepository;
import com.backdoor.vgr.View.Activity.MainActivity;

import java.util.Objects;
import java.util.regex.Pattern;

public class AuthViewModel extends ViewModel {

    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    //"(?=.*[0-9])" +     //at least 1 digit
                    //"(?=.*[a-z])" +     //at least 1 lower case
                    //"(?=.*[A-Z])" +     //at least 1 upper case
                    //"(?=.*[@#$%^&+=])"+ //at least 1 special case
                    "(?=\\S+$)" +       //no white spaces
                    ".{4,}" +           //at leaser 6 character
                    "$");

    Activity activity;
    private View view;

    //ChangePassword
    public String changeOldPass = "", changeNewPass = "", changeConNewPass = "";
    MutableLiveData<Integer> changeButtonPress = new MutableLiveData<>();

    public int yesNoBtnClickCom = 0;
    MutableLiveData<String> lastDonate = new MutableLiveData<>();
    MutableLiveData<String> dateOfBirth = new MutableLiveData<>();
    MutableLiveData<Integer> completeProfileBtnPress = new MutableLiveData<>();

    UserRepository userRepository;

    boolean isEmailValid(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    //ChangePassword

    public MutableLiveData<Integer> getChangeButtonTask() {
        if (changeButtonPress == null) {
            changeButtonPress = new MutableLiveData<>();
        }
        return changeButtonPress;
    }

    public void onTapChangePasswordBtn(View view) {
        this.view = view;
        if (checkChangePassEmpty()) {
            if (checkConnection()) {
                changeButtonPress.setValue(2);
                changePassword();
            }
        }
    }

    private void changePassword() {
//        userRepository = UserRepository.getInstance(activity.getApplicationContext());
//        userRepository = new UserRepository((UserRepository.isChangePasswordCompleteTask) body -> changePasswordResponse.setValue(body),activity.getApplicationContext());
//        userRepository.changePassword(view, activity, changeOldPass, changeConNewPass);
    }

    private boolean checkChangePassEmpty() {
//        if (changeOldPass.isEmpty() || changeNewPass.isEmpty() || changeNewPass.length() < 3 || changeConNewPass.isEmpty()) {
//            if (changeOldPass.isEmpty()) {
//                IntroActivity.perfConfig.displaySnackBar(view, "Please Enter Old Password");
//                return false;
//            }
//            if (changeNewPass.isEmpty()) {
//                IntroActivity.perfConfig.displaySnackBar(view, "Please Enter New Password");
//                return false;
//            }
//            if (changeNewPass.length() < 3) {
//                IntroActivity.perfConfig.displaySnackBar(view, "New Password too week! Please Enter at least 4 character!");
//                return false;
//            }
//            IntroActivity.perfConfig.displaySnackBar(view, "Please Enter Confirm New Password");
//            return false;
//        } else if (!PASSWORD_PATTERN.matcher(changeNewPass.trim()).matches()) {
//            IntroActivity.perfConfig.displaySnackBar(view, "New Password too week! Please Enter at least 4 character!");
//            return false;
//        } else if (!changeNewPass.equals(changeConNewPass)) {
//            IntroActivity.perfConfig.displaySnackBar(view, "Confirm password not matched");
//            return false;
//        }
        return true;
    }
    //ChangePassword End


    public void setActivity(FragmentActivity activity) {
        this.activity = activity;
    }

    public boolean checkConnection() {
        ConnectivityManager manager = (ConnectivityManager) activity.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        assert manager != null;
        NetworkInfo activeNetwork = manager.getActiveNetworkInfo();
        if (activeNetwork == null) {
            MainActivity.perfConfig.displayToast("No Internet Connection");
            return false;

        } else {
            return true;
        }
    }

    public void onBackBtnPress() {
        activity.onBackPressed();
    }

}
