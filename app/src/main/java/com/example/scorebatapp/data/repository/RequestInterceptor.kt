package com.example.scorebatapp.data.repository

import com.example.scorebatapp.data.matchesremote.ApiMatchesReference.AUTH_HEADER
import com.example.scorebatapp.data.matchesremote.ApiMatchesReference.AUTH_TOKEN
import okhttp3.Interceptor
import okhttp3.Response

class RequestInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
            .newBuilder()
            .addHeader(AUTH_HEADER, AUTH_TOKEN)
            .build()

        return chain.proceed(request)
    }
}