package com.sendbizcard.models.request

import com.google.gson.annotations.SerializedName

data class LoginRequestModel (
    @field:SerializedName("username")
    val username: String? = null,
    @field:SerializedName("password")
    val password: String? = null

        )