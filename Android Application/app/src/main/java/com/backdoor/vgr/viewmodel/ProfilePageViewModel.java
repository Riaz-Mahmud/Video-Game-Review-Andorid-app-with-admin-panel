package com.backdoor.vgr.viewmodel;


import static com.backdoor.vgr.View.Activity.MainActivity.perfConfig;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.backdoor.vgr.View.Activity.Auth.LoginActivity;

public class ProfilePageViewModel extends ViewModel {

    Activity activity;
    View view;

    MutableLiveData<Integer> buttonPress = new MutableLiveData<>();
    MutableLiveData<String> fullName = new MutableLiveData<>();
    MutableLiveData<String> email = new MutableLiveData<>();

    public MutableLiveData<String> getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName.setValue(fullName);
    }

    public MutableLiveData<String> getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email.setValue(email);
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
        setEmail(perfConfig.readEmail());
    }

    public void setActivity(FragmentActivity activity) {
        this.activity = activity;
    }

    public void setView(View view) {
        this.view = view;
    }


}
