package com.sendbizcard.api

import android.util.Log
import com.sendbizcard.prefs.PreferenceSourceImpl
import okhttp3.Interceptor
import okhttp3.Response

class SessionTokenInterceptor (val preferenceSourceImpl: PreferenceSourceImpl): Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val builder = original.newBuilder()
        builder.addHeader("Connection", "close")
        builder.addHeader("Accept", "application/json")
        builder.addHeader("Content-Type", "application/json")
        builder.addHeader("Authorization", "Bearer ${preferenceSourceImpl.userToken}")
        val request = builder.method(original.method, original.body).build()
        return chain.proceed(request)
    }
}