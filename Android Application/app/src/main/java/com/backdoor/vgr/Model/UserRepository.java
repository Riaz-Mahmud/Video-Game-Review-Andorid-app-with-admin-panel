package com.backdoor.vgr.Model;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.view.View;

import com.backdoor.vgr.network.ApiClient;
import com.backdoor.vgr.network.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserRepository {
    public static final String BASE_URL_API = "https://lifecell.almahmudriaz.com/api/";
    public static UserRepository userRepository;

//    private final ApiInterface apiInterface;

//    public isCompleteProfileTask isCompleteProfileTask;
//    public isRefreshUserInfoTask isRefreshUserInfoTask;
//    public isChangePasswordCompleteTask isChangePasswordCompleteTask;
//    private ProgressDialog loadingBar;

//    public static UserRepository getInstance(Context context) {
//        if (userRepository == null) {
//            userRepository = new UserRepository(context);
//        }
//        return userRepository;
//    }

//    public UserRepository(isCompleteProfileTask listener, Context context) {
//        apiInterface = ApiClient.getClient(context).create(ApiInterface.class);
//        this.isCompleteProfileTask = listener;
//    }

//    public UserRepository(isRefreshUserInfoTask listener, Context context) {
//        apiInterface = ApiClient.getClient(context).create(ApiInterface.class);
//        this.isRefreshUserInfoTask = listener;
//    }

//    public UserRepository(isChangePasswordCompleteTask listener,Context context) {
//        apiInterface = ApiClient.getClient(context).create(ApiInterface.class);
//        this.isChangePasswordCompleteTask = listener;
//    }

//    public UserRepository(Context context) {
//        apiInterface = ApiClient.getClient(context).create(ApiInterface.class);
//    }

    //FirstTimeLogin Complete Profile
//    public void completeProfile(String email, String totalDonate, String lastDonate, String dateOfBirth ,View view, Activity activity) {
//        LoadingBar(activity);
//
//        apiInterface.completeProfile(IntroActivity.perfConfig.readUserId(), email, totalDonate, lastDonate, dateOfBirth).enqueue(new Callback<Default_Contact>() {
//            @Override
//            public void onResponse(Call<Default_Contact> call, Response<Default_Contact> response) {
//                loadingBar.cancel();
//                if (response.isSuccessful()) {
//                    IntroActivity.perfConfig.displaySnackBar(view, "Complete Profile Successfully");
//                    isCompleteProfileTask.completeProfileServerResponse(response.body());
//                    IntroActivity.perfConfig.writeFirstTimeLogin(0);
//                } else {
//                    IntroActivity.perfConfig.displaySnackBar(view, "Something Going Wrong!");
//                }
//            }
//
//            @Override
//            public void onFailure(Call<Default_Contact> call, Throwable t) {
//                call.cancel();
//            }
//        });
//    }

    //Refresh user data
//    public void refreshUserInfo(View view) {
//        apiInterface.refreshUserInfo(MainActivity.perfConfig.readNumber()).enqueue(new Callback<LoginContact>() {
//            @Override
//            public void onResponse(Call<LoginContact> call, Response<LoginContact> response) {
//                if (response.isSuccessful()) {
//                    isRefreshUserInfoTask.refreshUserInfoTaskServerResponse(response.body());
//                } else {
//                    MainActivity.perfConfig.displaySnackBar(view, "Something Going Wrong!");
//                }
//            }
//
//            @Override
//            public void onFailure(Call<LoginContact> call, Throwable t) {
//                call.cancel();
//                MainActivity.perfConfig.displaySnackBar(view, "Reach Max Time! Please try again or check your internet connection!");
//            }
//        });
//    }

    //ChangePassword
//    public void changePassword(View view, Activity activity, String changeOldPass, String changeConNewPass) {
//        Log.d("userRepo", "changePasswordCall");
//
//        LoadingBar(activity);
//
//        apiInterface.changePassword(MainActivity.perfConfig.readUserId(), changeOldPass, changeConNewPass).enqueue(new Callback<Default_Contact>() {
//            @Override
//            public void onResponse(Call<Default_Contact> call, Response<Default_Contact> response) {
//                loadingBar.cancel();
//                if (response.isSuccessful()) {
//                    Log.d("userRepo", "changePasswordCall isSuccessful");
//                    MainActivity.perfConfig.displaySnackBar(view, response.body().getMessage());
//                    isChangePasswordCompleteTask.changePassServerResponse(response.body());
//                } else {
//                    MainActivity.perfConfig.displaySnackBar(view, "Something going wrong");
//                }
//            }
//
//            @Override
//            public void onFailure(Call<Default_Contact> call, Throwable t) {
//                MainActivity.perfConfig.displaySnackBar(view, "Reach Max time [Change Password]! Try again or check your internet connection!");
//                call.cancel();
//                loadingBar.cancel();
//            }
//        });
//    }


//    public interface isCompleteProfileTask {
//        void completeProfileServerResponse(Default_Contact body);
//    }

//    public interface isRefreshUserInfoTask {
//        void refreshUserInfoTaskServerResponse(LoginContact body);
//    }

//    public interface isChangePasswordCompleteTask {
//        void changePassServerResponse(Default_Contact body);
//    }

//    private void LoadingBar(Activity activity) {
//        loadingBar = new ProgressDialog(activity);
//        loadingBar.setMessage("Please Wait a moment");
//        loadingBar.setCanceledOnTouchOutside(false);
//        loadingBar.show();
//    }
}
