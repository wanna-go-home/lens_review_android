package com.wannagohome.lens_review_android.ui.signup

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.wannagohome.lens_review_android.R
import kotlinx.android.synthetic.main.activity_sign_up.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class SignUpActivity : AppCompatActivity() {

    val signUpViewModel : SignUpViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        //TODO 중복클릭 처리
        signUp.setOnClickListener {

            val email = emailEdit.text.toString()
            val pw = pwEdit.text.toString()
            val pwCheck = pwCheckEdit.text.toString()
            val phoneNumber = phoneNumberEdit.text.toString()
            val nickname = nicknameEdit.text.toString()

            Timber.d("click1!!!@!@!@")
            signUpViewModel.signUp(email,pw,pwCheck, phoneNumber, nickname)
        }

    }
}