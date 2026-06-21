package com.homecontentlock.data.local.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [LogEntity::class, ConfigEntity::class, CustomSiteEntity::class],
    version = 1,
    exportSchema = false
)
abstract class BlockerDatabase : RoomDatabase() {
    abstract fun logDao(): LogDao
    abstract fun configDao(): ConfigDao
    abstract fun customSiteDao(): CustomSiteDao

    companion object {
        @Volatile
        private var INSTANCE: BlockerDatabase? = null

        fun getDatabase(context: Context): BlockerDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    BlockerDatabase::class.java,
                    "blocker_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}