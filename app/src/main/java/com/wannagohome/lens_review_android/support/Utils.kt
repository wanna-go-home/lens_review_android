package com.wannagohome.lens_review_android.support

import android.widget.Toast
import androidx.annotation.StringRes
import com.wannagohome.lens_review_android.AppComponents

object Utils {
    fun getString(@StringRes stringId: Int): String {
        return AppComponents.applicationContext.getString(stringId)
    }

    fun showToast(message: String) {
        Toast.makeText(AppComponents.applicationContext, message, Toast.LENGTH_SHORT).show()
    }
}