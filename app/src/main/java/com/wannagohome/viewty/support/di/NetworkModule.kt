package com.wannagohome.viewty.support.di

import com.wannagohome.viewty.network.AppRetrofitBuilder
import com.wannagohome.viewty.network.InterceptorManager
import com.wannagohome.viewty.network.NetworkConfig
import com.wannagohome.viewty.network.lensapi.LensApiClient
import com.wannagohome.viewty.network.lensapi.LensApiInterface
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