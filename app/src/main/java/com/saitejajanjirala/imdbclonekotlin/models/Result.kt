package com.saitejajanjirala.imdbclonekotlin.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "results")
data class Result  (
    var adult: Boolean = false,
    var backdrop_path: String? = null,
    var genre_ids: List<Int>? = null,
    @PrimaryKey
    var pKey: String = "" + System.currentTimeMillis(),
    var id: Int = 0,
    var original_language: String? = null,
    var original_title: String? = null,
    var overview: String? = null,
    var popularity: Double = 0.0,
    var poster_path: String? = null,
    var release_date: String? = null,
    var title: String? = null,
    var video: Boolean = false,
    var vote_average: Double = 0.0,
    var vote_count: Int = 0,
    var bookmarked: Boolean = false,
    var type: String? = null

):Serializable{

}