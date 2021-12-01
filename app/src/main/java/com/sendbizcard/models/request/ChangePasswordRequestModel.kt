package com.sendbizcard.models.request

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class ChangePasswordRequestModel(

	@field:SerializedName("password")
	val password: String? = null,

	@field:SerializedName("contact_otp")
	val contactOtp: String? = null,

	@field:SerializedName("conf_password")
	val confPassword: String? = null
)
