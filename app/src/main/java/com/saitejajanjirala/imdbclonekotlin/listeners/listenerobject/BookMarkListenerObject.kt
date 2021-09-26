package com.saitejajanjirala.imdbclonekotlin.listeners.listenerobject

import com.saitejajanjirala.imdbclonekotlin.listeners.BookMarkStatusesListener
import java.io.Serializable

class BookMarkListenerObject(listener: BookMarkStatusesListener) : Serializable {
    var listener: BookMarkStatusesListener = listener

}