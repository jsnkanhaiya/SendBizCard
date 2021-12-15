package com.sendbizcard.models

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class ErrorResponse(

    @field:SerializedName("error_code")
    val errorCode: Any? = null,

    @field:SerializedName("message")
    val message: Message? = null,

    @field:SerializedName("status")
    val status: Int? = null
)

@Keep
data class Message(

    @field:SerializedName("errors")
    val errors: List<String?>? = null,

    @field:SerializedName("email")
    val email: List<String?>? = null,

    @field:SerializedName("contact")
    val contact: List<String?>? = null,

    @field:SerializedName("id")
    val id: List<String?>? = null,

    @field:SerializedName("username")
    val username: List<String?>? = null,

    @field:SerializedName("password")
    val password: List<String?>? = null,

    @field:SerializedName("otp")
    val otp: List<String?>? = null

)
