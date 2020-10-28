package com.wannagohome.lens_review_android.ui.login

import androidx.lifecycle.MutableLiveData
import com.wannagohome.lens_review_android.network.lensapi.LensApiClient
import com.wannagohome.lens_review_android.support.basemodel.BaseViewModel
import com.wannagohome.lens_review_android.support.disposableExt.addTo
import org.koin.core.inject
import timber.log.Timber

class LoginViewModel : BaseViewModel() {

    private val lensClient: LensApiClient by inject()

    val loginSuccess = MutableLiveData<Boolean>()

    //TODO 에러처리
    fun login(id: String, pw: String) {
        lensClient.login(id,pw)
            .subscribe {
                loginSuccess.value = true
            }.addTo(compositeDisposable)
    }

}