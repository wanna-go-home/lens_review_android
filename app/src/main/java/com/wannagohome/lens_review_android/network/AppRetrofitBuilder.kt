package com.wannagohome.lens_review_android.network

import com.wannagohome.lens_review_android.support.AccessKeyHelper
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import java.util.concurrent.TimeUnit

class AppRetrofitBuilder(private val baseUrl: String, private val interceptor: Interceptor? = null) {

    fun build(): Retrofit {

        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(createClient())
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build()
    }

    private fun createClient(): OkHttpClient {
        val httpLogging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(httpLogging)
            .addNetworkInterceptor(AuthenticationInterceptor())
//            .addNetworkInterceptor(StethoInterceptor())
            .connectTimeout(NetworkConfig.ALL_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(NetworkConfig.ALL_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(NetworkConfig.ALL_TIMEOUT, TimeUnit.SECONDS)

        interceptor?.let {
            okHttpClient.addInterceptor(it)
        }

        return okHttpClient.build()
    }
    class AuthenticationInterceptor : Interceptor {

        private var accessToken = ""

        override fun intercept(chain: Interceptor.Chain): Response {
            val originalRequest = chain.request()
            val url = originalRequest.url

            if (url.toString().contains("user")) {
                return chain.proceed(originalRequest)
            }

            if (accessToken.isBlank() || accessToken.isEmpty()) {
                accessToken = AccessKeyHelper.readToken()
            }

            val newRequest = originalRequest.newBuilder()
                .addHeader("authorization", accessToken)
                .build()
            Timber.d("my token!! $accessToken")
            return chain.proceed(newRequest)
        }
    }
}