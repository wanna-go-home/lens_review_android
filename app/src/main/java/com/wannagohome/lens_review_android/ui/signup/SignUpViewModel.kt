package com.wannagohome.lens_review_android.ui.signup

import androidx.lifecycle.MutableLiveData
import com.wannagohome.lens_review_android.R
import com.wannagohome.lens_review_android.extension.addTo
import com.wannagohome.lens_review_android.network.lensapi.LensApiClient
import com.wannagohome.lens_review_android.network.model.user.CheckDuplicateResponse
import com.wannagohome.lens_review_android.support.Utils
import com.wannagohome.lens_review_android.support.baseclass.BaseViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.PublishSubject
import org.koin.core.inject
import retrofit2.HttpException
import timber.log.Timber
import java.net.SocketTimeoutException

class SignUpViewModel : BaseViewModel() {
    private val lensApiClient: LensApiClient by inject()

    val emailWarn = MutableLiveData<String>()
    val pwWarn = MutableLiveData<String>()
    val pwCheckWarn = MutableLiveData<String>()
    val phoneNumWarn = MutableLiveData<String>()
    val nicknameWarn = MutableLiveData<String>()

    val signUpResult = MutableLiveData<String>()

    val errMessage = MutableLiveData<String>()

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
                emailWarn.value = ""
            }, {
                emailWarn.value = when (it) {
                    is HttpException -> Utils.getString(R.string.signup_warn_duplicate_email)
                    else -> ""
                }
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

    private fun isValidPhoneNumber(phoneNum: String): Boolean {
        if (phoneNum.length != 11) {
            phoneNumWarn.value = Utils.getString(R.string.signup_warn_wrong_phone_number)
            return false
        }
        phoneNumWarn.value = ""
        return true
    }

    fun checkPhoneNumber(phoneNumber: String) {
        if (!isValidPhoneNumber(phoneNumber)) {
            return
        }

        lensApiClient.checkSamePhoneNumber(phoneNumber)
            .subscribe({
                val duplicate = it.body()!!

                if (duplicate.available) {
                    phoneNumWarn.value = ""
                } else {
                    phoneNumWarn.value = Utils.getString(R.string.signup_warn_duplicate_phone_number)
                }
            }, {

            })
    }

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
                nicknameWarn.value = ""
            }, {
                nicknameWarn.value = when (it) {
                    is HttpException -> Utils.getString(R.string.signup_warn_duplicate_nickname)
                    else ->

                        ""

                }

            }).addTo(compositeDisposable)

    }

    private fun checkAll(email: String, pw: String, pwCheck: String, phoneNumber: String, nickname: String): Observable<Boolean> {
        val validEmail = isValidEmail(email)
        val validPw = isValidPw(pw)
        val validPwCheck = isValidPwCheck(pw, pwCheck)
        val validPhoneNumber = isValidPhoneNumber(phoneNumber)
        val validNickname = isValidNickname(nickname)

        return Observable.just(
            validEmail &&
                    validPw &&
                    validPwCheck &&
                    validPhoneNumber &&
                    validNickname
        )
    }

    fun signUp(email: String, pw: String, pwCheck: String, phoneNumber: String, nickname: String) {

        checkAll(email, pw, pwCheck, phoneNumber, nickname)
            .filter { it }
            .flatMap {
                Observable.zip(lensApiClient.checkSameId(email).map { it.body() }, lensApiClient.checkSameNickname(nickname).map { it.body() },
                    lensApiClient.checkSamePhoneNumber(phoneNumber).map { it.body() })
                { emailAvailable: CheckDuplicateResponse, nicknameAvailable: CheckDuplicateResponse, phoneNumberAvailable: CheckDuplicateResponse ->
                    if (!emailAvailable.available) {
                        emailWarn.value = Utils.getString(R.string.signup_warn_duplicate_email)
                    }
                    if (!nicknameAvailable.available) {
                        nicknameWarn.value = Utils.getString(R.string.signup_warn_duplicate_nickname)
                    }
                    if (!phoneNumberAvailable.available) {
                        phoneNumWarn.value = Utils.getString(R.string.signup_warn_duplicate_phone_number)
                    }
                    emailAvailable.available && nicknameAvailable.available && phoneNumberAvailable.available
                }
            }
            .doOnError { errMessage.value = Utils.getString(R.string.signup_fail_for_server) }
            .onErrorReturn { false }
            .subscribe { canSignUp ->
                if (canSignUp) {
                    callSignUp(email, pw, phoneNumber, nickname)
                }
            }
            .addTo(compositeDisposable)
    }

    private fun callSignUp(email: String, pw: String, phoneNumber: String, nickname: String) {
        lensApiClient.signUp(email, pw, phoneNumber, nickname)
            .subscribe({
                signUpResult.value = Utils.getString(R.string.signup_success)
            }, {

                errMessage.value = when (it) {
                    is SocketTimeoutException -> Utils.getString(R.string.login_fail_for_server)
                    is HttpException -> Utils.getString(R.string.signup_fail_wrong_input)
                    else -> "" //TODO 서버로 로그전송
                }

            }).addTo(compositeDisposable)
    }
}