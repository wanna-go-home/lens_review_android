package com.wannagohome.viewty.ui.signup

import androidx.lifecycle.MutableLiveData
import com.wannagohome.viewty.R
import com.wannagohome.viewty.extension.addTo
import com.wannagohome.viewty.network.lensapi.LensApiClient
import com.wannagohome.viewty.support.Utils
import com.wannagohome.viewty.support.baseclass.BaseViewModel
import io.reactivex.rxjava3.core.Observable
import org.koin.core.inject
import java.util.regex.Pattern

class SignUpViewModel : BaseViewModel() {
    private val lensApiClient: LensApiClient by inject()

    enum class SignUpStage {
        REQUEST_SMS_CODE,
        VERIFY_SMS_CODE,
        SIGN_UP
    }

    val emailWarn = MutableLiveData<String>()
    val pwWarn = MutableLiveData<String>()
    val pwCheckWarn = MutableLiveData<String>()
    val phoneNumberWarn = MutableLiveData<String>()
    val nicknameWarn = MutableLiveData<String>()

    val signUpResult = MutableLiveData<String>()

    val errMessage = MutableLiveData<String>()

    val signUpCurrentStagePosition = MutableLiveData(0)

    private var requestId = -1

    private var signUpCurrentStage = SignUpStage.REQUEST_SMS_CODE
        set(value) {
            field = value

            signUpCurrentStagePosition.value = field.ordinal
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
        if(!Pattern.matches("^01(?:0|1|[6-9])-(?:\\d{3}|\\d{4})-\\d{4}$", phoneNumberWithHyphen))
        {
            phoneNumberWarn.value = Utils.getString(R.string.signup_warn_wrong_phone_number)
            return
        }
        phoneNumberWarn.value = ""
        val phoneNumber = phoneNumberWithHyphen.replace("-","")
        lensApiClient.requestAuthCode(phoneNumber)
            .subscribe({
                requestId = it.body()!!.requestId
                nextStage()
            }, {
                errMessage.value = Utils.getString(R.string.signup_fail_for_server)
            }).addTo(compositeDisposable)
    }

    fun verifyAuthCode(authCode: String) {
        lensApiClient.verifyAuthCode(authCode, requestId)
            .subscribe({
                nextStage()
            }, {
                errMessage.value = Utils.getString(R.string.signup_fail_for_server)
            }).addTo(compositeDisposable)
    }

    fun register(pass1: String, pass2: String) {
        if (pass1!=pass2){
            pwCheckWarn.value = Utils.getString(R.string.signup_warn_different_with_pw)
            return
        }
        else{
            lensApiClient.signUp(pass1, requestId)
                .subscribe({
                    signUpResult.value = Utils.getString(R.string.signup_success)
                }, {
                    errMessage.value = Utils.getString(R.string.signup_fail_for_server)
                }).addTo(compositeDisposable)
        }

    }


    private fun isValidEmail(email: String): Boolean {
        if (email.isEmpty() || email.isBlank()) {
            emailWarn.value = Utils.getString(R.string.signup_warn_empty_email)
            return false
        }

        val reg = Regex("[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*\\.[a-zA-Z]{2,3}")
        if (!email.matches(reg)) {
            emailWarn.value = Utils.getString(R.string.signup_warn_not_email_form)
            return false
        }
        return true
    }

    fun checkEmail(email: String) {

        if (!isValidEmail(email)) {
            return
        }

        lensApiClient.checkSameId(email)
            .subscribe({
                val available = it.body()!!.available

                if (available) {
                    emailWarn.value = ""
                } else {
                    emailWarn.value = Utils.getString(R.string.signup_warn_duplicate_email)
                }

            }, {
                errMessage.value = Utils.getString(R.string.signup_fail_for_server)
            }).addTo(compositeDisposable)
    }

    fun isValidPw(pw: String): Boolean {
        if (pw.isEmpty() || pw.isBlank()) {
            pwWarn.value = Utils.getString(R.string.signup_warn_empty_pw)
            return false
        }

        val reg = Regex("[0-9|a-zA-Z|!|@|#]{6,15}")
        if (pw.length < 6 || !pw.matches(reg)) {
            pwWarn.value = Utils.getString(R.string.signup_warn_not_acceptable_pw)
            return false
        }
        pwWarn.value = ""

        return true
    }

