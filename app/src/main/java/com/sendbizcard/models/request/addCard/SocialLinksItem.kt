package com.sendbizcard.models.request.addCard

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class SocialLinksItem(

	@field:SerializedName("name")
	var name: String? = null,

	@field:SerializedName("link")
	var link: String? = null
)