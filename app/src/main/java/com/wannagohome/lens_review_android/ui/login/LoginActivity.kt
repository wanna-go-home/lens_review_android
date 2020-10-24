package com.wannagohome.lens_review_android.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import com.wannagohome.lens_review_android.R
import com.wannagohome.lens_review_android.ui.MainActivity
import kotlinx.android.synthetic.main.activity_login.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginActivity : AppCompatActivity() {

    private val loginViewModel: LoginViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        loginBtn.setOnClickListener {
            //TODO id pw 검증로직
            loginViewModel.login(userId.text.toString(), userPw.text.toString())
        }

        loginViewModel.loginSuccess.observe(this, Observer {
            if(it){
                val intent = Intent(this@LoginActivity, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        })
    }
}