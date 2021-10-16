package com.sendbizcard.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ErrorBody(
    @SerializedName("status") val httpStatus: Int? = null,
    @SerializedName("username") val title: String? = null,
) : Serializable