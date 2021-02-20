package com.wannagohome.lens_review_android.support

import android.content.Context
import com.wannagohome.lens_review_android.AppComponents
import com.wannagohome.lens_review_android.network.InterceptorManager
import org.koin.core.KoinComponent
import org.koin.core.inject

object AccessKeyHelper : KoinComponent {
    private const val KEY_TOKEN = "token"
    private const val KEY_AUTH = "auth"
    private val interceptorManager: InterceptorManager by inject()

    fun addToken(token: String) {
        val editor = AppComponents.applicationContext.getSharedPreferences(KEY_AUTH, Context.MODE_PRIVATE).edit()
        editor.putString(KEY_TOKEN, token)
        editor.commit()
    }

    fun readToken(): String {

        val preference = AppComponents.applicationContext.getSharedPreferences(
            KEY_AUTH,
            Context.MODE_PRIVATE
        )
        return preference.getString(KEY_TOKEN, "") ?: ""
    }

    fun deleteToken() {
        val editor = AppComponents.applicationContext.getSharedPreferences(KEY_AUTH, Context.MODE_PRIVATE).edit()
        editor.remove(KEY_TOKEN)
        editor.apply()
        interceptorManager.clearAuth()
    }

    fun deleteTokenSync() {
        val editor = AppComponents.applicationContext.getSharedPreferences(KEY_AUTH, Context.MODE_PRIVATE).edit()
        editor.remove(KEY_TOKEN)
        editor.commit()
        interceptorManager.clearAuth()
    }
}