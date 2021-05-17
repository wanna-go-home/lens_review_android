package com.wannagohome.viewty.ui.login

import android.os.Bundle
import androidx.activity.viewModels
import com.jakewharton.rxbinding4.view.clicks
import com.jakewharton.rxbinding4.widget.textChanges
import com.wannagohome.viewty.databinding.ActivityLoginBinding
import com.wannagohome.viewty.support.Utils
import com.wannagohome.viewty.support.baseclass.BaseAppCompatActivity
import com.wannagohome.viewty.ui.MainActivity
import com.wannagohome.viewty.ui.signup.SignUpActivity
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class LoginActivity : BaseAppCompatActivity() {

    private val loginViewModel by viewModels<LoginViewModel>()

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        addListener()

        observeEvents()

        loginViewModel.checkAccessKey()

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