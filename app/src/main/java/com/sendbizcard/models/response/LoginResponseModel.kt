package com.sendbizcard.models.response

import com.google.gson.annotations.SerializedName

data class LoginResponseModel(

	@field:SerializedName("expires")
	val expires: Int? = null,

	@field:SerializedName("error_code")
	val errorCode: Any? = null,

	@field:SerializedName("status")
	val status: Int? = null,

	@field:SerializedName("token")
	val token: String? = null,

	@field:SerializedName("data")
val data: Data? = null,

)
