package com.sendbizcard.models.request

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class DeleteCardRequest(
    @field:SerializedName("id")
    val id: String? = null,
)

