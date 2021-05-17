package com.wannagohome.viewty.ui.signup

import androidx.lifecycle.MutableLiveData
import com.wannagohome.viewty.R
import com.wannagohome.viewty.extension.addTo
import com.wannagohome.viewty.network.lensapi.LensApiClient
import com.wannagohome.viewty.support.AccessKeyHelper
import com.wannagohome.viewty.support.Utils
import com.wannagohome.viewty.support.baseclass.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import timber.log.Timber
import java.util.regex.Pattern
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor() : BaseViewModel() {

    @Inject
    lateinit var lensApiClient: LensApiClient

    enum class SignUpStage {
        REQUEST_SMS_CODE,
        VERIFY_SMS_CODE,
        SIGN_UP
    }

    companion object {
        const val PIN_NUMBER_LENGTH = 6
    }

    val pwError = MutableLiveData<String>()
    val pwCheckError = MutableLiveData<String>()
    val phoneNumberError = MutableLiveData<String>()
    val verifyCodeError = MutableLiveData<String>()
    val errorToastMsg = MutableLiveData<String>()

    val signUpDone = MutableLiveData<Boolean>()

    val signUpCurrentStagePosition = MutableLiveData(0)

    val autoLogin = MutableLiveData<Boolean>()

    private var requestId = -1

    private var signUpCurrentStage = SignUpStage.REQUEST_SMS_CODE
        set(value) {
            field = value

            signUpCurrentStagePosition.value = field.ordinal
        }

    fun checkAccessKey() {
        val accessKey = AccessKeyHelper.readToken()

        Timber.d("kgp token " + accessKey)

        if (accessKey.isNotEmpty()) {
            autoLogin.value = true
        }
    }

    fun backStage() {
        signUpCurrentStage = when (signUpCurrentStage) {
            SignUpStage.SIGN_UP -> SignUpStage.VERIFY_SMS_CODE
            SignUpStage.VERIFY_SMS_CODE -> SignUpStage.REQUEST_SMS_CODE
            else -> SignUpStage.REQUEST_SMS_CODE
        }
    }

    private fun nextStage() {
        signUpCurrentStage = when (signUpCurrentStage) {
            SignUpStage.REQUEST_SMS_CODE -> SignUpStage.VERIFY_SMS_CODE
            SignUpStage.VERIFY_SMS_CODE -> SignUpStage.SIGN_UP
            SignUpStage.SIGN_UP -> SignUpStage.SIGN_UP
        }
    }

    fun requestAuthCode(phoneNumberWithHyphen: String) {

        if (!Pattern.matches("^01(?:0|1|[6-9])-(?:\\d{3}|\\d{4})-\\d{4}$", phoneNumberWithHyphen)) {
            phoneNumberError.value = Utils.getString(R.string.signup_warn_wrong_phone_number)
            return
        }
        phoneNumberError.value = ""

        val phoneNumber = phoneNumberWithHyphen.replace("-", "")

        lensApiClient.requestAuthCode(phoneNumber)
            .subscribe({
                requestId = it.body()!!.requestId

                nextStage()
            }, {
                errorToastMsg.value = Utils.getString(R.string.signup_fail_for_server)
            }).addTo(compositeDisposable)
    }

    fun verifyAuthCode(authCode: String) {
        lensApiClient.verifyAuthCode(authCode, requestId)
            .subscribe({
                verifyCodeError.value = ""

                nextStage()
            }, {
                verifyCodeError.value = Utils.getString(R.string.signup_fail_verification_code)
            }).addTo(compositeDisposable)
    }

    fun register(pass1: String, pass2: String) {
        if (pass1.length != PIN_NUMBER_LENGTH) {
            pwError.value = Utils.getString(R.string.signup_pin_length)
            return
        }
        pwError.value = ""

        if (pass2.length != PIN_NUMBER_LENGTH) {
            pwCheckError.value = Utils.getString(R.string.signup_pin_length)
            return
        }
        pwCheckError.value = ""

        if (pass1 != pass2) {
            pwCheckError.value = Utils.getString(R.string.signup_warn_different_with_pw)
            return
        }
        pwCheckError.value = ""

        lensApiClient.signUp(pass1, requestId)
            .subscribe({

                signUpDone.value = true
            }, {
                errorToastMsg.value = Utils.getString(R.string.signup_fail_for_server)
            }).addTo(compositeDisposable)


    }
}