package com.flow.hugh.datasource.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.flow.hugh.data.Recent

@Dao
interface RecentDao {
    @Query("SELECT DISTINCT query FROM recent ORDER BY ID DESC LIMIT 10")
    fun getLiveList(): LiveData<List<String>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun add(recent: Recent)

    @Query("DELETE FROM recent WHERE query = :query")
    suspend fun remove(query: String)

    @Query("DELETE FROM recent")
    suspend fun removeAll()
}