package com.sendbizcard.models.request

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class ViewCardRequest (
    @field:SerializedName("id")
    val id: String? = null,

    @field:SerializedName("theme_id")
    val theme_id: String? = null
    )