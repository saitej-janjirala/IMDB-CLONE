package com.saitejajanjirala.imdbclone.di.component;

import android.content.Context;

import com.saitejajanjirala.imdbclone.di.module.FragmentModule;
import com.saitejajanjirala.imdbclone.di.qualifiers.FragmentContext;
import com.saitejajanjirala.imdbclone.di.scopes.FragmentScope;
import com.saitejajanjirala.imdbclone.ui.bookmark.BookMarkFragment;
import com.saitejajanjirala.imdbclone.ui.home.HomeFragment;
import com.saitejajanjirala.imdbclone.ui.search.SearchFragment;

import dagger.Component;

@FragmentScope
@Component(
        dependencies = {ApplicationComponent.class},
        modules = {FragmentModule.class}
)
public interface FragmentComponent {
    void inject(HomeFragment homeFragment);
    void inject(SearchFragment searchFragment);
    void inject(BookMarkFragment bookMarkFragment);

    @FragmentContext
    Context providesContext();
}
