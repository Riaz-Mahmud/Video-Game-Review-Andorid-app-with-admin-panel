package com.backdoor.vgr.Model.RoomDB.Review

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "reviews")
data class ReviewDataEntity(

    @PrimaryKey(autoGenerate = true) val id: Long?,
    @ColumnInfo(name = "uid") val uid: String,
    @ColumnInfo(name = "name") val title: String?,
    @ColumnInfo(name = "comment") val comment: String?,
    @ColumnInfo(name = "rating") val rating: String?,
    @ColumnInfo(name = "is_mine") val isMine: Int,
    @ColumnInfo(name = "gameId") val gameId: Int,
    @ColumnInfo(name = "status") val status: String?,

    )
