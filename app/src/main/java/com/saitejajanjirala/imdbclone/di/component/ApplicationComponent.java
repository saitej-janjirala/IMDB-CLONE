package com.saitejajanjirala.imdbclone.di.component;

import android.content.Context;

import com.saitejajanjirala.imdbclone.BaseApplication;
import com.saitejajanjirala.imdbclone.db.DatabaseService;
import com.saitejajanjirala.imdbclone.di.module.ApplicationModule;
import com.saitejajanjirala.imdbclone.di.qualifiers.ApplicationContext;
import com.saitejajanjirala.imdbclone.network.ApiService;
import com.saitejajanjirala.imdbclone.repo.IMDBRepo;

import javax.inject.Singleton;

import dagger.Component;
import dagger.Provides;
import io.reactivex.rxjava3.disposables.CompositeDisposable;

@Singleton
@Component(modules = {ApplicationModule.class})
public interface ApplicationComponent {

    void inject(BaseApplication application);

    ApiService providesApiService();

    CompositeDisposable providesCompositeDisposable();

    DatabaseService providesDatabaseService();

    @ApplicationContext
    Context providesContext();


}
