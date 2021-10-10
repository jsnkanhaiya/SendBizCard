package com.sendbizcard.models.request

import com.google.gson.annotations.SerializedName

data class VerifyOtpRequest(
    @field:SerializedName("otp")
    val otp: String? = null
)
