package com.wannagohome.lens_review_android.ui.login

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.wannagohome.lens_review_android.databinding.ActivityLoginBinding
import com.wannagohome.lens_review_android.ui.MainActivity
import com.wannagohome.lens_review_android.ui.signup.SignUpActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginActivity : AppCompatActivity() {

    private val loginViewModel: LoginViewModel by viewModel()

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.loginBtn.setOnClickListener {
            //TODO id pw 검증로직
            loginViewModel.login(binding.userId.text.toString(), binding.userPw.text.toString())
        }
        binding.signUp.setOnClickListener {
            val intent = Intent(this@LoginActivity, SignUpActivity::class.java)
            startActivity(intent)
        }

        loginViewModel.loginSuccess.observe(this, {
            if (it) {
                val intent = Intent(this@LoginActivity, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        })
    }
}