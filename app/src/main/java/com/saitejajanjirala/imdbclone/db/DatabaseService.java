package com.saitejajanjirala.imdbclone.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;


import com.saitejajanjirala.imdbclone.db.dao.ResultDao;

import com.saitejajanjirala.imdbclone.models.Popular;
import com.saitejajanjirala.imdbclone.models.Result;

import org.jetbrains.annotations.NotNull;

@Database(
        entities = { Result.class},
        exportSchema = false,
        version = 1
)
@TypeConverters({IdsToStringConverter.class})
public abstract class DatabaseService extends RoomDatabase {

    @NotNull
    public abstract ResultDao resultDao();
}
