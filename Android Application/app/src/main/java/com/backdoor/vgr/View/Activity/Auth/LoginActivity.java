package com.backdoor.vgr.View.Activity.Auth;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.backdoor.vgr.R;
import com.backdoor.vgr.View.Model.PerfConfig;
import com.backdoor.vgr.viewmodel.AuthViewModel;

public class LoginActivity extends AppCompatActivity {

    private PerfConfig perfConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        com.backdoor.vgr.databinding.ActivityLoginBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        AuthViewModel viewModel = ViewModelProviders.of(LoginActivity.this).get(AuthViewModel.class);
        viewModel.setActivity(this);
        binding.setAuthViewModel(viewModel);
        binding.setLifecycleOwner(this);
        setContentView(binding.getRoot());
        viewModel.setView(binding.getRoot());

        perfConfig = new PerfConfig(this);
    }
}