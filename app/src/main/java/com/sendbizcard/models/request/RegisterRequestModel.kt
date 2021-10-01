package com.sendbizcard.models.request

import com.google.gson.annotations.SerializedName

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
