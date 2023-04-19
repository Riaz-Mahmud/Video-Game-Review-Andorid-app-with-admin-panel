package com.backdoor.vgr.viewmodel;


import static com.backdoor.vgr.View.Activity.MainActivity.perfConfig;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.backdoor.vgr.Model.UserRepository;
import com.backdoor.vgr.R;
import com.backdoor.vgr.View.Activity.Auth.LoginActivity;

import java.util.Calendar;
import java.util.Date;

public class ProfilePageViewModel extends ViewModel {

    Activity activity;
    View view;

    MutableLiveData<Integer> buttonPress = new MutableLiveData<>();
    MutableLiveData<String> fullName = new MutableLiveData<>();
    MutableLiveData<String> totalDonate = new MutableLiveData<>();
    MutableLiveData<Boolean> availableForDonate = new MutableLiveData<>();
    MutableLiveData<String> lastDonate = new MutableLiveData<>();
    MutableLiveData<String> lastDonateFormat = new MutableLiveData<>();
    MutableLiveData<Integer> gender = new MutableLiveData<>();
    MutableLiveData<String> genderFormat = new MutableLiveData<>();
    MutableLiveData<String> presentAddress = new MutableLiveData<>();
    MutableLiveData<String> nextDonate = new MutableLiveData<>();
    MutableLiveData<String> phoneNumber = new MutableLiveData<>();
    MutableLiveData<String> email = new MutableLiveData<>();
    MutableLiveData<String> dateOfBirth = new MutableLiveData<>();
    MutableLiveData<String> dateOfBirthFormat = new MutableLiveData<>();
    MutableLiveData<String> bloodGroup = new MutableLiveData<>();
    MutableLiveData<Integer> viewOrEditMood = new MutableLiveData<>();
    MutableLiveData<String> referralCode = new MutableLiveData<>();

    UserRepository userRepository;
//    private LoginContact loginContact;


    public MutableLiveData<Integer> getViewOrEditMood() {
        return viewOrEditMood;
    }

    public void setViewOrEditMood(Integer value) {
        this.viewOrEditMood.setValue(value);
    }

    public void copyReferralCode() {
        if (!perfConfig.readReferralCode().isEmpty()) {
            String text = "Hey, My LifeCell Referral Code is: " + perfConfig.readReferralCode() + " \nDownload LifeCell from the Play Store and create an account with my referral code. https://play.google.com/store/apps/details?id=com.backdoor.lifecell";
            ClipboardManager clipboard = (ClipboardManager) activity.getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText("Referral Code", text);
            clipboard.setPrimaryClip(clip);
            perfConfig.displaySnackBar(view, "Code Copied");
        }
    }

    public void shareReferralCode() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        String Body = "LifeCell Referral Code";
        String Sub = "Hey, My LifeCell Referral Code is: " + perfConfig.readReferralCode() + " \nDownload LifeCell from the Play Store and create an account with my referral code. https://play.google.com/store/apps/details?id=com.backdoor.lifecell";
        intent.putExtra(Intent.EXTRA_SUBJECT, Body);
        intent.putExtra(Intent.EXTRA_TEXT, Sub);
        activity.startActivity(Intent.createChooser(intent, "Share using"));
    }

    public MutableLiveData<Boolean> getAvailableForDonate() {
        return availableForDonate;
    }

    public void setAvailableForDonate(Boolean status) {
        this.availableForDonate.setValue(status);
    }

    public MutableLiveData<String> getTotalDonate() {
        return totalDonate;
    }

    public void setTotalDonate(String totalDonate) {
        this.totalDonate.setValue(totalDonate);
    }

    public MutableLiveData<String> getLastDonate() {
        return lastDonate;
    }

    public void setLastDonate(String lastDonate) {
        this.lastDonate.setValue(lastDonate);
    }

    public MutableLiveData<String> getLastDonateFormat() {
        return lastDonateFormat;
    }

    public void setLastDonateFormat(String lastDonate) {
        this.lastDonateFormat.setValue(lastDonate);
    }

    public MutableLiveData<Integer> getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender.setValue(gender);
    }

    public MutableLiveData<String> getGenderFormat() {
        return genderFormat;
    }

    public void setGenderFormat(String gender) {
        this.genderFormat.setValue(gender);
    }

    public MutableLiveData<String> getPresentAddress() {
        return presentAddress;
    }

    public void setPresentAddress(String value) {
        this.presentAddress.setValue(value);
    }

    public MutableLiveData<String> getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup.setValue(bloodGroup);
    }

    public MutableLiveData<String> getNextDonate() {
        return nextDonate;
    }

    public void setNextDonate(String nextDonate) {
        this.nextDonate.setValue(nextDonate);
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber.setValue(phoneNumber);
    }

    public MutableLiveData<String> getPhoneNumber() {
        return phoneNumber;
    }

    public void setEmail(String email) {
        this.email.setValue(email);
    }

    public MutableLiveData<String> getEmail() {
        return email;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth.setValue(dateOfBirth);
    }

    public MutableLiveData<String> getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirthFormat(String dateOfBirth) {
        this.dateOfBirthFormat.setValue(dateOfBirth);
    }

    public MutableLiveData<String> getDateOfBirthFormat() {
        return dateOfBirthFormat;
    }

    public MutableLiveData<String> getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName.setValue(fullName);
    }

    public MutableLiveData<String> getReferralCode() {
        return referralCode;
    }

    public void setReferralCode(String value) {
        this.referralCode.setValue(value);
    }

    public void onTopBtnPress(Integer value) {
        buttonPress.setValue(value);
        Intent intent;

        if (value == 1) {
            // activity.startActivity(new Intent(activity, ChangePasswordActivity.class));
        }
        if (value == 2) {
            // activity.startActivity(new Intent(activity, ContactActivity.class));
        }
        if (value == 3) {
            intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            String Body = "Download Video Game Review App";
            String Sub = activity.getResources().getString(R.string.moto) + ". " + "Download Video Game Review App." + " \n";
            intent.putExtra(Intent.EXTRA_SUBJECT, Body);
            intent.putExtra(Intent.EXTRA_TEXT, Sub);
            activity.startActivity(Intent.createChooser(intent, "Share using"));
        }
        if (value == 4) {
            if (activity != null) {
                if (perfConfig.ClearSharedPreferences()) {
                    intent = new Intent(activity, LoginActivity.class);
                    activity.startActivity(intent);
                    activity.finish();
                }
            }
        }
    }

