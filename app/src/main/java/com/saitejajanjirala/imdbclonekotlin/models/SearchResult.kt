package com.saitejajanjirala.imdbclonekotlin.models


data class SearchResult (
    var page: Int = 0,
    var results: List<Result>? = null,
    var total_pages: Int = 0,
    var total_results: Int = 0,
    )