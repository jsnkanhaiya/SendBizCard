package com.sendbizcard.models.request

import com.google.gson.annotations.SerializedName

data class UpdateProfileRequest(

	@field:SerializedName("website")
	val website: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("email")
	val email: String? = null,

	@field:SerializedName("contact")
	val contact: String? = null,

	@field:SerializedName("designation")
	val designation: String? = null,

	@field:SerializedName("user_img")
	val userImg: String? = null
)
