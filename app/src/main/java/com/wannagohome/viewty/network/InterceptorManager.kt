package com.wannagohome.viewty.network

import com.facebook.stetho.okhttp3.StethoInterceptor
import com.wannagohome.viewty.support.AccessKeyHelper
import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import timber.log.Timber

class InterceptorManager {
    val httpLogging = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    val authInterceptor = AuthenticationInterceptor()

    val stethoInterceptor = StethoInterceptor()

    fun clearAuth(){
        authInterceptor.accessToken = ""
    }

    class AuthenticationInterceptor : Interceptor {

        var accessToken = ""

        override fun intercept(chain: Interceptor.Chain): Response {
            val originalRequest = chain.request()
            val url = originalRequest.url

            //TODO NON-auth list를 만들거나, non-auth api builder를 따로 만들기
            if (url.toString().contains("check") || url.toString().contains("signup") || url.toString().contains("login")) {
                return chain.proceed(originalRequest)
            }

            if (accessToken.isBlank() || accessToken.isEmpty()) {
                accessToken = AccessKeyHelper.readToken()
            }

            val newRequest = originalRequest.newBuilder()
                .addHeader("Authorization", accessToken)
                .build()

            return chain.proceed(newRequest)
        }
    }
}