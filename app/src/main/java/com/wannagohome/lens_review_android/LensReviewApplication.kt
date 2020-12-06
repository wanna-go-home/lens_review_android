package com.wannagohome.lens_review_android

import android.app.Application
import com.facebook.stetho.Stetho
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber


class LensReviewApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        AppComponents.init(this)

        initTimber()

        startStetho()

        startKoin()
    }

    private fun initTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    private fun startKoin() {
        startKoin {
            androidContext(this@LensReviewApplication)
            modules(koinModulesList)
        }
    }
    private fun startStetho(){
        if(BuildConfig.DEBUG){
            Stetho.initializeWithDefaults(this)
        }
    }
}