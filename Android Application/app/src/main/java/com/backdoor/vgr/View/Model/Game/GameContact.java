package com.backdoor.vgr.View.Model.Game;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GameContact {

    @SerializedName("success")
    private boolean success;

    @SerializedName("error")
    private String error;

    @SerializedName("result")
    private List<GameDetailsContact> gameDetailsContactList;

    public boolean isSuccess() {
        return success;
    }

    public String getError() {
        return error;
    }

    public List<GameDetailsContact> getGameDetailsContactList() {
        return gameDetailsContactList;
    }
}
