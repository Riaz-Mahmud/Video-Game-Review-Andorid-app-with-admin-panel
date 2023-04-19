package com.backdoor.vgr.viewmodel;

import static com.backdoor.vgr.View.Activity.MainActivity.perfConfig;

import android.app.Activity;
import android.content.Intent;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


public class DashboardViewModel extends ViewModel {

    Activity activity;
    MutableLiveData<String> fullName = new MutableLiveData<>();

    public MutableLiveData<String> getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName.setValue(fullName);
    }

    public void setActivity(FragmentActivity activity) {
        this.activity = activity;
    }


    public void setUserInfo() {
        setFullName("Welcome " + perfConfig.readUserName());
    }
}
