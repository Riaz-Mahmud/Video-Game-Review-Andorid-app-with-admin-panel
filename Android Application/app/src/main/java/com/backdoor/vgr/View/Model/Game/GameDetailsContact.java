package com.backdoor.vgr.View.Model.Game;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GameDetailsContact {

    @SerializedName("id")
    private String Id;

    @SerializedName("name")
    private String name;

    @SerializedName("slug")
    private String slug;

    @SerializedName("description")
    private String desc;

    @SerializedName("banner")
    private String banner;

    @SerializedName("rating")
    private float rating;

    @SerializedName("rating_count")
    private String rating_count;

    @SerializedName("status")
    private String status;

    @SerializedName("is_mine_review")
    private boolean is_mine_review;

    @SerializedName("reviews")
    private List<GameReviews> gameReviewsList;


    public String getId() {
        return Id;
    }

    public String getName() {
        return name;
    }

    public String getSlug() {
        return slug;
    }

    public String getDesc() {
        return desc;
    }

    public String getBanner() {
        return banner;
    }

    public float getRating() {
        return rating;
    }

    public String getRating_count() {
        return rating_count;
    }

    public String getStatus() {
        return status;
    }

    public boolean is_mine_review() {
        return is_mine_review;
    }

    public List<GameReviews> getGameReviewsList() {
        return gameReviewsList;
    }
}
