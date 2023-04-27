package com.backdoor.vgr.View.Activity;

import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.backdoor.vgr.R;
import com.backdoor.vgr.View.Model.PerfConfig;
import com.backdoor.vgr.databinding.ActivityIntroBinding;
import com.backdoor.vgr.viewmodel.AuthViewModel;

public class IntroActivity extends AppCompatActivity {

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

        ActivityIntroBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_intro);
        AuthViewModel viewModel = ViewModelProviders.of(IntroActivity.this).get(AuthViewModel.class);
        viewModel.setActivity(this);
        binding.setAuthViewModel(viewModel);
        binding.setLifecycleOwner(this);
        setContentView(binding.getRoot());
        viewModel.setView(binding.getRoot());

        perfConfig = new PerfConfig(this);

        viewModel.checkAlreadyLoggedIn();

    }
}