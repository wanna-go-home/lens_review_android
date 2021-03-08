package com.wannagohome.viewty.support.baseclass

import android.app.Activity
import android.content.Intent
import androidx.fragment.app.Fragment
import com.wannagohome.viewty.R
import io.reactivex.rxjava3.disposables.CompositeDisposable


open class BaseFragment : Fragment(){

    val compositeDisposable = CompositeDisposable()

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

    override fun onDestroy() {
        super.onDestroy()
        if(!compositeDisposable.isDisposed){
            compositeDisposable.dispose()
        }
    }


}