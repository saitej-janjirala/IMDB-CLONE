package com.saitejajanjirala.imdbclone.network;




import com.saitejajanjirala.imdbclone.models.NowPlaying;
import com.saitejajanjirala.imdbclone.models.Popular;
import com.saitejajanjirala.imdbclone.models.SearchResult;
import com.saitejajanjirala.imdbclone.utils.Constants;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {


    @GET(Constants.NOW_PLAYING_URL)
     Single<NowPlaying> getNowPlayingMovies(
        @Query("api_key") String apiKey
    );

    @GET(Constants.POPULAR_MOVIES_URL)
     Single<Popular> getPopularMovies(
            @Query("api_key") String apiKey
    );

    @GET(Constants.SEARCH_MULTI)
    Observable<SearchResult> getSearchResults(
            @Query("api_key") String apiKey,
            @Query("query") String query,
            @Query("include_adult") Boolean includeAdult
    );

}
