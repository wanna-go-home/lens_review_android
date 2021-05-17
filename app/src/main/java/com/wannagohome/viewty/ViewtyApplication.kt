package com.wannagohome.viewty

import android.app.Application
import com.facebook.stetho.Stetho
import com.google.firebase.crashlytics.ktx.crashlytics
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.HiltAndroidApp
import io.reactivex.rxjava3.exceptions.UndeliverableException
import io.reactivex.rxjava3.plugins.RxJavaPlugins
import timber.log.Timber
import java.io.IOException
import java.net.SocketException

@HiltAndroidApp
class ViewtyApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        AppComponents.init(this)

        initTimber()

        startStetho()

        setCrashlytics()

        addRxJavaExceptionHandler()
    }

    private fun setCrashlytics() {
        if (BuildConfig.DEBUG) {
            Firebase.crashlytics.setCrashlyticsCollectionEnabled(false)
        }
    }

    private fun initTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    private fun startStetho() {
        if (BuildConfig.DEBUG) {
            Stetho.initializeWithDefaults(this)
        }
    }

    private fun addRxJavaExceptionHandler() {
        RxJavaPlugins.setErrorHandler { e ->

            if (BuildConfig.DEBUG) {
                Timber.e("handle error ${e.message}")
                e.printStackTrace()
            }

            var error = e
            if (error is UndeliverableException) {
                error = e.cause
            }
            if (error is IOException || error is SocketException) {
                // fine, irrelevant network problem or API that throws on cancellation
                return@setErrorHandler
            }
            if (error is InterruptedException) {
                // fine, some blocking code was interrupted by a dispose call
                return@setErrorHandler
            }
            if (error is NullPointerException || error is IllegalArgumentException) {
                // that's likely a bug in the application
                Thread.currentThread().uncaughtExceptionHandler
                    .uncaughtException(Thread.currentThread(), error)
                return@setErrorHandler
            }
            if (error is IllegalStateException) {
                // that's a bug in RxJava or in a custom operator
                Thread.currentThread().uncaughtExceptionHandler
                    .uncaughtException(Thread.currentThread(), error)
                return@setErrorHandler
            }
            Timber.w("Undeliverable exception received, not sure what to do ${error.message}")
        }
    }
}