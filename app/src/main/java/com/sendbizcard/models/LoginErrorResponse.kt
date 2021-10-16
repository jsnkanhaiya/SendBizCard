package com.sendbizcard.models

import com.google.gson.annotations.SerializedName

data class LoginErrorResponse(

	@field:SerializedName("error_code")
	val errorCode: Any? = null,

	@field:SerializedName("message")
	val message: Message? = null,

	@field:SerializedName("status")
	val status: Int? = null
)

data class Message(

	@field:SerializedName("username")
	val username: List<String?>? = null
)
