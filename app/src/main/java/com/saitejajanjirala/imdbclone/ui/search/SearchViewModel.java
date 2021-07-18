package com.saitejajanjirala.imdbclone.ui.search;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.saitejajanjirala.imdbclone.models.SearchResult;
import com.saitejajanjirala.imdbclone.repo.IMDBRepo;

public class SearchViewModel extends ViewModel {
    IMDBRepo imdbRepo;
    MutableLiveData<SearchResult> searchResultMutableLiveData=new MutableLiveData<>();
    public SearchViewModel(IMDBRepo imdbRepo) {
        this.imdbRepo = imdbRepo;
    }

    void getData(String query){
        Log.i("called","query:-"+query);
        imdbRepo.getSearchResults(query,searchResultMutableLiveData);
    }
}
