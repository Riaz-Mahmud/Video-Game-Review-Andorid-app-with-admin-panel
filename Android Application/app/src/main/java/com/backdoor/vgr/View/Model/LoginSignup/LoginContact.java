package com.backdoor.vgr.View.Model.LoginSignup;

import com.google.gson.annotations.SerializedName;

public class LoginContact {

    @SerializedName("success")
    private boolean success;

    @SerializedName("error")
    private String error;

    @SerializedName("result")
    private ResultContact resultContact;

    public boolean isSuccess() {
        return success;
    }

    public String getError() {
        return error;
    }

    public ResultContact getResultContact() {
        return resultContact;
    }
}
