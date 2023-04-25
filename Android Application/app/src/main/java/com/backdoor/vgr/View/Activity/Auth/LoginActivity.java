package com.backdoor.vgr.View.Activity.Auth;

import static maes.tech.intentanim.CustomIntent.customType;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.backdoor.vgr.R;
import com.backdoor.vgr.View.Activity.IntroActivity;
import com.backdoor.vgr.View.Activity.MainActivity;
import com.backdoor.vgr.View.Model.LoginSignup.LoginContact;
import com.backdoor.vgr.View.Model.PerfConfig;
import com.backdoor.vgr.databinding.ActivityLoginBinding;
import com.backdoor.vgr.network.ApiClient;
import com.backdoor.vgr.network.ApiInterface;
import com.backdoor.vgr.viewmodel.AuthViewModel;

import net.yslibrary.android.keyboardvisibilityevent.util.UIUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    private PerfConfig perfConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        AuthViewModel viewModel = ViewModelProviders.of(LoginActivity.this).get(AuthViewModel.class);
        viewModel.setActivity(this);
        binding.setAuthViewModel(viewModel);
        binding.setLifecycleOwner(this);
        setContentView(binding.getRoot());

        perfConfig = new PerfConfig(this);

        binding.loginButton.setOnClickListener(v -> {

            String email = binding.usernameTxt.getText().toString().trim();
            String Password = binding.passwordTxt.getText().toString().trim();

            if (checkEmptyField(email, Password)) {
                userLogin(v, email, Password);
            }

        });

        binding.SignUpTxt.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
            startActivity(intent);
            customType(LoginActivity.this, "left-to-right");
        });
    }

    public boolean checkConnection() {
        ConnectivityManager manager = (ConnectivityManager)
                getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        assert manager != null;
        NetworkInfo activeNetwork = manager.getActiveNetworkInfo();
        if (activeNetwork == null) {
            perfConfig.displayToast("No Internet Connection");
            return false;

        } else {
            return true;
        }
    }

    private void userLogin(View v, String email, String password) {
        if (checkConnection()) {
            perfConfig.LoadingBar(LoginActivity.this, "Checking...");
            try {
                UIUtil.hideKeyboard(LoginActivity.this);

                ApiInterface apiService = ApiClient.getClient(getApplicationContext()).create(ApiInterface.class);
                Call<LoginContact> call = apiService.getUserLoginResponse(email, password);
                call.enqueue(new Callback<LoginContact>() {
                    @Override
                    public void onResponse(Call<LoginContact> call, Response<LoginContact> response) {
                        perfConfig.loadingBar.cancel();
                        if (response.isSuccessful()) {
                            if (!response.body().isSuccess()) {
                                perfConfig.displaySnackBar(v, response.body().getError());
                            } else {
                                userResponseSuccessful(response);
                            }
                        } else {
                            perfConfig.displaySnackBar(v, "Something wrong! Please Try again!");
                        }
                    }

                    @Override
                    public void onFailure(Call<LoginContact> call, Throwable t) {
                        Log.d("AdminLoginResponse", String.valueOf(t));
                        perfConfig.displayToast("Server Reach Max Time!\nPlease Check your internet connection and Try again!");
                        perfConfig.loadingBar.cancel();
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    private void userResponseSuccessful(Response<LoginContact> response) {

        perfConfig.displayToast("Login Successful");

        perfConfig.writeLoginStatus(true);
        perfConfig.writeUserId(response.body().getResultContact().getUserContact().getUser_id());
        perfConfig.writeUserName(response.body().getResultContact().getUserContact().getFirst_name() + " " + response.body().getResultContact().getUserContact().getLast_name());
        perfConfig.writeEmail(response.body().getResultContact().getUserContact().getEmail());
        perfConfig.writeAccessToken(response.body().getResultContact().getToken());
        perfConfig.writeTokenType(response.body().getResultContact().getTokenType());
        perfConfig.writeTokenExp(response.body().getResultContact().getExpires_in());

        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
        customType(LoginActivity.this, "left-to-right");
    }

    private boolean checkEmptyField(String email, String password) {
        if (email.isEmpty() || password.isEmpty()) {
            if (password.isEmpty()) {
                binding.passwordTxt.requestFocus();
                UIUtil.showKeyboard(LoginActivity.this, binding.passwordTxt);
            }
            if (email.isEmpty()) {
                binding.usernameTxt.requestFocus();
                UIUtil.showKeyboard(LoginActivity.this, binding.usernameTxt);
            }
            return false;
        } else {
            return true;
        }
    }
}