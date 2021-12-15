package com.sendbizcard.models.request

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class RegisterRequestModel(

	@field:SerializedName("password")
	val password: String? = null,

	@field:SerializedName("contact")
	val contact: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("email")
	val email: String? = null,

	@field:SerializedName("conf_password")
	val confPassword: String? = null
)
