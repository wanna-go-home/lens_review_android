package com.wannagohome.viewty.ui.signup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jakewharton.rxbinding4.view.clicks
import com.wannagohome.viewty.databinding.FragmentSignUpPasswordBinding
import com.wannagohome.viewty.extension.addTo
import com.wannagohome.viewty.support.baseclass.BaseFragment
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


class SignUpPasswordFragment : BaseFragment() {

    val signUpViewModel: SignUpViewModel by sharedViewModel()

    var _binding: FragmentSignUpPasswordBinding? = null
    val binding: FragmentSignUpPasswordBinding
        get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentSignUpPasswordBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initListener()
    }

    private fun initListener() {
        binding.registerBtn.clicks()
            .subscribe {
                val pass1 = binding.passwordEdit1.text.toString()
                val pass2 = binding.passwordEdit2.text.toString()
                signUpViewModel.register(pass1, pass2)
            }.addTo(compositeDisposable)

        binding.backLayout.clicks()
            .subscribe {
                signUpViewModel.backStage()
            }.addTo(compositeDisposable)
    }

    companion object {
        @JvmStatic
        fun newInstance() = SignUpPasswordFragment()

    }
}