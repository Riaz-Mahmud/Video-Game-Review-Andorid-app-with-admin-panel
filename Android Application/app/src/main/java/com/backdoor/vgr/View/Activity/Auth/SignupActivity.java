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
import android.os.Handler;
import android.view.View;

import com.backdoor.vgr.R;
import com.backdoor.vgr.View.Activity.MainActivity;
import com.backdoor.vgr.View.Model.LoginSignup.LoginContact;
import com.backdoor.vgr.View.Model.LoginSignup.SignupContact;
import com.backdoor.vgr.View.Model.PerfConfig;
import com.backdoor.vgr.databinding.ActivitySignupBinding;
import com.backdoor.vgr.network.ApiClient;
import com.backdoor.vgr.network.ApiInterface;
import com.backdoor.vgr.viewmodel.AuthViewModel;

import net.yslibrary.android.keyboardvisibilityevent.util.UIUtil;

import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignupActivity extends AppCompatActivity {

    private ActivitySignupBinding binding;
    private View view;
    private PerfConfig perfConfig;

    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
//                    "(?=.*[0-9])" +     //at least 1 digit
//                    "(?=.*[a-z])" +     //at least 1 lower case
//                    "(?=.*[A-Z])" +     //at least 1 upper case
//                    "(?=.*[@#$%^&+=])"+ //at least 1 special case
                    "(?=\\S+$)" +       //no white spaces
                    ".{5,}" +           //at leaser 5 character
                    "$");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_signup);
        AuthViewModel viewModel = ViewModelProviders.of(SignupActivity.this).get(AuthViewModel.class);
        viewModel.setActivity(this);
        binding.setAuthViewModel(viewModel);
        binding.setLifecycleOwner(this);
        setContentView(binding.getRoot());
        view = binding.getRoot();

        perfConfig = new PerfConfig(this);

        binding.signUpButton.setOnClickListener(v -> {

            String username = binding.usernameTxt.getText().toString().trim();
            String firstName = binding.firstNameTxt.getText().toString().trim();
            String lastName = binding.lastNameTxt.getText().toString().trim();
            String email = binding.emailTxt.getText().toString().trim();
            String password = binding.passwordTxt.getText().toString().trim();
            String rePassword = binding.rePasswordTxt.getText().toString().trim();

            if (checkEmptyField(firstName,lastName , email, username, password, rePassword)) {
                userSignUp(firstName, lastName, email, username, rePassword, v);
            }
        });
    }

    private void userSignUp(String firstName, String lastName, String email, String username, String rePassword, View v) {
        if (checkConnection()) {
            perfConfig.LoadingBar(SignupActivity.this, "Creating...");
            binding.signUpButton.setEnabled(false);
            try {
                ApiInterface apiService = ApiClient.getClient(getApplicationContext()).create(ApiInterface.class);
                Call<SignupContact> call = apiService.getUserSignUpResponse(firstName, lastName,username, email, rePassword );

                call.enqueue(new Callback<SignupContact>() {
                    @Override
                    public void onResponse(Call<SignupContact> call, Response<SignupContact> response) {
                        perfConfig.loadingBar.cancel();
                        if (response.isSuccessful()) {
                            if (!response.body().isSuccess()) {
                                binding.signUpButton.setEnabled(true);
                                perfConfig.displaySnackBarLong(v, response.body().getError());
                                perfConfig.showErrorDialog(SignupActivity.this);
                            } else {
                                resetData();
                                binding.signUpButton.setEnabled(true);
                                perfConfig.displaySnackBar(v, "Signup Successful");
                                perfConfig.showSuccessDialog(SignupActivity.this);
                                userResponseSuccessful(response);
                            }
                        } else {
                            binding.signUpButton.setEnabled(true);
                            perfConfig.displaySnackBar(v, "Something going wrong..");
                        }
                    }

                    @Override
                    public void onFailure(Call<SignupContact> call, Throwable t) {
                        perfConfig.displayToast("Server Time out! Try again!");
                        perfConfig.loadingBar.cancel();
                        binding.signUpButton.setEnabled(true);
                    }
                });

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    private void userResponseSuccessful(Response<SignupContact> response) {

        perfConfig.writeLoginStatus(true);
        perfConfig.writeUserId(response.body().getResultContact().getUserContact().getUser_id());
        perfConfig.writeUserName(response.body().getResultContact().getUserContact().getFirst_name() + " " + response.body().getResultContact().getUserContact().getLast_name());
        perfConfig.writeEmail(response.body().getResultContact().getUserContact().getEmail());
        perfConfig.writeAccessToken(response.body().getResultContact().getToken());
        perfConfig.writeTokenType(response.body().getResultContact().getTokenType());
        perfConfig.writeTokenExp(response.body().getResultContact().getExpires_in());

        Intent intent = new Intent(SignupActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
        customType(SignupActivity.this, "left-to-right");
    }

    private void resetData() {
        binding.usernameTxt.setText("");
        binding.firstNameTxt.setText("");
        binding.lastNameTxt.setText("");
        binding.emailTxt.setText("");
        binding.passwordTxt.setText("");
        binding.rePasswordTxt.setText("");
    }

    private boolean checkEmptyField(String firstName, String lastName, String email, String username, String password, String rePassword) {

        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || username.isEmpty() || password.isEmpty() || rePassword.isEmpty()) {
            if (firstName.isEmpty()) {
                binding.firstNameTxt.setError("Empty!");
                binding.firstNameTxt.requestFocus();
                UIUtil.showKeyboard(SignupActivity.this, binding.firstNameTxt);
            }
            if (lastName.isEmpty()) {
                binding.lastNameTxt.setError("Empty!");
                binding.lastNameTxt.requestFocus();
                UIUtil.showKeyboard(SignupActivity.this, binding.lastNameTxt);
            }
            if (email.isEmpty()) {
                binding.emailTxt.setError("Empty!");
                binding.emailTxt.requestFocus();
                UIUtil.showKeyboard(SignupActivity.this, binding.emailTxt);
            }
            if (username.isEmpty()) {
                binding.usernameTxt.setError("Empty!");
                binding.usernameTxt.requestFocus();
                UIUtil.showKeyboard(SignupActivity.this, binding.usernameTxt);
            }
            if (password.isEmpty()) {
                binding.passwordTxt.setError("Empty!");
                binding.passwordTxt.requestFocus();
                UIUtil.showKeyboard(SignupActivity.this, binding.passwordTxt);
            }
            if (rePassword.isEmpty()) {
                binding.rePasswordTxt.setError("Empty!");
                binding.rePasswordTxt.requestFocus();
                UIUtil.showKeyboard(SignupActivity.this, binding.rePasswordTxt);
            }
            return false;
        } else if (!PASSWORD_PATTERN.matcher(password.trim()).matches()) {
            perfConfig.displaySnackBarLong(view, "Password too week" +
                    "\n*at least 5 character");
            binding.passwordTxt.setError("Password too week" +
                    "\n*at least 5 character");
            binding.passwordTxt.requestFocus();
            UIUtil.showKeyboard(SignupActivity.this, binding.passwordTxt);
            return false;
        } else if (!password.equals(rePassword)) {
            binding.rePasswordTxt.setError("Not Match");
            binding.rePasswordTxt.requestFocus();
            perfConfig.displaySnackBarLong(view, "Confirm Password Not Match");
            UIUtil.showKeyboard(SignupActivity.this, binding.rePasswordTxt);
            return false;
        } else if (!binding.privacyCheckBox.isChecked()) {
            perfConfig.displaySnackBarLong(view, "AGREE with Privacy & Policy");
            return false;
        }
        return true;
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
}