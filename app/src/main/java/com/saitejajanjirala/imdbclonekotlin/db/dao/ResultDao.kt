package com.saitejajanjirala.imdbclonekotlin.db.dao

import androidx.room.*
import com.saitejajanjirala.imdbclonekotlin.models.Result

@Dao
interface ResultDao {
    @Insert
    suspend fun insert(result: Result): Long

    @Delete
    suspend fun delete(result: Result): Int

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertMany(results: List<Result>): List<Long>

    @Query("select * from results where type=:type")
    suspend fun getResultsByType(type: String): List<Result>

    @Query("select * from results where bookmarked = 1")
    suspend fun bookMarkedResults(): List<Result>

    @Query("DELETE FROM results where bookmarked = 0")
    suspend fun clear():Int

    @Query("UPDATE  results SET bookmarked=0 where bookmarked = 1")
    suspend fun clearBookMarks():Int

    @Update
    suspend fun updateBookMarked(result: Result): Int
}