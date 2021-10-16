package com.sendbizcard.models.response.theme

import com.google.gson.annotations.SerializedName

data class ThemeResponse(

	@field:SerializedName("data")
	val data: Data? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: Int? = null
)

data class Data(

	@field:SerializedName("list_theme")
	val listTheme: List<ListThemeItem>? = null
)

data class ListThemeItem(

	@field:SerializedName("card_img")
	val cardImg: String? = null,

	@field:SerializedName("card_name")
	val cardName: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("is_default")
	val isDefault: Int? = null,

	@field:SerializedName("card_slug")
	val cardSlug: String? = null
)
