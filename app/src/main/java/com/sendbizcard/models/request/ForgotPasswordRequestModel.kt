package com.sendbizcard.models.request

import com.google.gson.annotations.SerializedName

data class ForgotPasswordRequestModel(

	@field:SerializedName("email")
	val email: String? = null
)
