package com.sendbizcard.models.request

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class VerifyOtpRequest(
    @field:SerializedName("otp")
    val otp: String? = null
)
