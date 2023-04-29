package com.backdoor.vgr.Model.RoomDB

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.backdoor.vgr.Model.RoomDB.Game.GameDao
import com.backdoor.vgr.Model.RoomDB.Game.GameDataEntity
import com.backdoor.vgr.Model.RoomDB.Review.ReviewDao
import com.backdoor.vgr.Model.RoomDB.Review.ReviewDataEntity


@Database(
    version = 10,
    entities = [GameDataEntity::class, ReviewDataEntity::class],
    exportSchema = true,
)

abstract class ReviewQuestDatabase : RoomDatabase() {

    abstract fun getGameDao(): GameDao
    abstract fun getReviewDao(): ReviewDao

    companion object {
        @Volatile
        private var INSTANCE: ReviewQuestDatabase? = null

        private const val DB_NAME = "reviewQuest.db"
        const val TAG = "DBINFO" //<<<< ADDED for logging

        fun getDatabase(context: Context): ReviewQuestDatabase {
            if (INSTANCE == null) {
                synchronized(this) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        ReviewQuestDatabase::class.java,
                        DB_NAME
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }
            return INSTANCE!!
        }

        object dbCallback : Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                Log.d(TAG, "onCreate")
            }

            override fun onOpen(db: SupportSQLiteDatabase) {
                super.onOpen(db)
                Log.d(TAG, "onOpen")
            }

            override fun onDestructiveMigration(db: SupportSQLiteDatabase) {
                super.onDestructiveMigration(db)
                Log.d(TAG, "onDestructiveMigration")
            }
        }
    }


}