package com.wannagohome.lens_review_android.ui.signup

import androidx.lifecycle.MutableLiveData
import com.wannagohome.lens_review_android.R
import com.wannagohome.lens_review_android.network.lensapi.LensApiClient

import com.wannagohome.lens_review_android.support.baseclass.BaseViewModel
import com.wannagohome.lens_review_android.extension.addTo
import com.wannagohome.lens_review_android.support.Utils
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.functions.Function5
import io.reactivex.rxjava3.subjects.PublishSubject
import org.koin.core.inject
import timber.log.Timber
import java.util.concurrent.TimeUnit

class SignUpViewModel : BaseViewModel() {
    private val lensApiClient: LensApiClient by inject()

    val emailWarn = MutableLiveData<String>()
    val pwWarn = MutableLiveData<String>()
    val pwCheckWarn = MutableLiveData<String>()
    val phoneNumWarn = MutableLiveData<String>()
    val nicknameWarn = MutableLiveData<String>()

    val signUpResult = MutableLiveData<String>()

    private val emailChecker = PublishSubject.create<Boolean>()
    private val pwChecker = PublishSubject.create<Boolean>()
    private val pwCheckChecker = PublishSubject.create<Boolean>()
    private val phoneNumChecker = PublishSubject.create<Boolean>()
    private val nicknameChecker = PublishSubject.create<Boolean>()

    init {
//        compositeDisposable.add(emailChecker)
//        compositeDisposable.add(pwChecker)
//        compositeDisposable.add(pwCheckChecker)
//        compositeDisposable.add(phoneNumChecker)
//        compositeDisposable.add(nicknameChecker)
    }

    fun checkEmail(email: String) {

        if (email.isEmpty() || email.isBlank()) {
            emailWarn.value = Utils.getString(R.string.signup_warn_empty_email)
            emailChecker.onNext(false)
            return
        }

        val reg = Regex("[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*\\.[a-zA-Z]{2,3}")
        if (!email.matches(reg)) {
            emailWarn.value = Utils.getString(R.string.signup_warn_not_email_form)
            emailChecker.onNext(false)
            return
        }

        checkSameEmail(email)
    }

    fun checkPw(pw: String) {
        if (pw.isEmpty() || pw.isBlank()) {
            pwWarn.value = Utils.getString(R.string.signup_warn_empty_pw)
            pwChecker.onNext(false)
            return
        }

        val reg = Regex("[0-9|a-zA-Z]{6,15}")
        if (pw.length < 6 || !pw.matches(reg)) {
            pwWarn.value = Utils.getString(R.string.signup_warn_not_acceptable_pw)
            pwChecker.onNext(false)
            return
        }
        pwChecker.onNext(true)
        pwWarn.value = ""
    }

    fun checkPwCheck(pw: String, pwCheck: String) {
        if (pw != pwCheck) {
            pwCheckWarn.value = Utils.getString(R.string.signup_warn_different_with_pw)
            pwCheckChecker.onNext(false)
            return
        }
        pwCheckChecker.onNext(true)
        pwCheckWarn.value = ""
    }

    fun checkPhoneNum(phoneNum: String) {
        if (phoneNum.length != 11) {
            phoneNumWarn.value = Utils.getString(R.string.signup_warn_wrong_phone_number)
            phoneNumChecker.onNext(false)
            return
        }
        phoneNumChecker.onNext(true)
        phoneNumWarn.value = ""
    }

    fun checkNickname(nickname: String) {
        if (nickname.isEmpty() || nickname.isBlank()) {
            nicknameWarn.value = Utils.getString(R.string.signup_warn_duplicate_nickname)
            nicknameChecker.onNext(false)
            return
        }

//        nicknameWarn.value = ""
        checkSameNickname(nickname)
    }

    private fun checkSameEmail(email: String) {
        lensApiClient.checkSameId(email)
            .subscribe({
                emailWarn.value = ""
                emailChecker.onNext(true)
            }, {
                emailWarn.value = Utils.getString(R.string.signup_warn_duplicate_email)
                emailChecker.onNext(false)
            })
    }

    private fun checkSameNickname(nickname: String) {
        lensApiClient.checkSameNickname(nickname)
            .subscribe({
                nicknameWarn.value = ""
                nicknameChecker.onNext(true)
            }, {
                nicknameWarn.value = Utils.getString(R.string.signup_warn_duplicate_nickname)
                nicknameChecker.onNext(false)
            }).addTo(compositeDisposable)
    }

    fun checkAll(email: String, pw: String, pwCheck: String, phoneNumber: String, nickname: String) {
        checkEmail(email)
        checkPw(pw)
        checkPwCheck(pw, pwCheck)
        checkPhoneNum(phoneNumber)
        checkNickname(nickname)
    }

    fun signUp(email: String, pw: String, pwCheck: String, phoneNumber: String, nickname: String) {
        val emailRes = emailChecker.first(false)
        val pwRes = pwChecker.first(false)
        val pwCheckRes = pwCheckChecker.first(false)
        val phoneNumRes = phoneNumChecker.first(false)
        val nicknameRes = nicknameChecker.first(false)

        Single.zip(emailRes, pwRes, pwCheckRes, phoneNumRes, nicknameRes,
            Function5 { emailForm: Boolean, pwForm: Boolean, pwCheckForm: Boolean, phoneForm: Boolean, nicknameForm: Boolean ->

                emailForm && pwForm && pwCheckForm && phoneForm && nicknameForm
            })
            .subscribe { canSignUp ->
                if (canSignUp) {
                    callSignUp(email, pw, phoneNumber, nickname)
                }
            }

        checkAll(email, pw, pwCheck, phoneNumber, nickname)
    }

    private fun callSignUp(email: String, pw: String, phoneNumber: String, nickname: String) {
        lensApiClient.signUp(email, pw, phoneNumber, nickname)
            .subscribe({
                signUpResult.value = Utils.getString(R.string.signup_success)
            }, {
                signUpResult.value = ""
            }).addTo(compositeDisposable)
    }
}