package com.backdoor.vgr.network;

import com.backdoor.vgr.View.Model.Game.GameContact;
import com.backdoor.vgr.View.Model.Game.GameDetailsContact;
import com.backdoor.vgr.View.Model.Game.SingleGameContact;
import com.backdoor.vgr.View.Model.LoginSignup.LoginContact;
import com.backdoor.vgr.View.Model.LoginSignup.SignupContact;
import com.backdoor.vgr.View.Model.StatusCheck.CheckStatusContact;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiInterface {

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
    Call<GameContact> getUserHistory();

    @GET("games/list/{gameId}")
    Call<SingleGameContact> getSingleGameData(@Path("gameId") String gameId);

//    @FormUrlEncoded
//    @POST("changePassword")
//    Call<Default_Contact> changePassword(
//            @Field("id") String id,
//            @Field("oldPassword") String oldPassword,
//            @Field("newPassword") String newPassword
//    );
//
//    @FormUrlEncoded
//    @POST("resetUserPassword")
//    Call<Default_Contact> resetUserPassword(
//            @Field("id") String id,
//            @Field("number") String oldPassword,
//            @Field("newPassword") String newPassword
//    );
//
//    @FormUrlEncoded
//    @POST("updateAvailableStatus")
//    Call<Default_Contact> updateAvailableStatus(
//            @Field("user_id") String id,
//            @Field("status") int status
//    );
//
//    @FormUrlEncoded
//    @POST("updateUserInfo")
//    Call<Default_Contact> updateUserInfo(
//            @Field("name") String name,
//            @Field("number") String number,
//            @Field("email") String email,
//            @Field("dateOfBirth") String dateOfBirth,
//            @Field("lastDonate") String lastDonate,
//            @Field("bloodGroup") String bloodGroup,
//            @Field("gender") String gender,
//            @Field("presentAddress") String presentAddress
//    );
//
//    @Multipart
//    @POST("updateUserInfoWithImage")
//    Call<Default_Contact> updateUserInfoWithImage(
//            @Part("name") RequestBody name,
//            @Part("number") RequestBody number,
//            @Part("email") RequestBody email,
//            @Part("dateOfBirth") RequestBody dateOfBirth,
//            @Part("lastDonate") RequestBody lastDonate,
//            @Part("bloodGroup") RequestBody bloodGroup,
//            @Part("gender") RequestBody gender,
//            @Part("presentAddress") RequestBody presentAddress,
//            @Part MultipartBody.Part file
//    );
//
//
//    @Multipart
//    @POST("updateUserImage")
//    Call<Default_Contact> uploadImage(
//            @Part("user_id") RequestBody userId,
//            @Part MultipartBody.Part file
//    );
//
//    @GET("stateList")
//    Call<StateContact> getStateList();
//
//    @FormUrlEncoded
//    @POST("donateLocationList/{stateId}")
//    Call<ZoneContact> getDonatedLocationList(
//            @Path("stateId") int stateId,
//            @Field("user_id") String userId
//    );
//
//    @FormUrlEncoded
//    @POST("removeLocation")
//    Call<Default_Contact> removeLocationToUser(
//            @Field("user_id") String userId,
//            @Field("city_id") int cityId
//    );
//
//    @FormUrlEncoded
//    @POST("addLocation")
//    Call<Default_Contact> addLocationToUser(
//            @Field("user_id") String userId,
//            @Field("city_id") int cityId
//    );
//
//    @FormUrlEncoded
//    @POST("firstTimeInfoUpdate")
//    Call<Default_Contact> completeProfile(
//            @Field("user_id") String userId,
//            @Field("email") String email,
//            @Field("totalDonate") String total_donate,
//            @Field("lastDonate") String last_donate,
//            @Field("dateOfBirth") String date_of_birth
//    );
//
//    @GET("slideImage")
//    Call<List<HomePageImageSliderItem>> getAllSliderImage();
//
//    @FormUrlEncoded
//    @POST("findAvailableDonor")
//    Call<DonorSearchContact> getAvailableDonor(
//            @Field("bloodGroup") String bloodGroup,
//            @Field("location") String location,
//            @Field("user_id") String Number
//    );
//

//
//    @FormUrlEncoded
//    @POST("refreshUserInfo")
//    Call<LoginContact> refreshUserInfo(
//            @Field("number") String number
//    );
//
//    @FormUrlEncoded
//    @POST("makeBloodRequest")
//    Call<Default_Contact> getMakeRequestForDonorResponse(
//            @Field("bloodGroup") String BloodGroup,
//            @Field("number") String Number,
//            @Field("location") String Location,
//            @Field("date") String Date,
//            @Field("time") String Time,
//            @Field("unit") String Unit,
//            @Field("patientName") String PatientName,
//            @Field("gender") String Gender,
//            @Field("relation") String Relation,
//            @Field("stateId") String stateId,
//            @Field("user_id") String userId
//    );
//
//    @FormUrlEncoded
//    @POST("readDonorRequest")
//    Call<ReceivedDonorsRequest_Contact> readRequestForDonor(
//            @Field("user_id") String userId,
//            @Field("number") String Number,
//            @Field("bloodGroup") String BloodGroup,
//            @Field("limit") int limit,
//            @Field("page") int page
//    );
//
//    @FormUrlEncoded
//    @POST("makeAcceptBloodRequest")
//    Call<Default_Contact> confirmBloodDonateRqsResponse(
//            @Field("blood_request_id") int bloodRequestId,
//            @Field("user_id") String userId
//    );
//
//
//    @FormUrlEncoded
//    @POST("deleteMyBloodRequest")
//    Call<Default_Contact> deleteMyBloodRqsResponse(
//            @Field("request_id") int requestId,
//            @Field("user_id") String user_id
//    );
//
//    @FormUrlEncoded
//    @POST("markAsDoneMyReq")
//    Call<Default_Contact> markAsDoneMyBloodRqsResponse(
//            @Field("request_id") int requestId,
//            @Field("user_id") String user_id
//    );
//
//    @FormUrlEncoded
//    @POST("myBloodRequest")
//    Call<MyDonorsRequest_Contact> readMyRequestForDonor(
//            @Field("user_id") String Number,
//            @Field("limit") int limit,
//            @Field("page") int page
//    );
//
//    @FormUrlEncoded
//    @POST("requestResponse")
//    Call<UserResponse_Contact> readResponseUserInfo(
//            @Field("user_id") String user_id,
//            @Field("blood_request_id") String blood_request_id
//    );
//
//    @FormUrlEncoded
//    @POST("updateBloodRequest")
//    Call<Default_Contact> updateUserBloodRequest(
//            @Field("id") String id,
//            @Field("bloodGroup") String BloodGroup,
//            @Field("number") String Number,
//            @Field("location") String Location,
//            @Field("date") String Date,
//            @Field("time") String Time,
//            @Field("unit") String Unit,
//            @Field("patientName") String PatientName,
//            @Field("gender") String Gender,
//            @Field("relation") String Relation,
//            @Field("stateId") String StateId
//    );
//
//    @FormUrlEncoded
//    @POST("makeDonatedBloodRequest")
//    Call<Default_Contact> makeDonatedMyBloodRqsResponse(
//            @Field("accept_blood_request_id") int accept_blood_request_id,
//            @Field("code") int Code
//    );
//
//    @FormUrlEncoded
//    @POST("rejectBloodRequest")
//    Call<Default_Contact> cancelAcceptBloodRequest(
//            @Field("blood_request_id") int blood_request_id,
//            @Field("user_id") String user_id,
//            @Field("code") int code
//    );
//
//    @FormUrlEncoded
//    @POST("readUserNotificationCount")
//    Call<NotificationCount> readUserNotificationCount(
//            @Field("user_id") String user_id
//    );
//
//    @FormUrlEncoded
//    @POST("readUserNotification")
//    Call<NotificationContact> readUserNotification(
//            @Field("user_id") String user_id,
//            @Field("limit") int limit,
//            @Field("page") int page
//    );
//
//    @FormUrlEncoded
//    @POST("markAsReadNotification")
//    Call<Default_Contact> markAsReadNotification(
//            @Field("user_id") String user_id
//    );
//
//    @FormUrlEncoded
//    @POST("p7770j3ct/LifeCell/read-notification.php")
//    Call<List<HomeReqNotify_Contact>> readUserHomeDonateNotification(
//            @Field("userId") String userId
//    );
//

//

//    //----------------------Report START---------------
//
//    @FormUrlEncoded
//    @POST("createNewReport")
//    Call<Default_Contact> createNewReport(
//            @Field("blood_request_id") int blood_request_id,
//            @Field("user_id") String user_id,
//            @Field("report_category_id") String report_category_id,
//            @Field("message") String message
//    );
//
//    //----------------------Report END ----------------
//
//    //----------------------User START ----------------
//    @FormUrlEncoded
//    @POST("updateUserDeviceToken")
//    Call<Default_Contact> updateUserToken(
//            @Field("user_id") String id,
//            @Field("device_token") String token
//    );
//
//    @FormUrlEncoded
//    @POST("updateUserStatus")
//    Call<Default_Contact> updateUserStatus(
//            @Field("user_id") String id,
//            @Field("status") int status
//    );
//
//    //----------------------User END ------------------
//
//
//    //---------------------- Notification START ------------
//    @FormUrlEncoded
//    @POST("sendPushNotifyChatReply")
//    Call<Default_Contact> sendPushNotifyChatReply(
//            @Field("id") String id,
//            @Field("from_id") String from_id,
//            @Field("body") String body
//    );
//
//    @Headers({"Authorization: key=", "Content-Type:application/json"})
//    @POST("fcm/send")
//    Call<ResponseBody> sendNotification(@Body RootModel rootModel);
//
//    @FormUrlEncoded
//    @POST("sendSingleUserNotification")
//    Call<Default_Contact> sendSingleUserNotification(
//            @Field("token") String token,
//            @Field("title") String title,
//            @Field("body") String body,
//            @Field("channel") String channel,
//            @Field("image") String image
//    );
//
//    //---------------------- Notification END --------------
//
//    //---------------------- OTP info save START ------------
//    @FormUrlEncoded
//    @POST("validateOTP")
//    Call<Default_Contact> verifyOTP(
//            @Field("number") String number,
//            @Field("otp") String otp
//    );
//
//    @FormUrlEncoded
//    @POST("validateFirebaseOTP")
//    Call<Default_Contact> verifyFirebaseOTP(
//            @Field("number") String number
//    );
//
//    @FormUrlEncoded
//    @POST("resendOTP")
//    Call<OTP_Contact> resendOTP(
//            @Field("number") String number
//    );
//
//    @FormUrlEncoded
//    @POST("validateOTPForgotPassword")
//    Call<Default_Contact> validateOTPForgotPassword(
//            @Field("number") String number,
//            @Field("otp") String otp
//    );
//    //---------------------- OTP info save END --------------
//
//    //---------------------- Referral Code START ------------
//    @FormUrlEncoded
//    @POST("checkReferralCode")
//    Call<Default_Contact> checkReferralCode(
//            @Field("referralCode") String referralCode,
//            @Field("user_id") String user_id
//    );
//    //---------------------- Referral Code END ------------

}
