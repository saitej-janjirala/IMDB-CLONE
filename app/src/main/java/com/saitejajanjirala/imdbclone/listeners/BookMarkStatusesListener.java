package com.saitejajanjirala.imdbclone.listeners;

import com.saitejajanjirala.imdbclone.models.Result;

public interface BookMarkStatusesListener {
    void onItemChanged(Result result);
    void onAllCleared(int size);
}
