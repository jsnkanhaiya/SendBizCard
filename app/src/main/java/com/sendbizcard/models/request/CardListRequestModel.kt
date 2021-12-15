package com.sendbizcard.models.request

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class CardListRequestModel(

	@field:SerializedName("search")
	val search: String? = null,

	/*@field:SerializedName("offset")
	val offset: Int? = null,

	@field:SerializedName("limit")
	val limit: Int? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("sort")
	val sort: String? = null*/
)
