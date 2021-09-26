package com.saitejajanjirala.imdbclonekotlin.listeners

import com.saitejajanjirala.imdbclonekotlin.models.Result

interface BookMarkStatusesListener {
    fun onItemChanged(result: Result?)
    fun onAllCleared(size: Int)
}