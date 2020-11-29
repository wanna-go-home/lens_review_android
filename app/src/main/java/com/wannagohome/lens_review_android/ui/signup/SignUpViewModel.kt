package com.wannagohome.lens_review_android.ui.signup

import com.wannagohome.lens_review_android.network.lensapi.LensApiClient

import com.wannagohome.lens_review_android.support.baseclass.BaseViewModel
import com.wannagohome.lens_review_android.support.disposableExt.addTo
import org.koin.core.inject

class SignUpViewModel : BaseViewModel() {
    val lensApiClient: LensApiClient by inject()

    fun signUp(email: String, pw: String, pwCheck: String, phoneNumber: String, nickname: String) {
        lensApiClient.signUp(email, pw, phoneNumber, nickname)
            .subscribe {

            }.addTo(compositeDisposable)
    }
}