package com.wannagohome.viewty

import android.content.Context

object AppComponents {

    lateinit var applicationContext: Context

    fun init(context: Context) {
        applicationContext = context
    }
}