package com.sendbizcard.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ErrorBody(
    @SerializedName("http_code") val httpCode: Int? = null,
    @SerializedName("title") val title: String? = null,
    @SerializedName("details") val details: String? = null,
    @SerializedName("code") val code: Int? = null,
    @SerializedName("detail") val detail: String? = null
) : Serializable