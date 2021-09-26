package com.saitejajanjirala.imdbclonekotlin.utils

import android.content.Context
import android.media.ApplicationMediaCapabilities
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.saitejajanjirala.imdbclonekotlin.R
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.ArrayList
import java.util.HashMap
import javax.inject.Singleton

@Singleton
class  Helper(private val context: Context) {

    fun getConnection(): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }
    companion object {
        fun loadImage(context: Context?, url: String, imageView: ImageView) {
            Glide.with(context!!)
                .load(Keys.IMAGE_BASE_URL.toString() + url)
                .error(R.drawable.ic_baseline_error_24)
                .into(imageView)
        }
        fun getGenresFromIds(ids: List<Int>): ArrayList<String>? {
            val map = HashMap<Int, String>()
            map[28] = "Action"
            map[12] = "Adventure"
            map[16] = "Animation"
            map[35] = "Comedy"
            map[80] = "Crime"
            map[99] = "Documentary"
            map[18] = "Drama"
            map[10751] = "Family"
            map[14] = "Fantasy"
            map[36] = "History"
            map[27] = "Horror"
            map[10402] = "Music"
            map[9648] = "Mystery"
            map[10749] = "Romance"
            map[878] = "Science Fiction"
            map[10770] = "TV Movie"
            map[53] = "Thriller"
            map[10752] = "War"
            map[37] = "Western"
            val genres = ArrayList<String>()
            for (i in ids) {
                val `val` = map[i]
                if (`val` != null) {
                    genres.add(`val`)
                }
            }
            return genres
        }

    }


}