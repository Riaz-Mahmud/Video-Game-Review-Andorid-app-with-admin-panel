package com.backdoor.vgr.View.Model;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.backdoor.vgr.R;
import com.google.android.material.snackbar.Snackbar;

public class PerfConfig {

    private final SharedPreferences sharedPreferences;
    private final Context context;
    private static final String LIST_KEY = "list_key";
    public ProgressDialog loadingBar;
    public Snackbar snackbar;

    public PerfConfig(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(context.getString(R.string.pref_file), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
    }

    public boolean ClearSharedPreferences() {
        try {
            return sharedPreferences.edit().clear().commit();
        } catch (Exception ex) {
            return false;
        }
    }

    public void writeLoginStatus(boolean status) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(context.getString(R.string.pref_login_status), status);
        editor.apply();
    }

    public boolean readLoginStatus() {
        return sharedPreferences.getBoolean(context.getString(R.string.pref_login_status), false);
    }

    public void writeUserId(String userId) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(context.getString(R.string.pref_user_Id), userId);
        editor.apply();
    }

    public String readUserId() {
        return sharedPreferences.getString(context.getString(R.string.pref_user_Id), "default");
    }

    public void writeUserName(String userName) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(context.getString(R.string.pref_user_name), userName);
        editor.apply();
    }

    public String readUserName() {
        return sharedPreferences.getString(context.getString(R.string.pref_user_name), "default");
    }

    public void writeImage(String image) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(context.getString(R.string.pref_user_image), image);
        editor.apply();
    }

    public String readImage() {
        return sharedPreferences.getString(context.getString(R.string.pref_user_image), "default");
    }

    public void writeEmail(String email) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(context.getString(R.string.pref_user_email), email);
        editor.apply();
    }

    public String readEmail() {
        return sharedPreferences.getString(context.getString(R.string.pref_user_email), "");
    }

    public void writeAccessToken(String name) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(context.getString(R.string.pref_access_token), name);
        editor.apply();
    }

    public String readAccessToken() {
        return sharedPreferences.getString(context.getString(R.string.pref_access_token), null);
    }

    public void writeTokenType(String value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(context.getString(R.string.pref_token_type), value);
        editor.apply();
    }

    public String readTokenType() {
        return sharedPreferences.getString(context.getString(R.string.pref_token_type), "");
    }

    public void writeTokenExp(String gender) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(context.getString(R.string.pref_token_exp), gender);
        editor.apply();
    }

    public String readTokenExp() {
        return sharedPreferences.getString(context.getString(R.string.pref_token_exp), "0");
    }


    public String readAESKey() {
        return sharedPreferences.getString(context.getString(R.string.pref_aes_key), "9Hh6g6z7aofDER77");
    }

    public String readReferralCode() {
        return sharedPreferences.getString(context.getString(R.string.pref_referral_Code), "");
    }

    public void displayToast(String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public void displayToastLong(String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    public void displaySnackBar(View view, String message) {
        try {
            snackbar = Snackbar.make(view, message, Snackbar.LENGTH_SHORT);
            snackbar.setAction("OK", v -> snackbar.dismiss());
            snackbar.show();
        } catch (Exception ex) {
            Log.d("Error", "displaySnackBar Error: " + ex.getMessage());
        }
    }

    public void displaySnackBarLong(View view, String message) {
        try {
            snackbar = Snackbar.make(view, message, Snackbar.LENGTH_LONG);
            snackbar.setAction("OK", v -> snackbar.dismiss());
            snackbar.show();
        } catch (Exception ex) {
            Log.d("Error", "displaySnackBar Error: " + ex.getMessage());
        }
    }

    public void showSuccessDialog(Context context) {
        Dialog dialog = new Dialog(context, R.style.bottomSheetDialogTheme);
        dialog.setContentView(R.layout.item_success);
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();

        new Handler().postDelayed(dialog::dismiss, 2000);
    }

    public void showErrorDialog(Context context) {
        Dialog dialog = new Dialog(context, R.style.bottomSheetDialogTheme);
        dialog.setContentView(R.layout.item_error);
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();

        new Handler().postDelayed(dialog::dismiss, 1500);

    }

    public void LoadingBar(Context context, String message) {
        loadingBar = new ProgressDialog(context);
        loadingBar.setMessage(message);
        loadingBar.setCanceledOnTouchOutside(false);
        loadingBar.show();
    }

    public void displaySnackBarWithProgressBar(View view, String message) {
        try {
            snackbar = Snackbar.make(view, message, Snackbar.LENGTH_SHORT);
            ViewGroup contentLay = (ViewGroup) snackbar.getView().findViewById(com.karumi.dexter.R.id.snackbar_text).getParent();
            ProgressBar item = new ProgressBar(context);
            contentLay.addView(item);
            snackbar.show();
        } catch (Exception ex) {
            Log.d("Error", "displaySnackBarWithProgressBar Error: " + ex.getMessage());
        }
    }

}
