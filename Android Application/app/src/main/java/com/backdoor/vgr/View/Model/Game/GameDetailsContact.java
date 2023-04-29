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

    public void setId(String id) {
        Id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public void setRating_count(String rating_count) {
        this.rating_count = rating_count;
    }

    public void setIs_mine_review(boolean is_mine_review) {
        this.is_mine_review = is_mine_review;
    }

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

    public void setGameReviewsList(List<GameReviews> gameReviewsList) {
        this.gameReviewsList = gameReviewsList;
    }
}
