package com.flow.hugh.datasource

import androidx.room.Database
import androidx.room.RoomDatabase
import com.flow.hugh.data.Recent
import com.flow.hugh.datasource.dao.RecentDao

@Database(entities = [Recent::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getRecentDao(): RecentDao
}