package com.saitejajanjirala.imdbclone.ui.home;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.saitejajanjirala.imdbclone.listeners.BookMarkListener;
import com.saitejajanjirala.imdbclone.models.Result;
import com.saitejajanjirala.imdbclone.repo.IMDBRepo;

import java.util.List;

public class HomeViewModel extends ViewModel {
    IMDBRepo imdbRepo;
    MutableLiveData<List<Result>> nowPlayingMutableLiveData=new MutableLiveData<>();
    MutableLiveData<List<Result>> popularMutableLiveData=new MutableLiveData<>();
    MutableLiveData<Result> resultMutableLiveData=new MutableLiveData<>();
    MutableLiveData<Boolean> noInternet=new MutableLiveData<>();
    public HomeViewModel(IMDBRepo imdbRepo) {
        this.imdbRepo = imdbRepo;
        getData();
    }

    void getData(){
        getNowPlaying();
        getPopular();
    }
    void getNowPlaying(){
        imdbRepo.getNowPlaying(nowPlayingMutableLiveData);
    }
    void getPopular(){
        imdbRepo.getPopular(popularMutableLiveData);

    }
    void setBookMarkStatus(Result result, int position, BookMarkListener listener){
        imdbRepo.setBookMarkingStatus(result, position, listener);
    }


}