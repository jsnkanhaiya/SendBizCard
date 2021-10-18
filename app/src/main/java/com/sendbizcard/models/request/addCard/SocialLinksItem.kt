package com.sendbizcard.models.request.addCard

import com.google.gson.annotations.SerializedName

data class SocialLinksItem(

	@field:SerializedName("name")
	var name: String? = null,

	@field:SerializedName("link")
	var link: String? = null
)