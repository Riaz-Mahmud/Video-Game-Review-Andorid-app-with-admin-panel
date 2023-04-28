package com.backdoor.vgr.View.Activity.Auth;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.backdoor.vgr.R;
import com.backdoor.vgr.viewmodel.AuthViewModel;

public class SignupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        com.backdoor.vgr.databinding.ActivitySignupBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_signup);
        AuthViewModel viewModel = ViewModelProviders.of(SignupActivity.this).get(AuthViewModel.class);
        viewModel.setActivity(this);
        binding.setAuthViewModel(viewModel);
        binding.setLifecycleOwner(this);
        setContentView(binding.getRoot());
        View view = binding.getRoot();
        viewModel.setView(view);

    }
}