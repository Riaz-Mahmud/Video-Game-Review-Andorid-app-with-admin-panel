package com.backdoor.vgr.View.Model.StatusCheck;

import com.google.gson.annotations.SerializedName;

public class CheckStatusContact {

    @SerializedName("success")
    private boolean success;

    @SerializedName("error")
    private String error;

    public boolean isSuccess() {
        return success;
    }

    public String getError() {
        return error;
    }

}
