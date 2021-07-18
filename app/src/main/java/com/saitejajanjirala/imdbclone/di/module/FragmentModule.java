package com.saitejajanjirala.imdbclone.di.module;

import android.content.Context;

import androidx.core.util.Supplier;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.saitejajanjirala.imdbclone.ViewModelProviderFactory;
import com.saitejajanjirala.imdbclone.di.qualifiers.FragmentContext;
import com.saitejajanjirala.imdbclone.di.scopes.FragmentScope;
import com.saitejajanjirala.imdbclone.repo.IMDBRepo;
import com.saitejajanjirala.imdbclone.ui.bookmark.BookMarkViewModel;
import com.saitejajanjirala.imdbclone.ui.home.HomeFragment;
import com.saitejajanjirala.imdbclone.ui.home.HomeViewModel;
import com.saitejajanjirala.imdbclone.ui.search.SearchViewModel;

import dagger.Module;
import dagger.Provides;
import kotlin.jvm.functions.Function0;

@Module
public class FragmentModule {
   private Fragment fragment;


    public FragmentModule(Fragment fragment) {
        this.fragment = fragment;
    }

    @FragmentScope
    @Provides
    HomeViewModel providesHomeViewModel(IMDBRepo imdbRepo){
        Supplier<HomeViewModel> supplier = () -> new HomeViewModel(imdbRepo);
        ViewModelProviderFactory<HomeViewModel> factory = new ViewModelProviderFactory<>(HomeViewModel.class, supplier);
        return new ViewModelProvider(fragment, factory).get(HomeViewModel.class);
    }

    @FragmentScope
    @Provides
    SearchViewModel providesSearchViewModel(IMDBRepo imdbRepo){
        Supplier<SearchViewModel> supplier = () -> new SearchViewModel(imdbRepo);
        ViewModelProviderFactory<SearchViewModel> factory = new ViewModelProviderFactory<>(SearchViewModel.class, supplier);
        return new ViewModelProvider(fragment, factory).get(SearchViewModel.class);
    }

    @FragmentScope
    @Provides
    BookMarkViewModel providesBookMarkModel(IMDBRepo imdbRepo){
        Supplier<BookMarkViewModel> supplier = () -> new BookMarkViewModel(imdbRepo);
        ViewModelProviderFactory<BookMarkViewModel> factory = new ViewModelProviderFactory<>(BookMarkViewModel.class, supplier);
        return new ViewModelProvider(fragment, factory).get(BookMarkViewModel.class);
    }


    @FragmentContext
    @Provides
    Context providesContext(){
        return fragment.requireContext();
    }

}
