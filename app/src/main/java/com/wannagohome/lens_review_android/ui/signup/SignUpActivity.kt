package com.wannagohome.lens_review_android.ui.signup

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.wannagohome.lens_review_android.R
import kotlinx.android.synthetic.main.activity_sign_up.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class SignUpActivity : AppCompatActivity() {

    val signUpViewModel : SignUpViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        //TODO 중복클릭 처리
        signUp.setOnClickListener {

        }

    }
}