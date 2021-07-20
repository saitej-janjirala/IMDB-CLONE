package com.saitejajanjirala.imdbclone.repo;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.saitejajanjirala.imdbclone.BuildConfig;
import com.saitejajanjirala.imdbclone.db.DatabaseService;
import com.saitejajanjirala.imdbclone.di.qualifiers.ApplicationContext;
import com.saitejajanjirala.imdbclone.models.Result;
import com.saitejajanjirala.imdbclone.models.SearchResult;
import com.saitejajanjirala.imdbclone.network.ApiService;
import com.saitejajanjirala.imdbclone.listeners.BookMarkListener;
import com.saitejajanjirala.imdbclone.utils.Constants;
import com.saitejajanjirala.imdbclone.utils.Utils;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.ObservableSource;
import io.reactivex.rxjava3.core.SingleSource;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.schedulers.Schedulers;


public class IMDBRepo {
    CompositeDisposable compositeDisposable;
    ApiService apiService;
    Context context;
    DatabaseService databaseService;

    @Inject
    public IMDBRepo(CompositeDisposable compositeDisposable, ApiService apiService, @ApplicationContext Context context, DatabaseService databaseService) {
        this.compositeDisposable = compositeDisposable;
        this.apiService = apiService;
        this.context = context;
        this.databaseService = databaseService;
    }

    public void getNowPlaying(MutableLiveData<List<Result>> nowPlayingMutableLiveData) {
        if (Utils.getConnection(context)) {
            try {
                compositeDisposable.add(
                        apiService.getNowPlayingMovies(BuildConfig.API_KEY)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(response -> {
                                    saveResultsDatabase(Constants.NOW_PLAYING, response.results, nowPlayingMutableLiveData, null);
                                    Log.i("response code", response.statusCode + "");
                                }, throwable -> {
                                })
                );
            } catch (Exception e) {
                Log.i("now playing exception", e.getMessage());
            }
        } else {
            getDataFromDB(Constants.NOW_PLAYING, nowPlayingMutableLiveData, null);
        }
    }

    public void getPopular(MutableLiveData<List<Result>> mLiveData) {
        if (Utils.getConnection(context)) {
            try {
                compositeDisposable.add(
                        apiService.getPopularMovies(BuildConfig.API_KEY)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(response -> {
                                    saveResultsDatabase(Constants.POPULAR, response.results, null, mLiveData);
                                }, throwable -> {
                                })
                );
            } catch (Exception e) {
                Log.i("now playing exception", e.getMessage());
            }
        } else {
            getDataFromDB(Constants.POPULAR, null, mLiveData);
        }
    }

    public ObservableSource<SearchResult> getSearchResults(String query, MutableLiveData<SearchResult> mLiveData) {
        try {
            return apiService.getSearchResults(BuildConfig.API_KEY,query,true);
//            return compositeDisposable.add(
//                    apiService.getSearchResults(BuildConfig.API_KEY, query, true)
//                            .subscribeOn(Schedulers.io())
//                            .observeOn(AndroidSchedulers.mainThread())
//                            .subscribe(response -> {
//                                mLiveData.postValue(response);
//                                Log.i("response", response + "");
//                            }, throwable -> {
//
//                            })
//            );
        } catch (Exception e) {
            Log.i("now playing exception", e.getMessage());
        }
        return null;
    }

    private void saveResultsDatabase(String type, List<Result> results, MutableLiveData<List<Result>> nowPlaying, MutableLiveData<List<Result>> populars) {
        for (Result i : results) {
            i.type = type;
            i.pKey = type + "" + i.id;
        }
        compositeDisposable.add(
                databaseService.resultDao().clear()
                        .flatMap(new Function<Integer, SingleSource<List<Long>>>() {
                            @Override
                            public SingleSource<List<Long>> apply(Integer integer) throws Throwable {
                                return databaseService.resultDao().insertMany(results);
                            }
                        })
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(response -> {
                            getDataFromDB(type, nowPlaying, populars);
                        }, throwable -> {
                            Log.i("saved results error", throwable.getMessage());
                        }));


    }

    private void getDataFromDB(String type, MutableLiveData<List<Result>> nowPlaying, MutableLiveData<List<Result>> populars) {
        compositeDisposable.add(
                databaseService.resultDao().getResultsByType(type)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(response -> {
                            if (type.equals(Constants.POPULAR)) {
                                populars.postValue(response);
                            } else {
                                nowPlaying.postValue(response);
                            }
                        }, throwable -> {
                            Log.i("reason", throwable.getMessage());
                        }));
    }

    public void setBookMarkingStatus(Result result, int position, BookMarkListener listener) {
        result.bookmarked = !result.bookmarked;
        compositeDisposable.add(
                databaseService.resultDao().updateBookMarked(result)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(response -> {
                           listener.onBookMarkClicked(position,result);
                        }, throwable -> {
                        })
        );
    }
    public void getBookMarkedResults(MutableLiveData<List<Result>> bookMarkedResults){
        compositeDisposable.add(
          databaseService.resultDao().getBookMarkedResults()
                  .subscribeOn(Schedulers.io())
                  .observeOn(AndroidSchedulers.mainThread())
                  .subscribe(results -> {
                      bookMarkedResults.postValue(results);
                      Log.i("bookmarked",results.toString());
                  }, throwable -> {
                      Log.i("reason", throwable.getMessage());

                  })
        );
    }

    public void clearBookMarked(MutableLiveData<List<Result>> bookMarkedResults){
        compositeDisposable.add(
                databaseService.resultDao().clearBookMarks()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(results -> {
                            Log.i("cleared",results+"");
                            getBookMarkedResults(bookMarkedResults);
                        }, throwable -> {
                            Log.i("reason", throwable.getMessage());

                        })
        );

    }

}
