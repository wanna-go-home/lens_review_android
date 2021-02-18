package com.wannagohome.lens_review_android.ui.signup

import android.content.Intent
import android.os.Bundle
import com.jakewharton.rxbinding4.view.clicks
import com.jakewharton.rxbinding4.widget.textChanges
import com.wannagohome.lens_review_android.databinding.ActivitySignUpBinding
import com.wannagohome.lens_review_android.support.Utils
import com.wannagohome.lens_review_android.support.baseclass.BaseAppCompatActivity
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.bind
import java.util.concurrent.TimeUnit

class SignUpActivity : BaseAppCompatActivity() {

    private val signUpViewModel: SignUpViewModel by viewModel()

    private lateinit var binding: ActivitySignUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        observeEvents()

        addEventListener()

    }

    private fun observeEvents() {
        signUpViewModel.emailWarn.observe(this, {
            binding.emailWarnText.text = it
        })
        signUpViewModel.pwWarn.observe(this, {
            binding.pwWarnText.text = it
        })
        signUpViewModel.pwCheckWarn.observe(this, {
            binding.pwCheckWarnText.text = it
        })
        signUpViewModel.phoneNumWarn.observe(this, {
            binding.phoneNumWarnText.text = it
        })
        signUpViewModel.nicknameWarn.observe(this, {
            binding.nicknameWarnText.text = it
        })

        signUpViewModel.errMessage.observe(this, {
            if(it.isNotBlank()){
                Utils.showToast(it)
            }
        })

        signUpViewModel.signUpResult.observe(this, {
            if (it.isNotBlank() && it.isNotEmpty()) {
                Utils.showToast(it)
                finish()
            }
        })
    }

    private fun addEventListener() {
        binding.emailEdit.textChanges()
            .debounce(500, TimeUnit.MILLISECONDS)
            .skip(1)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                signUpViewModel.checkEmail(it.toString())
            }

        binding.pwEdit.textChanges()
            .debounce(400, TimeUnit.MILLISECONDS)
            .skip(1)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                signUpViewModel.isValidPw(it.toString())
            }

        binding.pwCheckEdit.textChanges()
            .debounce(400, TimeUnit.MILLISECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                signUpViewModel.isValidPwCheck(binding.pwEdit.text.toString(), it.toString())
            }

        binding.phoneNumberEdit.textChanges()
            .debounce(400, TimeUnit.MILLISECONDS)
            .skip(1)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                signUpViewModel.checkPhoneNumber(it.toString())
            }

        binding.nicknameEdit.textChanges()
            .debounce(400, TimeUnit.MILLISECONDS)
            .skip(1)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                signUpViewModel.nicknameCheck(it.toString())
            }

        binding.signUp.clicks()
            .throttleFirst(300, TimeUnit.MILLISECONDS)
            .subscribe {
                val email = binding.emailEdit.text.toString()
                val pw = binding.pwEdit.text.toString()
                val pwCheck = binding.pwCheckEdit.text.toString()
                val phoneNumber = binding.phoneNumberEdit.text.toString()
                val nickname = binding.nicknameEdit.text.toString()

                signUpViewModel.signUp(email, pw, pwCheck, phoneNumber, nickname)
            }
        binding.privacyTermsBtn.clicks()
            .subscribe {
                startActivityFromRight(this@SignUpActivity, TermsActivity::class.java)
            }

    }
    override fun onBackPressed() {
        super.onBackPressed()

        finishActivityToRight()

    }
}