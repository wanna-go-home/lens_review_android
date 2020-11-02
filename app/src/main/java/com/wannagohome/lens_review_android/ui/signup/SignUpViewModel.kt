package com.wannagohome.lens_review_android.ui.signup

import com.wannagohome.lens_review_android.network.lensapi.LensApiClient
import com.wannagohome.lens_review_android.support.basemodel.BaseViewModel
import org.koin.core.inject

class SignUpViewModel :BaseViewModel(){
    val lensApiClient : LensApiClient by inject()

    fun signUp(email : String, pw : String, pwCheck:String, nickname:String){

    }
}