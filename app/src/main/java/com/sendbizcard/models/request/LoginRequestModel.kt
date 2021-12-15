package com.sendbizcard.models.request

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class LoginRequestModel(

    @field:SerializedName("username")
    val username: String? = null,

    @field:SerializedName("password")
    val password: String? = null

)