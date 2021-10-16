package com.sendbizcard.models

import com.google.gson.annotations.SerializedName
import com.sendbizcard.models.ErrorBody
import java.io.Serializable

/**
 * Error Handling
 */

data class ErrorsListResponse(
    @SerializedName("message") val errors: ErrorBody? = null
) : Serializable
