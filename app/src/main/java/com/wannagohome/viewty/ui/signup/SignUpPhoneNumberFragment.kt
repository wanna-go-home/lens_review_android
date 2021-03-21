package com.wannagohome.viewty.ui.signup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jakewharton.rxbinding4.view.clicks
import com.wannagohome.viewty.databinding.FragmentSignUpPhoneNumberBinding
import com.wannagohome.viewty.extension.addTo
import com.wannagohome.viewty.support.baseclass.BaseFragment
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


class SignUpPhoneNumberFragment : BaseFragment() {

    private var _binding: FragmentSignUpPhoneNumberBinding? = null

    private val binding: FragmentSignUpPhoneNumberBinding
        get() = _binding!!

    private val signUpViewModel: SignUpViewModel by sharedViewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentSignUpPhoneNumberBinding.inflate(layoutInflater, container, false)
        return _binding!!.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initListener()

    }

    private fun initListener() {
        binding.requestSmsCodeBtn.clicks()
            .subscribe {
                signUpViewModel.requestSmsCode(binding.phoneNumberEdit.text.toString())
            }.addTo(compositeDisposable)

        binding.backLayout.clicks()
            .subscribe {
                signUpViewModel.backStage()
            }.addTo(compositeDisposable)
    }

    companion object {

        @JvmStatic
        fun newInstance() = SignUpPhoneNumberFragment()
    }
}