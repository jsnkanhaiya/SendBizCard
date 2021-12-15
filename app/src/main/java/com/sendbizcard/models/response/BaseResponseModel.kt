package com.sendbizcard.models.response

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class BaseResponseModel(

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: Int? = null
)
