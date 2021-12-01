package com.sendbizcard.models.request

import androidx.annotation.Keep

@Keep
data class FeedBackRequestModel(
	val name: String? = null,
	val mobile: String? = null,
	val description: String? = null,
	val email: String? = null
)

