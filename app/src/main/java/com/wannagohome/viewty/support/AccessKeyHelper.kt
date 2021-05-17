package com.wannagohome.viewty.support

import android.content.Context
import com.wannagohome.viewty.AppComponents
import com.wannagohome.viewty.network.interceptorManager


object AccessKeyHelper {
    private const val KEY_TOKEN = "token"
    private const val KEY_AUTH = "auth"

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