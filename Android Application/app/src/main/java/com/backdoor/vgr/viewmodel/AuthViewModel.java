package com.backdoor.vgr.viewmodel;

//import static com.backdoor.vgr.View.Activity.IntroActivity.perfConfig;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.backdoor.vgr.View.Activity.MainActivity;

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

    boolean isEmailValid(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public void setActivity(FragmentActivity activity) {
        this.activity = activity;
    }

    public void onBackBtnPress() {
        activity.onBackPressed();
    }

}
