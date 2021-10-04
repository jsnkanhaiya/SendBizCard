package com.sendbizcard.models.request

import com.google.gson.annotations.SerializedName

data class VerifyForgotPasswordRequest(

	@field:SerializedName("contact_otp")
	val contactOtp: String? = null,

	@field:SerializedName("email")
	val email: String? = null
)
