package com.backdoor.vgr.viewmodel;

import static com.backdoor.vgr.View.Activity.MainActivity.perfConfig;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.backdoor.vgr.Model.Repository.DataRepository;
import com.backdoor.vgr.Model.RoomDB.Game.GameDao;
import com.backdoor.vgr.Model.RoomDB.Game.GameDataEntity;
import com.backdoor.vgr.Model.RoomDB.Review.ReviewDao;
import com.backdoor.vgr.Model.RoomDB.Review.ReviewDataEntity;
import com.backdoor.vgr.R;
import com.backdoor.vgr.View.Activity.IntroActivity;
import com.backdoor.vgr.View.Model.Default_Contact;
import com.backdoor.vgr.View.Model.Game.GameDetailsContact;
import com.backdoor.vgr.View.Model.Game.GameReviews;
import com.backdoor.vgr.View.Model.Game.SingleGameContact;
import com.backdoor.vgr.View.Model.User.UserContact;
import com.backdoor.vgr.network.ApiClient;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class GameViewModel extends ViewModel {

    Activity activity;
    DataRepository dataRepository;
    View view;
    ReviewDao reviewDao;


    MutableLiveData<String> errorMessage = new MutableLiveData<>();
    MutableLiveData<List<GameDetailsContact>> gameDetailsContactList = new MutableLiveData<>();
    MutableLiveData<SingleGameContact> singleGameContact = new MutableLiveData<>();
    MutableLiveData<Default_Contact> deleteSingleReviewContact = new MutableLiveData<>();
    MutableLiveData<Default_Contact> submitReviewContact = new MutableLiveData<>();
    MutableLiveData<String> fullName = new MutableLiveData<>();
    MutableLiveData<String> gameName = new MutableLiveData<>();
    MutableLiveData<Float> rating = new MutableLiveData<>();
    MutableLiveData<String> ratingCount = new MutableLiveData<>();
    MutableLiveData<Spanned> gameDesc = new MutableLiveData<>();
    MutableLiveData<String> gameImage = new MutableLiveData<>();
    MutableLiveData<Integer> detailsVisibility = new MutableLiveData<>();
    MutableLiveData<Integer> buttonPress = new MutableLiveData<>();
    MutableLiveData<Boolean> isMineReview = new MutableLiveData<>(true);
    LiveData<List<GameDataEntity>> gameData = new MutableLiveData<>();
    MutableLiveData<String> showMoreOrLessTxt = new MutableLiveData<>("Show More...");
    MutableLiveData<Integer> maxDescLine = new MutableLiveData<>(5);

    public MutableLiveData<String> getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName.setValue(fullName);
    }

    public void setUserInfo() {
        setFullName("Welcome " + perfConfig.readUserName());
    }

    public void setFragmentActivity(FragmentActivity activity) {
        this.activity = activity;
    }

    public void getAllGameList() {
        if (checkConnection()) {
            dataRepository = DataRepository.getInstance(activity.getApplicationContext());
            dataRepository = new DataRepository((DataRepository.GetAllGamesTask) body -> {
                if (body != null) {
                    if (body.isSuccess()) {
                        gameDetailsContactList.postValue(body.getGameDetailsContactList());
                    } else {
                        perfConfig.displaySnackBar(view, body.getError());
                    }
                } else {
                    perfConfig.displaySnackBar(view, "Somethings went wrong!");
                }
            }, activity.getApplicationContext());
            dataRepository.getGamesList();
        } else {
            // Fetch data from the local RoomDB
            GameDao gameDao = IntroActivity.database.getGameDao();
            LiveData<List<GameDataEntity>> gameData = gameDao.getAllGame();
            gameData.observe((LifecycleOwner) activity, (Observer<List<GameDataEntity>>) gameDataEntities -> {
                List<GameDetailsContact> gameDetailsContacts = new ArrayList<>();
                for (GameDataEntity gameDataEntity : gameDataEntities) {
                    GameDetailsContact gameDetailsContact = new GameDetailsContact();
                    gameDetailsContact.setId(gameDataEntity.getUid());
                    gameDetailsContact.setName(gameDataEntity.getTitle());
                    gameDetailsContact.setDesc(gameDataEntity.getDesc());
                    gameDetailsContact.setRating(Float.parseFloat(Objects.requireNonNull(gameDataEntity.getRating())));
                    gameDetailsContact.setRating_count(gameDataEntity.getRatingCount());
                    gameDetailsContact.setBanner(gameDataEntity.getImage());
                    gameDetailsContacts.add(gameDetailsContact);
                }
                gameDetailsContactList.setValue(gameDetailsContacts);
            });
        }
    }


    public void getSingleGameDetails(String gameId) {
        if (checkConnection()) {
            Log.d("checkConnection", "if");

            dataRepository = DataRepository.getInstance(activity.getApplicationContext());
            dataRepository = new DataRepository((DataRepository.GetSingleGameDetailsTask) body -> {
                if (body != null) {
                    if (body.isSuccess()) {
                        setSingleGameDetails(body.getGameDetailsContact());
                        singleGameContact.postValue(body);
                    } else {
                        perfConfig.displayToast(body.getError());
                        activity.onBackPressed();
                    }
                } else {
                    perfConfig.displayToast("Somethings went wrong!");
                    activity.onBackPressed();
                }
            }, activity.getApplicationContext());
            dataRepository.getSingleGameDetails(gameId);
        } else {
            Log.d("checkConnection", "else");
            GameDao gameDao = IntroActivity.database.getGameDao();
            reviewDao = IntroActivity.database.getReviewDao();
            try {
                LiveData<GameDataEntity> gameData = gameDao.getGameById(Integer.parseInt(gameId));
                GameDetailsContact gameDetailsContact = new GameDetailsContact();

                gameData.observe((LifecycleOwner) activity, gameDataEntity -> {
                    gameDetailsContact.setId(gameDataEntity.getUid());
                    gameDetailsContact.setName(gameDataEntity.getTitle());
                    gameDetailsContact.setDesc(gameDataEntity.getDesc());
                    gameDetailsContact.setRating(Float.parseFloat(Objects.requireNonNull(gameDataEntity.getRating())));
                    gameDetailsContact.setRating_count(gameDataEntity.getRatingCount());
                    gameDetailsContact.setIs_mine_review(gameDataEntity.isMineReview() == 1);
                    gameDetailsContact.setBanner(gameDataEntity.getImage());

                    setSingleGameDetails(gameDetailsContact);
                    SingleGameContact singleGame = new SingleGameContact();
                    singleGame.setGameDetailsContact(gameDetailsContact);
                    singleGame.setSuccess(true);
                    singleGame.setError(null);

                    singleGameContact.postValue(singleGame);
                });

                LiveData<List<ReviewDataEntity>> reviewData = reviewDao.getReviewByGameId(Integer.parseInt(gameId));
                reviewData.observe((LifecycleOwner) activity, (Observer<List<ReviewDataEntity>>) reviewDataEntities -> {
                    List<GameReviews> gameReviewsArrayList = new ArrayList<>();
                    for (ReviewDataEntity reviewDataEntity : reviewDataEntities) {
                        GameReviews gameReviews = new GameReviews();
                        gameReviews.setId(reviewDataEntity.getUid());
                        gameReviews.setGameId(String.valueOf(reviewDataEntity.getGameId()));
                        gameReviews.setComment(reviewDataEntity.getComment());
                        gameReviews.setIs_mine(reviewDataEntity.isMine() == 1);
                        gameReviews.setRating(Float.parseFloat(Objects.requireNonNull(reviewDataEntity.getRating())));
                        gameReviews.setStatus(reviewDataEntity.getStatus());

                        UserContact userContact = new UserContact();
                        userContact.setFirst_name(reviewDataEntity.getTitle());

                        gameReviews.setUserContact(userContact);
                        gameReviewsArrayList.add(gameReviews);

                        Log.d("viewRoomDBReviewCat", "Loop value here: " + reviewDataEntity.getUid());
                    }
                    gameDetailsContact.setGameReviewsList(gameReviewsArrayList);
                });


            } catch (Exception e) {
                perfConfig.displayToast("Something went wrong!");
                activity.onBackPressed();
            }
        }
    }

    public void deleteSingleReview(String gameId, String reviewId) {
        if (checkConnection()) {

            perfConfig.displaySnackBarWithProgressBar(view, "Please wait...");

            dataRepository = DataRepository.getInstance(activity.getApplicationContext());
            dataRepository = new DataRepository((DataRepository.GetDeleteReviewTask) body -> {
                perfConfig.snackbar.dismiss();
                if (body != null) {
                    if (body.isSuccess()) {
                        deleteSingleReviewContact.postValue(body);
                    } else {
                        perfConfig.displayToast(body.getError());
                    }
                } else {
                    perfConfig.displayToast("Somethings went wrong!");
                }
            }, activity.getApplicationContext());
            dataRepository.deleteSingleReview(gameId, reviewId);
        }
    }

    public void submitUserReview(String gameId, float rating, String message, String lat, String lon) {
        if (checkConnection()) {
            perfConfig.displaySnackBarWithProgressBar(view, "Please wait...");

            dataRepository = DataRepository.getInstance(activity.getApplicationContext());
            dataRepository = new DataRepository((DataRepository.SubmitReviewTask) body -> {
                perfConfig.snackbar.dismiss();
                if (body != null) {
                    if (body.isSuccess()) {
                        submitReviewContact.postValue(body);
                    } else {
                        perfConfig.displayToast(body.getError());
                    }
                } else {
                    perfConfig.displayToast("Somethings went wrong!");
                }
            }, activity.getApplicationContext());
            dataRepository.submitUserReview(gameId, rating, message, lat, lon);
        }
    }

    private void setSingleGameDetails(GameDetailsContact data) {
        setDetailsVisibility(1);
        setRating(data.getRating());
        setRatingCount(data.getRating() + "(" + data.getRating_count() + ")");
        setGameName(data.getName());
        setGameDesc(Html.fromHtml(data.getDesc(), Html.FROM_HTML_MODE_COMPACT));
        if (data.getBanner() != null) {
            if (!data.getBanner().equals("")) {
                setGameImage(data.getBanner());
                gameImage.postValue(data.getBanner());
            }
        }

        if (checkConnection()) {
            setIsMineReview(data.is_mine_review());
        } else {
            setIsMineReview(true);
        }

        buttonPress.setValue(1);
    }

    @BindingAdapter("picassoImageLoad")
    public static void loadImage(ImageView view, String imageUrl) {
        try {
            Picasso.get().load(ApiClient.getImageUrl(view.getContext()) + imageUrl)
                    .error(R.drawable.no_image_found)
                    .into(view);
        } catch (Exception e) {
            view.setBackgroundResource(R.drawable.no_image_found);
        }
    }

    public MutableLiveData<String> getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName.setValue(gameName);
    }

    public MutableLiveData<Float> getRating() {
        return rating;
    }

    public void setRating(Float rating) {
        this.rating.setValue(rating);
    }

    public MutableLiveData<Spanned> getGameDesc() {
        return gameDesc;
    }

    public void setGameDesc(Spanned gameDesc) {
        this.gameDesc.setValue(gameDesc);
    }

    public MutableLiveData<String> getGameImage() {
        if (gameImage == null) {
            gameImage = new MutableLiveData<>();
        }
        return gameImage;
    }

    public void setGameImage(String gameImage) {
        this.gameImage.setValue(gameImage);
    }

    public MutableLiveData<String> getRatingCount() {
        return ratingCount;
    }

    public void setRatingCount(String ratingCount) {
        this.ratingCount.setValue(ratingCount);
    }

    public MutableLiveData<Integer> getDetailsVisibility() {
        return detailsVisibility;
    }

    public void setDetailsVisibility(Integer detailsVisibility) {
        this.detailsVisibility.setValue(detailsVisibility);
    }

    public MutableLiveData<Boolean> getIsMineReview() {
        return isMineReview;
    }

    public void setIsMineReview(Boolean isMineReview) {
        this.isMineReview.setValue(isMineReview);
    }

    public MutableLiveData<String> getShowMoreOrLessTxt() {
        return showMoreOrLessTxt;
    }

    public void setShowMoreOrLessTxt(String showMoreOrLessTxt) {
        this.showMoreOrLessTxt.setValue(showMoreOrLessTxt);
    }

    public void onTapMoreTextBtn() {
        if (Objects.equals(showMoreOrLessTxt.getValue(), "Show More...")) {
            setMaxDescLine(Integer.MAX_VALUE);
            setShowMoreOrLessTxt("Show less");
        } else {
            setMaxDescLine(5);
            setShowMoreOrLessTxt("Show More...");
        }
    }

    public MutableLiveData<Integer> getMaxDescLine() {
        return maxDescLine;
    }

    public void setMaxDescLine(Integer maxDescLine) {
        this.maxDescLine.setValue(maxDescLine);
    }

    public MutableLiveData<List<GameDetailsContact>> getGameDetailsContactList() {
        if (gameDetailsContactList == null) {
            gameDetailsContactList = new MutableLiveData<>();
        }
        return gameDetailsContactList;
    }

    public MutableLiveData<SingleGameContact> getSingleGameContact() {
        if (singleGameContact == null) {
            singleGameContact = new MutableLiveData<>();
        }
        return singleGameContact;
    }

    public void setSingleGameContact(SingleGameContact singleGameContact) {
        this.singleGameContact.setValue(singleGameContact);
    }

    public MutableLiveData<Default_Contact> getDeleteSingleReviewContact() {
        if (deleteSingleReviewContact == null) {
            deleteSingleReviewContact = new MutableLiveData<>();
        }
        return deleteSingleReviewContact;
    }

    public MutableLiveData<Default_Contact> getSubmitReviewContact() {
        if (submitReviewContact == null) {
            submitReviewContact = new MutableLiveData<>();
        }
        return submitReviewContact;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }

    public boolean checkConnection() {
        ConnectivityManager manager = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        assert manager != null;
        NetworkInfo activeNetwork = manager.getActiveNetworkInfo();
        if (activeNetwork == null) {
            perfConfig.displaySnackBar(view, "No Internet Connection");
            return false;
        } else {
            return true;
        }
    }

    public void onBackBtnPress() {
        activity.onBackPressed();
    }

}
