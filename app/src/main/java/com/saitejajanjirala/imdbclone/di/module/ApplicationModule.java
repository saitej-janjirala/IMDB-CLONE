package com.saitejajanjirala.imdbclone.di.module;

import android.content.Context;

import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.saitejajanjirala.imdbclone.BaseApplication;
import com.saitejajanjirala.imdbclone.db.DatabaseService;
import com.saitejajanjirala.imdbclone.di.qualifiers.ApplicationContext;
import com.saitejajanjirala.imdbclone.network.ApiService;
import com.saitejajanjirala.imdbclone.utils.Constants;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class ApplicationModule {
    private BaseApplication application;

    public ApplicationModule(BaseApplication application) {
        this.application = application;
    }

    @Provides
    @Singleton
    ApiService providesApiService() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.level(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(logging);
        return new Retrofit
                .Builder()
                .baseUrl(Constants.BASE_URL)
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build()
                .create(ApiService.class);
    }


    @ApplicationContext
    @Provides
    Context provideContext() {
        return application;
    }

    @Provides
    @Singleton
    CompositeDisposable providesCompositeDisposable() {
        return new CompositeDisposable();
    }

    @Provides
    @Singleton
    DatabaseService providesDatabaseService() {
        DatabaseService database= Room.databaseBuilder(application,DatabaseService.class,"imdb-clone-db").build();
        return database;
    }
}
