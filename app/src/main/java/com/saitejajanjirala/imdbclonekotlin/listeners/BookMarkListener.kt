package com.saitejajanjirala.imdbclonekotlin.listeners

import com.saitejajanjirala.imdbclonekotlin.models.Result

interface BookMarkListener {
    fun onBookMarkClicked(position: Int, result: Result?)
}