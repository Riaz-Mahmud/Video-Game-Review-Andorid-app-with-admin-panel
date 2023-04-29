package com.backdoor.vgr.Model.RoomDB.Review;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ReviewDao {

    @Insert
    public abstract long insertReview(ReviewDataEntity data) throws Exception;

    @Query("SELECT * FROM reviews")
    public abstract LiveData<List<ReviewDataEntity>> getAllReview();

    @Query("SELECT * FROM reviews WHERE gameId = :gameId")
    public abstract LiveData<List<ReviewDataEntity>> getReviewByGameId(int gameId);

    @Query("DELETE FROM reviews WHERE gameId = :gameId")
    public abstract int deleteAllReviewsByGameId(int gameId) throws Exception;

    @Query("SELECT COUNT(*) FROM reviews")
    int getReviewCount() throws Exception;

    @Query("SELECT COUNT(*) FROM reviews WHERE gameId = :gameId")
    int getReviewCountByGameId(int gameId);
}
