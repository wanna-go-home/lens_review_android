package com.wannagohome.lens_review_android

import android.content.Context

object AppComponents {

    lateinit var applicationContext: Context

    fun init(context: Context) {
        applicationContext = context
    }
}