package com.sendbizcard.models.request

import com.google.gson.annotations.SerializedName

data class DeleteCardRequest(
    @field:SerializedName("id")
    val id: String? = null,
)

