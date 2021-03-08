package com.wannagohome.viewty.support.baseclass

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import com.wannagohome.viewty.R
import org.koin.core.KoinComponent


open class BaseAppCompatActivity : AppCompatActivity(), KoinComponent {


    fun finishActivityToRight() {
        finish()
        overridePendingTransition(R.anim.left_to_center, R.anim.center_to_right)
    }

    fun <T> startActivity(activity: Context, cls: Class<T>) {
        val intent = Intent(activity, cls)
        startActivity(intent)
    }

    fun startActivityFromRight(intent: Intent) {
        startActivity(intent)
        overridePendingTransition(R.anim.right_to_center, R.anim.center_to_left)
    }

    fun <T> startActivityFromRight(activity: Context, cls: Class<T>) {
        val intent = Intent(activity, cls)
        startActivityFromRight(intent)
    }
}