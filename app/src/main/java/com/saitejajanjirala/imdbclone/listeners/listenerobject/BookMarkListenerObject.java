package com.saitejajanjirala.imdbclone.listeners.listenerobject;

import com.saitejajanjirala.imdbclone.listeners.BookMarkListener;
import com.saitejajanjirala.imdbclone.listeners.BookMarkStatusesListener;

import java.io.Serializable;

public class BookMarkListenerObject implements Serializable {
    public BookMarkStatusesListener listener;

    public BookMarkListenerObject(BookMarkStatusesListener listener) {
        this.listener = listener;
    }
}
