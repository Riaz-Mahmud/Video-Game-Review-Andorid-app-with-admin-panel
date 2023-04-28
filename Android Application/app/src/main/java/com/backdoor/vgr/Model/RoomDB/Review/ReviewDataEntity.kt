package com.backdoor.vgr.Model.RoomDB.Review

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "reviews")
data class ReviewDataEntity(

    @PrimaryKey val id: Long,
    @ColumnInfo(name = "uid") val uid: String,
    @ColumnInfo(name = "name") val title: String?,
    @ColumnInfo(name = "comment") val desc: String?,
    @ColumnInfo(name = "rating") val rating: String?,

)
