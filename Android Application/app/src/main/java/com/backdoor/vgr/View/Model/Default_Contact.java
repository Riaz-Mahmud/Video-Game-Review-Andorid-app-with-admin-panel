package com.backdoor.vgr.View.Model;


import com.google.gson.annotations.SerializedName;

public class Default_Contact {

    @SerializedName("error")
    private boolean error;

    @SerializedName("message")
    private String message;

    public boolean isError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

}
