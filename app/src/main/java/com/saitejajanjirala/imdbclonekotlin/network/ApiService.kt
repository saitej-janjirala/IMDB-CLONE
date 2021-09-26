package com.saitejajanjirala.imdbclonekotlin.network

import com.saitejajanjirala.imdbclonekotlin.models.NowPlaying
import com.saitejajanjirala.imdbclonekotlin.models.Popular
import com.saitejajanjirala.imdbclonekotlin.models.SearchResult
import com.saitejajanjirala.imdbclonekotlin.utils.Keys
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET(Keys.NOW_PLAYING_URL)
    suspend fun getNowPlayingMovies(): NowPlaying?

    @GET(Keys.POPULAR_MOVIES_URL)
    suspend fun getPopularMovies(): Popular?

    @GET(Keys.SEARCH_MULTI)
    suspend fun getSearchResults(
        @Query("query") query: String?,
        @Query("include_adult") includeAdult: Boolean?
    ): SearchResult?

    companion object{
        fun getInstance(): ApiService {
            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY
            val httpClient = OkHttpClient.Builder().apply {
                addInterceptor(object :Interceptor{
                    override fun intercept(chain: Interceptor.Chain): Response {
                        val originalRequest = chain.request()
                        val originalUrl = originalRequest.url
                        val newRequest=originalRequest.newBuilder().apply {
                            url(originalUrl.newBuilder().addQueryParameter(
                                "api_key","0360553e8b280942cadda1ad5ef33f42").build())
                        }.build()
                        return chain.proceed(newRequest)
                    }

                })
            }
            httpClient.addInterceptor(logging)
            return Retrofit.Builder()
                .baseUrl(Keys.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build()
                .create(ApiService::class.java)
        }
    }
}