package com.saitejajanjirala.imdbclone.utils;

import android.widget.SearchView;

import io.reactivex.rxjava3.subjects.PublishSubject;

public class RxSearchObservable {

    public static PublishSubject<String> fromView(SearchView searchView) {

        final PublishSubject<String> subject = PublishSubject.create();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                subject.onComplete();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String text) {
                subject.onNext(text);
                return true;
            }
        });

        return subject;
    }
}
