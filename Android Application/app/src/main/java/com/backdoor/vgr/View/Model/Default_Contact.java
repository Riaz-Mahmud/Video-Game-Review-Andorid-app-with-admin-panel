package com.backdoor.vgr.View.Model;


import com.google.gson.annotations.SerializedName;

public class Default_Contact {

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
