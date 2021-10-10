package com.sendbizcard.models.response

import com.google.gson.annotations.SerializedName

data class BaseResponseModel(

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: Int? = null
)
