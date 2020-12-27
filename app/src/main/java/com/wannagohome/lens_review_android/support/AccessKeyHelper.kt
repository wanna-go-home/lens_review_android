package com.wannagohome.lens_review_android.support

import android.content.Context
import com.wannagohome.lens_review_android.AppComponents

object AccessKeyHelper {
    private const val KEY_TOKEN = "token"
    private const val KEY_AUTH = "auth"

    fun addToken(token: String) {
        val editor = AppComponents.applicationContext.getSharedPreferences(KEY_AUTH, Context.MODE_PRIVATE).edit()
        editor.putString(KEY_TOKEN, token)
        editor.apply()
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
    }
}