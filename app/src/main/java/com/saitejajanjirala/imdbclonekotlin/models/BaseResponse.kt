package com.saitejajanjirala.imdbclonekotlin.models

import com.google.gson.annotations.SerializedName

open class BaseResponse(
    @SerializedName("status_message")
    var statusMesssage: String? = null,
    @SerializedName("status_code")
    var statusCode: Int = 0
)