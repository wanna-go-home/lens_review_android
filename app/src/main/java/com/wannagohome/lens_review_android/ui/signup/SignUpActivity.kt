package com.wannagohome.lens_review_android.ui.signup

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import com.jakewharton.rxbinding4.view.clicks
import com.jakewharton.rxbinding4.widget.textChanges
import com.wannagohome.lens_review_android.R
import com.wannagohome.lens_review_android.support.Utils
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_sign_up.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber
import java.util.concurrent.TimeUnit

class SignUpActivity : AppCompatActivity() {

    private val signUpViewModel: SignUpViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        observeEvents()

        addEventListener()

    }

    private fun observeEvents() {
        signUpViewModel.emailWarn.observe(this, Observer {
            emailWarnText.text = it
        })
        signUpViewModel.pwWarn.observe(this, Observer {
            pwWarnText.text = it
        })
        signUpViewModel.pwCheckWarn.observe(this, Observer {
            pwCheckWarnText.text = it
        })
        signUpViewModel.phoneNumWarn.observe(this, Observer {
            phoneNumWarnText.text = it
        })
        signUpViewModel.nicknameWarn.observe(this, Observer {
            nicknameWarnText.text = it
        })

        signUpViewModel.signUpResult.observe(this, Observer {
            if(it.isNotBlank() && it.isNotEmpty()){
                Utils.showToast(it)
                finish()
            }
        })
    }

    private fun addEventListener() {
        emailEdit.textChanges()
            .debounce(500, TimeUnit.MILLISECONDS)
            .skip(1)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                signUpViewModel.checkEmail(it.toString())
            }

        pwEdit.textChanges()
            .debounce(400, TimeUnit.MILLISECONDS)
            .skip(1)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                signUpViewModel.checkPw(it.toString())
            }

        pwCheckEdit.textChanges()
            .debounce(400, TimeUnit.MILLISECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                signUpViewModel.checkPwCheck(pwEdit.text.toString(), it.toString())
            }

        phoneNumberEdit.textChanges()
            .debounce(400, TimeUnit.MILLISECONDS)
            .skip(1)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                signUpViewModel.checkPhoneNum(it.toString())
            }

        nicknameEdit.textChanges()
            .debounce(400, TimeUnit.MILLISECONDS)
            .skip(1)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                signUpViewModel.checkNickname(it.toString())
            }

        signUp.clicks()
            .throttleFirst(300, TimeUnit.MILLISECONDS)
            .subscribe {
                val email = emailEdit.text.toString()
                val pw = pwEdit.text.toString()
                val pwCheck = pwCheckEdit.text.toString()
                val phoneNumber = phoneNumberEdit.text.toString()
                val nickname = nicknameEdit.text.toString()

                signUpViewModel.signUp(email, pw, pwCheck, phoneNumber, nickname)
            }
    }
}