package com.saitejajanjirala.imdbclone.models;

import com.google.gson.annotations.SerializedName;

public class BaseResponse {
    @SerializedName("status_message")
    public String statusMesssage;

    @SerializedName("status_code")
    public int statusCode;
}
