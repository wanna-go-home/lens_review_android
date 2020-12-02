package com.wannagohome.lens_review_android.ui.login

import androidx.lifecycle.MutableLiveData
import com.wannagohome.lens_review_android.R
import com.wannagohome.lens_review_android.network.lensapi.LensApiClient
import com.wannagohome.lens_review_android.support.baseclass.BaseViewModel
import com.wannagohome.lens_review_android.extension.addTo
import com.wannagohome.lens_review_android.support.Utils
import org.koin.core.inject
import retrofit2.HttpException
import timber.log.Timber
import java.net.SocketTimeoutException

class LoginViewModel : BaseViewModel() {

    private val lensClient: LensApiClient by inject()

    val loginSuccess = MutableLiveData<Boolean>()

    val errMessage = MutableLiveData<String>()

    //TODO 에러처리
    fun login(id: String, pw: String) {
        lensClient.login(id, pw)
            .subscribe({
                loginSuccess.value = true
            }, {

                errMessage.value = when(it){
                    is HttpException -> Utils.getString(R.string.login_fail_wrong_input)
                    else -> Utils.getString(R.string.login_fail_for_server) //TODO 서버로 로그전송
                }

            }).addTo(compositeDisposable)
    }

}