    fun isValidPwCheck(pw: String, pwCheck: String): Boolean {
        if (pw != pwCheck) {
            pwCheckWarn.value = Utils.getString(R.string.signup_warn_different_with_pw)
            return false
        }
        pwCheckWarn.value = ""
        return true
    }

//    fun checkPhoneNumber(phoneNumber: String) {
//        if (!isValidPhoneNumber(phoneNumber)) {
//            return
//        }
//
//        lensApiClient.checkSamePhoneNumber(phoneNumber)
//            .subscribe({
//                val duplicate = it.body()!!
//
//                if (duplicate.available) {
//                    phoneNumberWarn.value = ""
//                } else {
//                    phoneNumberWarn.value = Utils.getString(R.string.signup_warn_duplicate_phone_number)
//                }
//            }, {
//                errMessage.value = Utils.getString(R.string.signup_fail_for_server)
//            })
//    }

    private fun isValidNickname(nickname: String): Boolean {
        if (nickname.isEmpty() || nickname.isBlank()) {
            nicknameWarn.value = Utils.getString(R.string.signup_warn_empty_nickname)
            return false
        }
        return true
    }

    fun nicknameCheck(nickname: String) {
        if (!isValidNickname(nickname)) {
            return
        }

        lensApiClient.checkSameNickname(nickname)
            .subscribe({
                val available = it.body()!!.available
                if (available) {
                    nicknameWarn.value = ""
                } else {
                    nicknameWarn.value = Utils.getString(R.string.signup_warn_duplicate_nickname)
                }

            }, {
                errMessage.value = Utils.getString(R.string.signup_fail_for_server)
            }).addTo(compositeDisposable)

    }

//    private fun checkAll(email: String, pw: String, pwCheck: String, phoneNumber: String, nickname: String): Observable<Boolean> {
//        val validEmail = isValidEmail(email)
//        val validPw = isValidPw(pw)
//        val validPwCheck = isValidPwCheck(pw, pwCheck)
//        val validPhoneNumber = isValidPhoneNumber(phoneNumber)
//        val validNickname = isValidNickname(nickname)
//
//        return Observable.just(
//            validEmail &&
//                    validPw &&
//                    validPwCheck &&
//                    validPhoneNumber &&
//                    validNickname
//        )
//    }

//    fun signUp(email: String, pw: String, pwCheck: String, phoneNumber: String, nickname: String) {
//
//        checkAll(email, pw, pwCheck, phoneNumber, nickname)
//            .filter { it }
//            .flatMap {
//                Observable.zip(lensApiClient.checkSameId(email).map { it.body() }, lensApiClient.checkSameNickname(nickname).map { it.body() },
//                    lensApiClient.checkSamePhoneNumber(phoneNumber).map { it.body() })
//                { emailAvailable: CheckDuplicateResponse, nicknameAvailable: CheckDuplicateResponse, phoneNumberAvailable: CheckDuplicateResponse ->
//                    if (!emailAvailable.available) {
//                        emailWarn.value = Utils.getString(R.string.signup_warn_duplicate_email)
//                    }
//                    if (!nicknameAvailable.available) {
//                        nicknameWarn.value = Utils.getString(R.string.signup_warn_duplicate_nickname)
//                    }
//                    if (!phoneNumberAvailable.available) {
//                        phoneNumberWarn.value = Utils.getString(R.string.signup_warn_duplicate_phone_number)
//                    }
//                    emailAvailable.available && nicknameAvailable.available && phoneNumberAvailable.available
//                }
//            }
//            .doOnError { errMessage.value = Utils.getString(R.string.signup_fail_for_server) }
//            .onErrorReturn { false }
//            .subscribe { canSignUp ->
//                if (canSignUp) {
//                    callSignUp(email, pw, phoneNumber, nickname)
//                }
//            }
//            .addTo(compositeDisposable)
//    }

//    private fun callSignUp(email: String, pw: String, phoneNumber: String, nickname: String) {
//        lensApiClient.signUp(email, pw, phoneNumber, nickname)
//            .subscribe({
//                signUpResult.value = Utils.getString(R.string.signup_success)
//            }, {
//
//                errMessage.value = when (it) {
//                    is SocketTimeoutException -> Utils.getString(R.string.login_fail_for_server)
//                    is HttpException -> Utils.getString(R.string.signup_fail_wrong_input)
//                    else -> "" //TODO 서버로 로그전송
//                }
//
//            }).addTo(compositeDisposable)
//    }
}