//    private void userResponseSuccessful(LoginContact loginContact) {
//        UserDataContact userDataContact = loginContact.getUserDataContact();
//        perfConfig.writeUserName(userDataContact.getFull_name());
//        perfConfig.writeBloodGroup(userDataContact.getBlood_group());
//        perfConfig.writeImage(userDataContact.getImage());
//        perfConfig.writeDOB(userDataContact.getDate_of_birth());
//        perfConfig.writeEmail(userDataContact.getEmail());
//        perfConfig.writeTotalDonate(userDataContact.getTotal_donate());
//        perfConfig.writeLastDonate(userDataContact.getLast_donate());
//        perfConfig.writeFirstTimeLogin(userDataContact.getFirst_time_login());
//        perfConfig.writeAvailableForDonate(userDataContact.getIsAvailable() == 1);
//        perfConfig.writeReferralCode(userDataContact.getRef_code());
//        perfConfig.writeGender(userDataContact.getGender());
//        perfConfig.writeLocation(userDataContact.getLocation());
//        perfConfig.writeProfileCompletionCount(loginContact.getProfileCompletionPoint());
//        perfConfig.writeProfileCompletionMode(loginContact.getProfileCompletionPoint() == 100 ? 0 : 1);
//        perfConfig.writeLocationAddedCount(loginContact.getDonateLocationCount() == 0 ? 1 : 0);
//
//        setUserInfo();
//    }

    public void setUserInfo() {
        setFullName(perfConfig.readUserName());
    }

    public MutableLiveData<Integer> getButtonTask() {
        if (buttonPress == null) {
            buttonPress = new MutableLiveData<>();
        }
        return buttonPress;
    }

    public void setActivity(FragmentActivity activity) {
        this.activity = activity;
    }

    public void setView(View view) {
        this.view = view;
    }

    public boolean checkConnection() {
        ConnectivityManager manager = (ConnectivityManager) activity.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        assert manager != null;
        NetworkInfo activeNetwork = manager.getActiveNetworkInfo();
        if (activeNetwork == null) {
            perfConfig.displaySnackBar(view, "No Internet Connection");
            return false;

        } else {
            return true;
        }
    }

    public void onBackBtnPress() {
        activity.onBackPressed();
    }

    private final DatePickerDialog.OnDateSetListener mDisplayDateListener = (view, year, month, dayOfMonth) -> {
        month = month + 1;
        String date = year + "-" + month + "-" + dayOfMonth;
        setDateOfBirth(date);
    };

    private final DatePickerDialog.OnDateSetListener mLastDonateDateListener = (view, year, month, dayOfMonth) -> {
        month = month + 1;
        String date = year + "-" + month + "-" + dayOfMonth;
        setLastDonate(date);
    };

    public void selectDateOfBirth(int type) {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dialog = null;
        if (type == 1) {
            dialog = new DatePickerDialog(
                    activity,
                    android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                    mDisplayDateListener,
                    year, month, day);
        } else if (type == 2) {
            dialog = new DatePickerDialog(
                    activity,
                    android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                    mLastDonateDateListener,
                    year, month, day);
        }

        assert dialog != null;
        dialog.getDatePicker().setMaxDate(new Date().getTime());
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }

}
