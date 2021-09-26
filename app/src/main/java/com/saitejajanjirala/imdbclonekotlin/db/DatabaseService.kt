package com.saitejajanjirala.imdbclonekotlin.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.saitejajanjirala.imdbclonekotlin.db.dao.ResultDao
import com.saitejajanjirala.imdbclonekotlin.models.Result

@Database(entities = [Result::class], exportSchema = false, version = 1)
@TypeConverters(
    IdsToStringConverter::class
)
abstract class DatabaseService : RoomDatabase() {
    abstract fun resultDao(): ResultDao
}