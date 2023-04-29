package com.backdoor.vgr.Model.RoomDB.Game;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface GameDao {

    @Insert
    public abstract long insertGame(GameDataEntity data) throws Exception;

    @Query("SELECT * FROM games")
    public abstract LiveData<List<GameDataEntity>> getAllGame();

    @Query("SELECT * FROM games WHERE uid = :id")
    public abstract LiveData<GameDataEntity> getGameById(int id);

    @Delete
    public abstract void delete(GameDataEntity data) throws Exception;

    @Query("DELETE FROM games")
    public abstract int deleteAllGames() throws Exception;

    @Query("SELECT COUNT(*) FROM games")
    int getGameCount() throws Exception;

    @Query("UPDATE games SET is_mine_review = :isMineReview WHERE uid = :id")
    void updateGameIsMineReview(int id, int isMineReview) throws Exception;

}