package com.backdoor.vgr.View.Model.Game;

import com.backdoor.vgr.View.Model.User.UserContact;
import com.google.gson.annotations.SerializedName;

public class GameReviews {

    @SerializedName("id")
    private String id;

    @SerializedName("user_id")
    private String userId;

    @SerializedName("game_id")
    private String gameId;

    @SerializedName("rating")
    private float rating;

    @SerializedName("comments")
    private String comment;

    @SerializedName("address")
    private String address;

    @SerializedName("status")
    private String status;

    @SerializedName("is_mine")
    private boolean is_mine;

    @SerializedName("user")
    private UserContact userContact;

    public String getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public String getGameId() {
        return gameId;
    }

    public float getRating() {
        return rating;
    }

    public String getComment() {
        return comment;
    }

    public String getAddress() {
        return address;
    }

    public String getStatus() {
        return status;
    }

    public boolean is_mine() {
        return is_mine;
    }

    public UserContact getUserContact() {
        return userContact;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setIs_mine(boolean is_mine) {
        this.is_mine = is_mine;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setUserContact(UserContact userContact) {
        this.userContact = userContact;
    }
}
