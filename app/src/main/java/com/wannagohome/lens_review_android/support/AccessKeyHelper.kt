package com.wannagohome.lens_review_android.support

import android.content.Context
import com.wannagohome.lens_review_android.AppComponents

object AccessKeyHelper {

    public fun addToken(token: String) {
        val editor = AppComponents.applicationContext.getSharedPreferences("auth", Context.MODE_PRIVATE).edit()
        editor.putString("token", token)
        editor.apply()
    }

    public fun readToken(): String {

        val preference = AppComponents.applicationContext.getSharedPreferences(
            "auth",
            Context.MODE_PRIVATE
        )

        return preference.getString("token", "") ?: ""
    }
}