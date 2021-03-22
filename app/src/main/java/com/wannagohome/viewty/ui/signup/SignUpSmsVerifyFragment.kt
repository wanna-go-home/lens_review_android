package com.wannagohome.viewty.ui.signup

import android.os.Bundle
import android.text.SpannableString
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.jakewharton.rxbinding4.view.clicks
import com.wannagohome.viewty.AppComponents
import com.wannagohome.viewty.R
import com.wannagohome.viewty.databinding.FragmentSignUpSmsVerifyBinding
import com.wannagohome.viewty.extension.addTo
import com.wannagohome.viewty.support.baseclass.BaseFragment
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class SignUpSmsVerifyFragment : BaseFragment() {

    val signUpViewModel : SignUpViewModel by sharedViewModel()

    var _binding: FragmentSignUpSmsVerifyBinding? = null

    val binding: FragmentSignUpSmsVerifyBinding
        get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentSignUpSmsVerifyBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initListener()

    }

    private fun initListener() {
        binding.verifySmsCodeBtn.clicks()
            .subscribe {
                signUpViewModel.verifySmsCode(binding.smsCodeEdit.text.toString())
            }.addTo(compositeDisposable)

        binding.backLayout.clicks()
            .subscribe {
                signUpViewModel.backStage()
            }.addTo(compositeDisposable)
    }


    companion object {

        @JvmStatic
        fun newInstance() = SignUpSmsVerifyFragment()
    }
}