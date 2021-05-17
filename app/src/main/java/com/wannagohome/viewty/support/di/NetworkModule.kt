package com.wannagohome.viewty.support.di

import com.wannagohome.viewty.network.AppRetrofitBuilder
import com.wannagohome.viewty.network.NetworkConfig
import com.wannagohome.viewty.network.lensapi.LensApiClient
import com.wannagohome.viewty.network.lensapi.LensApiInterface
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {
    @Provides
    @Singleton
    fun provideLensApiClient(): LensApiClient {
        return LensApiClient(
            AppRetrofitBuilder(NetworkConfig.API_BASE_URL)
                .build()
                .create(LensApiInterface::class.java)
        )
    }
}