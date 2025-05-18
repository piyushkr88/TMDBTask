package com.piyush.moviedbtask.data.remote.api

import com.piyush.moviedbtask.utils.Constants
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor : Interceptor {
    private val apiKey = Constants.API_KEY
    private val bearerToken = Constants.TOKEN

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val originalUrl = originalRequest.url

        val newUrl = originalUrl.newBuilder()
            .addQueryParameter("api_key", apiKey)
            .build()

        val newRequest = originalRequest.newBuilder()
            .url(newUrl)
            .addHeader("Authorization", "Bearer $bearerToken")
            .build()

        return chain.proceed(newRequest)
    }
}
