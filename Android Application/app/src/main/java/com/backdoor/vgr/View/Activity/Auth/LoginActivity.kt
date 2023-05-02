package com.backdoor.vgr.View.Activity.Auth

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.backdoor.vgr.R
import com.backdoor.vgr.View.Model.PerfConfig
import com.backdoor.vgr.databinding.ActivityLoginBinding
import com.backdoor.vgr.viewmodel.AuthViewModel

class LoginActivity : AppCompatActivity() {
    private var perfConfig: PerfConfig? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding =
            DataBindingUtil.setContentView<ActivityLoginBinding>(this, R.layout.activity_login)
        val viewModel = ViewModelProviders.of(this@LoginActivity)[AuthViewModel::class.java]
        viewModel.setActivity(this)
        binding.authViewModel = viewModel
        binding.lifecycleOwner = this
        setContentView(binding.root)
        viewModel.view = binding.root
        perfConfig = PerfConfig(this)
    }
}