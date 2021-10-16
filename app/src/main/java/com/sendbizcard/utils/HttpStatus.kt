package com.sendbizcard.utils

import androidx.annotation.Keep

enum class HttpStatus(val code: Int, val reasonPhrase: String) {
    OK(200, "OK"),
    BAD_REQUEST(400, "Bad Request"),
    UNAUTHORIZED(401, "Unauthorized"),
    FORBIDDEN(403, "Forbidden"),
    NOT_FOUND(404, "Not Found"),
    DEFAULT(-66, "An unexpected error occurred \nSorry for the inconvenience."), // custom
}