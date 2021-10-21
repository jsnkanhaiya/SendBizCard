package com.sendbizcard.models.response

import com.google.gson.annotations.SerializedName

data class ViewCardResponse(

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("redirect_url")
	val redirectUrl: String? = null,

	@field:SerializedName("status")
	val status: Int? = null
)
