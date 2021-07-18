package com.saitejajanjirala.imdbclone;

import android.app.Application;

import com.saitejajanjirala.imdbclone.di.component.ApplicationComponent;
import com.saitejajanjirala.imdbclone.di.component.DaggerApplicationComponent;
import com.saitejajanjirala.imdbclone.di.module.ApplicationModule;
import com.saitejajanjirala.imdbclone.repo.IMDBRepo;

import javax.inject.Inject;

public class BaseApplication extends Application {
    public ApplicationComponent applicationComponent;


    @Override
    public void onCreate() {
        super.onCreate();
        injectDependencies();
    }

    private void injectDependencies() {
        applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
        applicationComponent.inject(this);
    }
}
