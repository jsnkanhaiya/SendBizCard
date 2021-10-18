package com.sendbizcard.models.request.addCard

import com.google.gson.annotations.SerializedName

data class ServicesItem(

	@field:SerializedName("name")
	var name: String? = null
)