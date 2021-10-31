package com.sendbizcard.models

import com.google.gson.annotations.SerializedName

data class ErrorResponse(

    @field:SerializedName("error_code")
    val errorCode: Int? = null,

    @field:SerializedName("message")
    val message: Message? = null,

    @field:SerializedName("status")
    val status: Int? = null
)

data class Message(

    @field:SerializedName("errors")
    val errors: List<String?>? = null,

   /* @field:SerializedName("email")
    val email: List<String?>? = null,

    @field:SerializedName("errors")
    val password: List<String?>? = null,

    @field:SerializedName("mobile")
    val mobile: List<String?>? = null,*/
)
