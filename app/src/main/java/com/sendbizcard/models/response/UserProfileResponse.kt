package com.sendbizcard.models.response

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class UserProfileResponse(

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("user")
	val user: User? = null,

	@field:SerializedName("status")
	val status: Int? = null
)

@Keep
data class User(

	@field:SerializedName("website")
	val website: String? = null,

	@field:SerializedName("otp_expiry_time")
	val otpExpiryTime: String? = null,

	@field:SerializedName("contact_verified_at")
	val contactVerifiedAt: String? = null,

	@field:SerializedName("last_login")
	val lastLogin: Any? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("user_img")
	val userImg: String? = null,

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("total_count_sms")
	val totalCountSms: Int? = null,

	@field:SerializedName("contact")
	val contact: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("contact_otp")
	val contactOtp: Any? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("designation")
	val designation: String? = null,

	@field:SerializedName("email")
	val email: String? = null,

	@field:SerializedName("status")
	val status: Int? = null
)
