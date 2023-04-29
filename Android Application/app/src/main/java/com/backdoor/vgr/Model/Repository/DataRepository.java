package com.backdoor.vgr.Model.Repository;

import static com.backdoor.vgr.View.Activity.IntroActivity.perfConfig;

import android.content.Context;
import android.util.Log;

import com.backdoor.vgr.Model.RoomDB.Game.GameDao;
import com.backdoor.vgr.Model.RoomDB.Game.GameDataEntity;
import com.backdoor.vgr.Model.RoomDB.Review.ReviewDao;
import com.backdoor.vgr.Model.RoomDB.Review.ReviewDataEntity;
import com.backdoor.vgr.View.Activity.IntroActivity;
import com.backdoor.vgr.View.Model.Default_Contact;
import com.backdoor.vgr.View.Model.Game.GameContact;
import com.backdoor.vgr.View.Model.Game.GameDetailsContact;
import com.backdoor.vgr.View.Model.Game.GameReviews;
import com.backdoor.vgr.View.Model.Game.SingleGameContact;
import com.backdoor.vgr.View.Model.LoginSignup.LoginContact;
import com.backdoor.vgr.View.Model.LoginSignup.SignupContact;
import com.backdoor.vgr.View.Model.StatusCheck.CheckStatusContact;
import com.backdoor.vgr.network.ApiClient;
import com.backdoor.vgr.network.ApiInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DataRepository {

    public static DataRepository dataRepository;
    private final ApiInterface apiInterface;
    private static GameDao gameDao;
    private static ReviewDao reviewDao;

    public GetLoginTask getLoginTask;
    public GetSignupTask getSignupTask;
    public IsCheckUserStatusTask isCheckUserStatusTask;
    public GetAllGamesTask getAllGameTask;
    public GetSingleGameDetailsTask getSingleGameDetailsTask;
    public GetDeleteReviewTask getDeleteReviewTask;
    public SubmitReviewTask submitReviewTask;

    public static DataRepository getInstance(Context context) {
        if (dataRepository == null) {
            dataRepository = new DataRepository(context);
        }
        return dataRepository;
    }

    public DataRepository(Context context) {
        apiInterface = ApiClient.getClient(context).create(ApiInterface.class);
    }

    public DataRepository(GetLoginTask listener, Context context) {
        apiInterface = ApiClient.getClient(context).create(ApiInterface.class);
        this.getLoginTask = listener;
    }

    public DataRepository(GetSignupTask listener, Context context) {
        apiInterface = ApiClient.getClient(context).create(ApiInterface.class);
        this.getSignupTask = listener;
    }

    public DataRepository(IsCheckUserStatusTask listener, Context context) {
        apiInterface = ApiClient.getClient(context).create(ApiInterface.class);
        this.isCheckUserStatusTask = listener;
    }

    public DataRepository(GetAllGamesTask listener, Context context) {
        apiInterface = ApiClient.getClient(context).create(ApiInterface.class);
        this.getAllGameTask = listener;
    }

    public DataRepository(GetSingleGameDetailsTask listener, Context context) {
        apiInterface = ApiClient.getClient(context).create(ApiInterface.class);
        this.getSingleGameDetailsTask = listener;
    }

    public DataRepository(GetDeleteReviewTask listener, Context context) {
        apiInterface = ApiClient.getClient(context).create(ApiInterface.class);
        this.getDeleteReviewTask = listener;
    }

    public DataRepository(SubmitReviewTask listener, Context context) {
        apiInterface = ApiClient.getClient(context).create(ApiInterface.class);
        this.submitReviewTask = listener;
    }

    public void userLogin(String email, String password) {
        apiInterface.getUserLoginResponse(email, password).enqueue(new Callback<LoginContact>() {
            @Override
            public void onResponse(Call<LoginContact> call, Response<LoginContact> response) {
                if (response.isSuccessful()) {
                    getLoginTask.getLoginTaskResponse(response.body());
                } else {
                    getLoginTask.getLoginTaskResponse(null);
                }
            }

            @Override
            public void onFailure(Call<LoginContact> call, Throwable t) {
                call.cancel();
                getLoginTask.getLoginTaskResponse(null);
            }
        });
    }

    public void userSignup(String firstName, String lastName, String email, String username, String rePassword) {
        apiInterface.getUserSignUpResponse(firstName, lastName, username, email, rePassword).enqueue(new Callback<SignupContact>() {
            @Override
            public void onResponse(Call<SignupContact> call, Response<SignupContact> response) {
                if (response.isSuccessful()) {
                    getSignupTask.getSignupTaskResponse(response.body());
                } else {
                    getSignupTask.getSignupTaskResponse(null);
                }
            }

            @Override
            public void onFailure(Call<SignupContact> call, Throwable t) {
                call.cancel();
                getSignupTask.getSignupTaskResponse(null);
            }
        });
    }

    public void checkUserStatus() {
        apiInterface.checkUserStatus(perfConfig.readUserId()).enqueue(new Callback<CheckStatusContact>() {
            @Override
            public void onResponse(Call<CheckStatusContact> call, Response<CheckStatusContact> response) {
                if (response.code() == 401) {
                    isCheckUserStatusTask.checkUserStatusServerResponse(response);
                    return;
                }
                if (response.isSuccessful()) {
                    isCheckUserStatusTask.checkUserStatusServerResponse(response);
                } else {
                    isCheckUserStatusTask.checkUserStatusServerResponse(null);
                }
            }

            @Override
            public void onFailure(Call<CheckStatusContact> call, Throwable t) {
                call.cancel();
                isCheckUserStatusTask.checkUserStatusServerResponse(null);
            }
        });
    }

    public void getGamesList() {
        apiInterface.getGamesList().enqueue(new Callback<GameContact>() {
            @Override
            public void onResponse(Call<GameContact> call, Response<GameContact> response) {
                if (response.isSuccessful()) {
                    getAllGameTask.getAllGameListTask(response.body());
                    saveRoomDB(response.body().getGameDetailsContactList());
                } else {
                    getAllGameTask.getAllGameListTask(null);
                }
            }

            @Override
            public void onFailure(Call<GameContact> call, Throwable t) {
                call.cancel();
                getAllGameTask.getAllGameListTask(null);
            }
        });
    }

    private void saveRoomDB(List<GameDetailsContact> launches) {

        gameDao = IntroActivity.database.getGameDao();

        new Thread(() -> {
            try {
                int count = gameDao.getGameCount();
                Log.d("RoomDBCat", "getGameCount value: " + count);
                if (count > 0) {
                    int save = gameDao.deleteAllGames();
                    Log.d("RoomDBCat", "deleteAllGames value: " + save);
                    if (save > 0) {
                        insertIntoDB(gameDao, launches);
                    } else {
                        Log.d("RoomDBCat", "deleteAllGames else");
                    }
                } else {
                    insertIntoDB(gameDao, launches);
                }
            } catch (Exception e) {
                Log.d("RoomDBCat", "DeleteAll Error: " + e.getMessage());
            }
        }).start();
    }

    private void insertIntoDB(GameDao gameDao, List<GameDetailsContact> launches) {
        new Thread(() -> {
            if (launches != null) {
                int i = 1;

                for (GameDetailsContact item : launches) {
                    try {
                        long dataInsert = gameDao.insertGame(new GameDataEntity(
                                i++,
                                item.getId(),
                                item.getName(),
                                item.getDesc(),
                                String.valueOf(item.getRating()),
                                item.getRating_count(),
                                item.is_mine_review() ? 1 : 0,
                                item.getBanner()
                        ));

                        Log.d("isMineReview", "DataRepo itemIsMine: " + item.is_mine_review());
                        int test = item.is_mine_review() ? 1 : 0;
                        Log.d("isMineReview", "DataRepo itemIsMine to int: " + test);

                        if (dataInsert > 0) {
                            Log.d("RoomDBCat", "Saved: " + item.getName());
                        } else {
                            Log.d("RoomDBCat", "Not Saved: " + item.getName());
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.d("RoomDBCat", "Data Store: " + item.getName() + " Error: " + e.getMessage());
                    }

                    Log.d("RoomDBCat", "Data Store: " + item.getName());
                }
            }
        }).start();
    }

    public void getSingleGameDetails(String gameId) {
        apiInterface.getSingleGameData(gameId).enqueue(new Callback<SingleGameContact>() {
            @Override
            public void onResponse(Call<SingleGameContact> call, Response<SingleGameContact> response) {
                if (response.isSuccessful()) {
                    getSingleGameDetailsTask.getSingleGameListTask(response.body());
                    saveReviewRoomDB(response.body().getGameDetailsContact());
                } else {
                    getSingleGameDetailsTask.getSingleGameListTask(null);
                }
            }

            @Override
            public void onFailure(Call<SingleGameContact> call, Throwable t) {
                call.cancel();
                getSingleGameDetailsTask.getSingleGameListTask(null);
            }
        });
    }

    private void saveReviewRoomDB(GameDetailsContact gameDetailsContact) {
        reviewDao = IntroActivity.database.getReviewDao();

        new Thread(() -> {
            try {
                int count = reviewDao.getReviewCountByGameId(Integer.parseInt(gameDetailsContact.getId()));
                Log.d("RoomDBReviewCat", "getReviewCountByGameId value: " + count);
                if (count > 0) {
                    int save = reviewDao.deleteAllReviewsByGameId(Integer.parseInt(gameDetailsContact.getId()));
                    Log.d("RoomDBReviewCat", "deleteAllReviewsByGameId value: " + save);
                    if (save > 0) {
                        insertReviewsIntoDB(gameDetailsContact);
                    } else {
                        Log.d("RoomDBReviewCat", "deleteAllReviewsByGameId else");
                    }
                } else {
                    Log.d("RoomDBReviewCat", "else");
                    insertReviewsIntoDB(gameDetailsContact);
                }
            } catch (Exception e) {
                Log.d("RoomDBReviewCat", "Catch Error: " + e.getMessage());
            }
        }).start();
    }

    private void insertReviewsIntoDB(GameDetailsContact gameDetailsContact) {
        reviewDao = IntroActivity.database.getReviewDao();
        gameDao = IntroActivity.database.getGameDao();
        new Thread(() -> {
            if (gameDetailsContact != null) {

                try {
                    gameDao.updateGameIsMineReview(Integer.parseInt(gameDetailsContact.getId()), gameDetailsContact.is_mine_review() ? 1 : 0);
                } catch (Exception e) {
                    Log.d("RoomDBReviewCat", "update game isMineReview error: " + e.getMessage());
                }

                for (GameReviews item : gameDetailsContact.getGameReviewsList()) {
                    try {
                        long dataInsert = reviewDao.insertReview(new ReviewDataEntity(
                                null
                                , item.getId(),
                                item.getUserContact().getFirst_name() + " " + item.getUserContact().getLast_name(),
                                item.getComment(),
                                String.valueOf(item.getRating()),
                                item.is_mine() ? 1 : 0,
                                Integer.parseInt(item.getGameId()),
                                item.getStatus()
                        ));

                        if (dataInsert > 0) {
                            Log.d("RoomDBReviewCat", "Saved: " + item.getId());
                        } else {
                            Log.d("RoomDBReviewCat", "Not Saved: " + item.getId());
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.d("RoomDBReviewCat", "Data Store: " + item.getId() + " Error: " + e.getMessage());
                    }
                }
            }
        }).start();
    }

    public void deleteSingleReview(String gameId, String reviewId) {
        apiInterface.deleteSingleReview(gameId, reviewId).enqueue(new Callback<Default_Contact>() {
            @Override
            public void onResponse(Call<Default_Contact> call, Response<Default_Contact> response) {
                if (response.isSuccessful()) {
                    getDeleteReviewTask.getDeleteReviewTask(response.body());
                } else {
                    getDeleteReviewTask.getDeleteReviewTask(null);
                }
            }

            @Override
            public void onFailure(Call<Default_Contact> call, Throwable t) {
                call.cancel();
                getDeleteReviewTask.getDeleteReviewTask(null);
            }
        });
    }

    public void submitUserReview(String gameId, float rating, String message, String lat, String lon) {
        apiInterface.submitNewReview(gameId, rating, message, lat, lon).enqueue(new Callback<Default_Contact>() {
            @Override
            public void onResponse(Call<Default_Contact> call, Response<Default_Contact> response) {
                if (response.isSuccessful()) {
                    submitReviewTask.getSubmitReviewTask(response.body());
                } else {
                    submitReviewTask.getSubmitReviewTask(null);
                }
            }

            @Override
            public void onFailure(Call<Default_Contact> call, Throwable t) {
                submitReviewTask.getSubmitReviewTask(null);
                call.cancel();
            }
        });
    }


    public interface IsCheckUserStatusTask {
        void checkUserStatusServerResponse(Response body);
    }

    public interface GetAllGamesTask {
        void getAllGameListTask(GameContact body);
    }

    public interface GetSingleGameDetailsTask {
        void getSingleGameListTask(SingleGameContact body);
    }

    public interface GetDeleteReviewTask {
        void getDeleteReviewTask(Default_Contact body);
    }

    public interface SubmitReviewTask {
        void getSubmitReviewTask(Default_Contact body);
    }

    public interface GetLoginTask {
        void getLoginTaskResponse(LoginContact body);
    }

    public interface GetSignupTask {
        void getSignupTaskResponse(SignupContact body);
    }
}
