package com.wannagohome.lens_review_android.support.di

import com.wannagohome.lens_review_android.network.AppRetrofitBuilder
import com.wannagohome.lens_review_android.network.InterceptorManager
import com.wannagohome.lens_review_android.network.NetworkConfig
import com.wannagohome.lens_review_android.network.lensapi.LensApiClient
import com.wannagohome.lens_review_android.network.lensapi.LensApiInterface
import org.koin.dsl.module

val networkModule = module {

    single {
        LensApiClient(
            AppRetrofitBuilder(NetworkConfig.API_BASE_URL)
                .build()

                .create(LensApiInterface::class.java)
        )
    }

    single { InterceptorManager() }
}