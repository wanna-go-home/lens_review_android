package com.wannagohome.viewty.network

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import org.koin.core.KoinComponent
import org.koin.core.inject
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class AppRetrofitBuilder(private val baseUrl: String, private val interceptor: Interceptor? = null) : KoinComponent {

    private val interceptorManager: InterceptorManager by inject()

    fun build(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(createClient())
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build()
    }

    private fun createClient(): OkHttpClient {

        val okHttpClient = OkHttpClient.Builder()
            .addNetworkInterceptor(interceptorManager.authInterceptor)
            .addInterceptor(interceptorManager.httpLogging)
            .addNetworkInterceptor(interceptorManager.stethoInterceptor)
            .connectTimeout(NetworkConfig.ALL_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(NetworkConfig.ALL_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(NetworkConfig.ALL_TIMEOUT, TimeUnit.SECONDS)

        interceptor?.let {
            okHttpClient.addInterceptor(it)
        }

        return okHttpClient.build()
    }

}