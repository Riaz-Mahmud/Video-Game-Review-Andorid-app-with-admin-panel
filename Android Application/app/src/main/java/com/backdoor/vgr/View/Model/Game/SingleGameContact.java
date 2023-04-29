package com.backdoor.vgr.View.Model.Game;

import com.google.gson.annotations.SerializedName;

public class SingleGameContact {

    @SerializedName("success")
    private boolean success;

    @SerializedName("error")
    private String error;

    @SerializedName("result")
    private GameDetailsContact gameDetailsContact;

    public boolean isSuccess() {
        return success;
    }

    public String getError() {
        return error;
    }

    public GameDetailsContact getGameDetailsContact() {
        return gameDetailsContact;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void setError(String error) {
        this.error = error;
    }

    public void setGameDetailsContact(GameDetailsContact gameDetailsContact) {
        this.gameDetailsContact = gameDetailsContact;
    }
}
