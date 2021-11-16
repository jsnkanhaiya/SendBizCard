package com.sendbizcard.models.request.addCard

import com.google.gson.annotations.SerializedName

data class AddCardRequest(

	@field:SerializedName("theme_color")
	var themeColor: String? = null,

	@field:SerializedName("website")
	var website: String? = null,

	@field:SerializedName("social_links")
	var socialLinks: List<SocialLinksItem?>? = null,

	@field:SerializedName("theme_id")
	var themeId: String? = null,

	@field:SerializedName("services")
	var services: List<ServicesItem?>? = null,

	@field:SerializedName("user_img")
	var userImg: String? = null,

	@field:SerializedName("contact_prefix")
	var contactPrefix: String? = null,

	@field:SerializedName("company_logo")
	var companyLogo: String? = null,

	@field:SerializedName("contact_no")
	var contactNo: String? = null,

	@field:SerializedName("company_name")
	var companyName: String? = null,

	@field:SerializedName("name")
	var name: String? = null,

	@field:SerializedName("location")
	var location: String? = null,

	@field:SerializedName("designation")
	var designation: String? = null,

	@field:SerializedName("email")
	var email: String? = null,

	@field:SerializedName("id")
	var id: Int? = null
)