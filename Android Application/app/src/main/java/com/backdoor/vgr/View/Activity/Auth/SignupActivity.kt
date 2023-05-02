package com.backdoor.vgr.View.Activity.Auth

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.backdoor.vgr.R
import com.backdoor.vgr.databinding.ActivitySignupBinding
import com.backdoor.vgr.viewmodel.AuthViewModel

class SignupActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding =
            DataBindingUtil.setContentView<ActivitySignupBinding>(this, R.layout.activity_signup)
        val viewModel = ViewModelProviders.of(this@SignupActivity)[AuthViewModel::class.java]
        viewModel.setActivity(this)
        binding.authViewModel = viewModel
        binding.lifecycleOwner = this
        setContentView(binding.root)
        val view = binding.root
        viewModel.view = view
    }
}