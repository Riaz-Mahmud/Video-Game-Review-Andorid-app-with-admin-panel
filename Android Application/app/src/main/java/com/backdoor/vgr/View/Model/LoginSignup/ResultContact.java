package com.backdoor.vgr.View.Model.LoginSignup;

import com.backdoor.vgr.View.Model.User.UserContact;
import com.google.gson.annotations.SerializedName;

public class ResultContact {

    @SerializedName("user")
    private UserContact userContact;

    @SerializedName("access_token")
    private String token;

    @SerializedName("token_type")
    private String tokenType;

    @SerializedName("expires_in")
    private String expires_in;

    public UserContact getUserContact() {
        return userContact;
    }

    public String getToken() {
        return token;
    }

    public String getTokenType() {
        return tokenType;
    }

    public String getExpires_in() {
        return expires_in;
    }
}
