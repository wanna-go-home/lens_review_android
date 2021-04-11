package com.wannagohome.viewty.ui.signup

import android.os.Bundle
import com.wannagohome.viewty.databinding.ActivitySignUpBinding
import com.wannagohome.viewty.support.baseclass.BaseAppCompatActivity
import com.wannagohome.viewty.ui.MainActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class SignUpActivity : BaseAppCompatActivity() {

    private val signUpViewModel: SignUpViewModel by viewModel()

    private lateinit var binding: ActivitySignUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)


        initViewPager()

        observeEvents()

        signUpViewModel.checkAccessKey()

//        addEventListener()

    }

    private fun initViewPager() {
        binding.signUpViewPager.adapter = SignUpViewPagerAdapter(this)
        //TODO api 연결시 제거
        binding.signUpViewPager.isUserInputEnabled = false
    }

    private fun observeEvents() {
        signUpViewModel.autoLogin.observe(this, {
            if (it) {
                startActivity(this, MainActivity::class.java)
                finish()
            }
        })
        signUpViewModel.signUpCurrentStagePosition.observe(this, { position ->
            binding.signUpViewPager.currentItem = position
        })
        signUpViewModel.signUpDone.observe(this, {
            if(it){
                finish()
                startActivity(this, MainActivity::class.java)
            }
        })
    }
}