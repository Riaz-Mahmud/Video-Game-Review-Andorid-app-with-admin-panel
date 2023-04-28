package com.backdoor.vgr.Model.RoomDB

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "games")
data class GameDataEntity(

    @PrimaryKey val id: Long,
    @ColumnInfo(name = "uid") val uid: String,
    @ColumnInfo(name = "title") val title: String?,
    @ColumnInfo(name = "desc") val desc: String?,
    @ColumnInfo(name = "rating") val rating: String?,
    @ColumnInfo(name = "ratingCount") val ratingCount: String?,
    @ColumnInfo(name = "is_mine_review") val isMineReview: Boolean?,
    @ColumnInfo(name = "image") val image: String?,

    )
