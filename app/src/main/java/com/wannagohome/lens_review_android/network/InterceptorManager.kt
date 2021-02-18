package com.wannagohome.lens_review_android.network

import com.facebook.stetho.okhttp3.StethoInterceptor
import com.wannagohome.lens_review_android.support.AccessKeyHelper
import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor

class InterceptorManager {
    val httpLogging = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    val autoInterceptor = AuthenticationInterceptor()

    val stethoInterceptor = StethoInterceptor()

    fun clearAuth(){
        autoInterceptor.accessToken = ""
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
                .addHeader("authorization", accessToken)
                .build()
            return chain.proceed(newRequest)
        }
    }
}