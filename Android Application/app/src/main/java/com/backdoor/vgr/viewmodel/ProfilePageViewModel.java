package com.backdoor.vgr.viewmodel;


import static com.backdoor.vgr.View.Activity.MainActivity.perfConfig;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.backdoor.vgr.View.Activity.Auth.LoginActivity;

import java.util.Calendar;
import java.util.Date;

public class ProfilePageViewModel extends ViewModel {

    Activity activity;
    View view;

    MutableLiveData<Integer> buttonPress = new MutableLiveData<>();
    MutableLiveData<String> fullName = new MutableLiveData<>();
    MutableLiveData<String> phoneNumber = new MutableLiveData<>();
    MutableLiveData<String> email = new MutableLiveData<>();
    MutableLiveData<String> dateOfBirth = new MutableLiveData<>();


    public MutableLiveData<String> getPhoneNumber() {
        return phoneNumber;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth.setValue(dateOfBirth);
    }

    public MutableLiveData<String> getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName.setValue(fullName);
    }

    public void onTopBtnPress(Integer value) {
        buttonPress.setValue(value);
        Intent intent;

        if (value == 3) {
            intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            String Body = "Download Video Game Review App";
            String Sub = "Download Video Game Review App." + " \n";
            intent.putExtra(Intent.EXTRA_SUBJECT, Body);
            intent.putExtra(Intent.EXTRA_TEXT, Sub);
            activity.startActivity(Intent.createChooser(intent, "Share using"));
        }
        if (value == 4) {
            if (activity != null) {
                if (perfConfig.ClearSharedPreferences()) {
                    intent = new Intent(activity, LoginActivity.class);
                    activity.startActivity(intent);
                    activity.finish();
                }
            }
        }
    }


    public void setUserInfo() {
        setFullName(perfConfig.readUserName());
    }

    public MutableLiveData<Integer> getButtonTask() {
        if (buttonPress == null) {
            buttonPress = new MutableLiveData<>();
        }
        return buttonPress;
    }

    public void setActivity(FragmentActivity activity) {
        this.activity = activity;
    }

    public void setView(View view) {
        this.view = view;
    }

    public boolean checkConnection() {
        ConnectivityManager manager = (ConnectivityManager) activity.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        assert manager != null;
        NetworkInfo activeNetwork = manager.getActiveNetworkInfo();
        if (activeNetwork == null) {
            perfConfig.displaySnackBar(view, "No Internet Connection");
            return false;

        } else {
            return true;
        }
    }

    public void onBackBtnPress() {
        activity.onBackPressed();
    }

}
