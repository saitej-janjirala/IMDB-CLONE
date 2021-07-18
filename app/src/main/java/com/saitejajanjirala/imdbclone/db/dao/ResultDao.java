package com.saitejajanjirala.imdbclone.db.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.saitejajanjirala.imdbclone.models.Result;

import java.util.List;

import io.reactivex.rxjava3.core.Single;

@Dao
public interface ResultDao {
    @Insert
    Single<Long> insert(Result result);

    @Delete
    Single<Integer> delete(Result result);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    Single<List<Long>> insertMany(List<Result> results);

    @Query("select * from results where type=:type")
    Single<List<Result>> getResultsByType(String type);

    @Query("select * from results where bookmarked = 1")
    Single<List<Result>> getBookMarkedResults();

    @Query("DELETE FROM results where bookmarked = 0")
    Single<Integer> clear();

    @Query("UPDATE  results SET bookmarked=0 where bookmarked = 1")
    Single<Integer> clearBookMarks();


    @Update
    Single<Integer> updateBookMarked(Result result);



}
