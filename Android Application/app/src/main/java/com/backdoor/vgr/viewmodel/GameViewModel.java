package com.backdoor.vgr.viewmodel;

import android.app.Activity;

import androidx.lifecycle.ViewModel;

public class GameViewModel extends ViewModel {

    Activity activity;


    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }
}
