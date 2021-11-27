package com.sendbizcard.models

import com.google.gson.annotations.SerializedName

data class ErrorResponse(

    @field:SerializedName("error_code")
    val errorCode: Any? = null,

    @field:SerializedName("message")
    val message: Message? = null,

    @field:SerializedName("status")
    val status: Int? = null
)

data class Message(

    @field:SerializedName("errors")
    val errors: List<String?>? = null,

    @field:SerializedName("email")
    val email: List<String?>? = null,

    @field:SerializedName("contact")
    val contact: List<String?>? = null

)
