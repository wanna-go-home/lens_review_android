package com.wannagohome.lens_review_android.support.baseclass

import android.app.Activity
import android.content.Intent
import androidx.fragment.app.Fragment
import com.wannagohome.lens_review_android.R


open class BaseFragment : Fragment(){


    fun finishActivityToRight(activity : Activity) {
        activity.finish()
        activity.overridePendingTransition(R.anim.left_to_center, R.anim.center_to_right)
    }

    fun <T> startActivity(activity : Activity, cls : Class<T>){
        val intent = Intent(activity, cls)
        startActivity(intent)
    }

    fun <T> startActivityFromRight(activity: Activity, cls: Class<T>) {
        val intent = Intent(activity, cls)
        startActivityFromRight(intent)
    }
    fun startActivityFromRight(intent : Intent) {
        startActivity(intent)
        activity!!.overridePendingTransition(R.anim.right_to_center, R.anim.center_to_left)
    }



}