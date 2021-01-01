package com.wannagohome.lens_review_android.ui.login

import android.content.Intent
import android.os.Bundle
import com.jakewharton.rxbinding4.view.clicks
import com.jakewharton.rxbinding4.widget.textChanges
import com.wannagohome.lens_review_android.databinding.ActivityLoginBinding
import com.wannagohome.lens_review_android.support.Utils
import com.wannagohome.lens_review_android.support.baseclass.BaseAppCompatActivity
import com.wannagohome.lens_review_android.ui.MainActivity
import com.wannagohome.lens_review_android.ui.signup.SignUpActivity
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.concurrent.TimeUnit

class LoginActivity : BaseAppCompatActivity() {

    private val loginViewModel: LoginViewModel by viewModel()

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        addListener()

        observeEvents()
    }

    private fun addListener() {
        binding.loginBtn.clicks()
            .throttleFirst(300, TimeUnit.MILLISECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                loginViewModel.login(binding.userId.text.toString(), binding.userPw.text.toString())

            }
        binding.userId.textChanges()

            .debounce(300, TimeUnit.MILLISECONDS)
            .skip(1)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                loginViewModel.isValidEmail(it.toString())
            }

        binding.userPw.textChanges()

            .debounce(300, TimeUnit.MILLISECONDS)
            .skip(1)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                loginViewModel.isValidPassword(it.toString())
            }

        binding.signUp.setOnClickListener {
            startActivityFromRight(this@LoginActivity, SignUpActivity::class.java)
        }

    }

    private fun observeEvents() {
        loginViewModel.loginSuccess.observe(this) {
            if (it) {
                startActivityFromRight(this@LoginActivity, MainActivity::class.java)
                finish()
            }
        }

        loginViewModel.errMessage.observe(this) {
            if (it.isNotEmpty()) {
                Utils.showToast(it)
            }
        }

        loginViewModel.emailWarn.observe(this) {
            binding.loginEmailWarn.text = it
        }
        loginViewModel.passwordWarn.observe(this) {
            binding.loginPwWarn.text = it
        }
    }
}