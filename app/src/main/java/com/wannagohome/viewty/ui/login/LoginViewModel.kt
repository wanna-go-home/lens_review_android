package com.wannagohome.viewty.ui.login

import androidx.lifecycle.MutableLiveData
import com.wannagohome.viewty.R
import com.wannagohome.viewty.network.lensapi.LensApiClient
import com.wannagohome.viewty.support.baseclass.BaseViewModel
import com.wannagohome.viewty.extension.addTo
import com.wannagohome.viewty.support.AccessKeyHelper
import com.wannagohome.viewty.support.Utils
import io.reactivex.rxjava3.core.Observable
import org.koin.core.inject
import retrofit2.HttpException

class LoginViewModel : BaseViewModel() {

    private val lensClient: LensApiClient by inject()

    val loginSuccess = MutableLiveData<Boolean>()

    val errMessage = MutableLiveData<String>()

    val emailWarn = MutableLiveData<String>()

    val passwordWarn = MutableLiveData<String>()

    //TODO 에러처리
    fun login(email: String, pw: String) {
        val emailResult = Observable.just(isValidEmail(email))
        val pwResult = Observable.just(isValidPassword(pw))

        Observable.zip(emailResult, pwResult, { e, p ->
            e && p
        })
            .filter { it }
            .flatMap {
                lensClient.login(email, pw)

            }
            .subscribe({
                loginSuccess.value = true
            }, {

                errMessage.value = when (it) {

                    is HttpException ->{
                        if(it.code() == 401){
                            Utils.getString(R.string.login_fail_wrong_input)
                        }
                        else{
                            Utils.getString(R.string.login_fail_for_server)
                        }
                     }

                    else -> Utils.getString(R.string.login_fail_for_server) //TODO 서버로 로그전송
                }



            }).addTo(compositeDisposable)


    }

    fun isValidEmail(email: String): Boolean {
        if (email.isEmpty()) {
            emailWarn.value = Utils.getString(R.string.login_email_empty)
            return false
        }

        val reg = Regex("[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*\\.[a-zA-Z]{2,3}")
        if (!email.matches(reg)) {
            emailWarn.value = Utils.getString(R.string.login_email_invalid)
            return false
        }

        emailWarn.value = ""
        return true
    }

    fun isValidPassword(pw: String): Boolean {
        if (pw.isEmpty()) {
            passwordWarn.value = Utils.getString(R.string.login_pw_empty)
            return false
        }

        //TODO 비밀번호 조건 어떻게 할것이지?
        val reg = Regex("[0-9|a-zA-Z|!|@|#]{6,15}")
        if (pw.length < 5 || !pw.matches(reg)) {
            passwordWarn.value = Utils.getString(R.string.login_pw_invalid)
            return false
        }

        passwordWarn.value = ""
        return true
    }

    fun checkAccessKey(){
        val accessKey = AccessKeyHelper.readToken()

        if(accessKey.isNotEmpty()){
            loginSuccess.value = true
        }
    }

}