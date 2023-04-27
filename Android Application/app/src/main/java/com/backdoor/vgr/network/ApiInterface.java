package com.backdoor.vgr.network;

import com.backdoor.vgr.View.Model.Default_Contact;
import com.backdoor.vgr.View.Model.Game.GameContact;
import com.backdoor.vgr.View.Model.Game.SingleGameContact;
import com.backdoor.vgr.View.Model.LoginSignup.LoginContact;
import com.backdoor.vgr.View.Model.LoginSignup.SignupContact;
import com.backdoor.vgr.View.Model.StatusCheck.CheckStatusContact;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiInterface {

    @Headers({"Accept:application/json"})
    @FormUrlEncoded
    @POST("user/checkStatus")
    Call<CheckStatusContact> checkUserStatus(
            @Field("user_id") String user_id
    );

    @FormUrlEncoded
    @POST("login")
    Call<LoginContact> getUserLoginResponse(
            @Field("email") String email,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("registration")
    Call<SignupContact> getUserSignUpResponse(
            @Field("first_name") String first_name,
            @Field("last_name") String last_name,
            @Field("username") String username,
            @Field("email") String email,
            @Field("password") String password
    );

    @GET("games/list")
    Call<GameContact> getGamesList();

    @GET("games/list/{gameId}")
    Call<SingleGameContact> getSingleGameData(@Path("gameId") String gameId);

    @FormUrlEncoded
    @POST("games/{gameId}/reviews/add")
    Call<Default_Contact> submitNewReview(
            @Path("gameId") String gameId,
            @Field("rating") float rating,
            @Field("message") String message,
            @Field("latitude") String lat,
            @Field("longitude") String lon
    );

    @DELETE("games/{gameId}/reviews/delete/{reviewId}")
    Call<Default_Contact> deleteSingleReview(
            @Path("gameId") String gameId,
            @Path("reviewId") String reviewId
    );

}
