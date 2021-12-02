package com.sendbizcard.models.response

import android.os.Parcelable
import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Keep
data class CardListResponseModel(

	@field:SerializedName("data")
	val data: DataCardDetail? = null,

	@field:SerializedName("length")
	val length: Int? = null,

	@field:SerializedName("status")
	val status: Int? = null
)

@Keep
@Parcelize
data class SocialLinksItem(

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("link")
	val link: String? = null,

	@field:SerializedName("card_detail_id")
	val cardDetailId: Int? = null,

	@field:SerializedName("id")
	val id: Int? = null
): Parcelable

@Keep
@Parcelize
data class ServicesItem(

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("card_detail_id")
	val cardDetailId: Int? = null,

	@field:SerializedName("id")
	val id: Int? = null
) : Parcelable

@Keep
data class DataCardDetail(

	@field:SerializedName("card_details")
	val cardDetails: List<CardDetailsItem>? = null
)

@Keep
@Parcelize
data class CardDetailsItem(

	@field:SerializedName("card_theme_id")
	val cardThemeId: Int? = null,

	@field:SerializedName("website")
	val website: String? = null,

	@field:SerializedName("theme_color")
	val themeColor: String? = null,

	@field:SerializedName("social_links")
	val socialLinks: List<SocialLinksItem?>? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("services")
	val services: List<ServicesItem?>? = null,

	@field:SerializedName("user_img")
	val userImg: String? = null,

	@field:SerializedName("contact_prefix")
	val contactPrefix: String? = null,

	@field:SerializedName("company_logo")
	val companyLogo: String? = null,

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("user_id")
	val userId: Int? = null,

	@field:SerializedName("contact_no")
	val contactNo: String? = null,

	@field:SerializedName("company_name")
	val companyName: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("location")
	val location: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("designation")
	val designation: String? = null,

	@field:SerializedName("email")
	val email: String? = null,

	@field:SerializedName("theme_details")
	val themeDetails: ThemeDetails? = null,

	@field:SerializedName("status")
	val status: Int? = null
) : Parcelable

@Keep
@Parcelize
data class ThemeDetails(

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
) : Parcelable

