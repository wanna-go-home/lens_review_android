package com.wannagohome.lens_review_android.ui.signup

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.jakewharton.rxbinding4.view.clicks
import com.jakewharton.rxbinding4.widget.textChanges
import com.wannagohome.lens_review_android.databinding.ActivitySignUpBinding
import com.wannagohome.lens_review_android.support.Utils
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.concurrent.TimeUnit

class SignUpActivity : AppCompatActivity() {

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
                signUpViewModel.checkPw(it.toString())
            }

        binding.pwCheckEdit.textChanges()
            .debounce(400, TimeUnit.MILLISECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                signUpViewModel.checkPwCheck(binding.pwEdit.text.toString(), it.toString())
            }

        binding.phoneNumberEdit.textChanges()
            .debounce(400, TimeUnit.MILLISECONDS)
            .skip(1)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                signUpViewModel.checkPhoneNum(it.toString())
            }

        binding.nicknameEdit.textChanges()
            .debounce(400, TimeUnit.MILLISECONDS)
            .skip(1)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                signUpViewModel.checkNickname(it.toString())
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
    }
}