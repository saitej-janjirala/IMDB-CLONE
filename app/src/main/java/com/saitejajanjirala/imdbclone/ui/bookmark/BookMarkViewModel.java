package com.saitejajanjirala.imdbclone.ui.bookmark;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.saitejajanjirala.imdbclone.models.Result;
import com.saitejajanjirala.imdbclone.repo.IMDBRepo;
import com.saitejajanjirala.imdbclone.listeners.BookMarkListener;

import java.util.List;

public class BookMarkViewModel extends ViewModel {
    private IMDBRepo imdbRepo;
    MutableLiveData<List<Result>> bookMarkResults = new MutableLiveData<>();

    public BookMarkViewModel(IMDBRepo imdbRepo) {
        this.imdbRepo = imdbRepo;
        getData();
    }
    void getData(){
        imdbRepo.getBookMarkedResults(bookMarkResults);
    }

    void setBookMarkStatus(Result result, int position, BookMarkListener listener) {
        imdbRepo.setBookMarkingStatus(result, position, listener);
    }

    void clearBookMarks(){
        imdbRepo.clearBookMarked(bookMarkResults);
    }


}
