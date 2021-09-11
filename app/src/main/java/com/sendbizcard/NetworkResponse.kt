package com.sendbizcard

import java.io.IOException

sealed class NetworkResponse<out T : Any, out U : Any> {
    /**
     * A request that resulted in a response with a 2xx status code that has a body.
     */
    data class Success<T : Any>(
        val body: T,
        val code: Int
    ) : NetworkResponse<T, Nothing>()

    /**
     * A request that resulted in a response with a non-2xx status code.
     */
    data class ServerError<U : Any>(
        val body: U?,
        val code: Int,
    ) : NetworkResponse<Nothing, U>()

    /**
     * A request that didn't result in a response.
     */
    data class NetworkError(val error: IOException) : NetworkResponse<Nothing, Nothing>()

    /**
     * A request that resulted in an error different from an IO or Server error.
     *
     * An example of such an error is JSON parsing exception thrown by a serialization library.
     */
    data class UnknownError(
        val error: Throwable,
        val code: Int? = null,
    ) : NetworkResponse<Nothing, Nothing>()
}