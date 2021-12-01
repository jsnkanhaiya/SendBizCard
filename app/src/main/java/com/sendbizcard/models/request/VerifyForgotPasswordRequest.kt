package com.sendbizcard.models.request

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class VerifyForgotPasswordRequest(

	@field:SerializedName("contact_otp")
	val contactOtp: String? = null,

	@field:SerializedName("email")
	val email: String? = null
)
