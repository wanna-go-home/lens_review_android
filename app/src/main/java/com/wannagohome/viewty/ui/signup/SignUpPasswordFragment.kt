package com.wannagohome.viewty.ui.signup

import android.graphics.Color
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.TextUtils
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import com.jakewharton.rxbinding4.view.clicks
import com.wannagohome.viewty.AppComponents
import com.wannagohome.viewty.R
import com.wannagohome.viewty.databinding.FragmentSignUpPasswordBinding
import com.wannagohome.viewty.extension.addTo
import com.wannagohome.viewty.support.baseclass.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpPasswordFragment : BaseFragment() {

    private val signUpViewModel by activityViewModels<SignUpViewModel>()

    private var _binding: FragmentSignUpPasswordBinding? = null
    val binding: FragmentSignUpPasswordBinding
        get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentSignUpPasswordBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initListener()

        initTermsText()

        observeEvents()
    }

    private fun observeEvents() {
        signUpViewModel.pwError.observe(viewLifecycleOwner) {

            binding.passwordEdit1Layout.error = if (it.isNotEmpty()) it else null
        }

        signUpViewModel.pwCheckError.observe(viewLifecycleOwner) {

            binding.passwordEdit1Layout.error = if (it.isNotEmpty()) it else null
        }
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

    private fun initTermsText() {
        val termsLinkText = SpannableString("이용약관, 개인정보수집이용").apply {

            val clickableSpan = object : ClickableSpan() {
                override fun onClick(widget: View) {
                    startActivity(requireActivity(), TermsActivity::class.java)
                }
            }
            setSpan(clickableSpan, 0, length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

            val blueColor = ContextCompat.getColor(AppComponents.applicationContext, R.color.link_blue)
            setSpan(ForegroundColorSpan(blueColor), 0, length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        }

        val normalText = SpannableString("에 동의하시면 시작하기를 눌러주세요")

        val result = TextUtils.concat(termsLinkText, normalText)

        binding.termsText.text = result
        binding.termsText.movementMethod = LinkMovementMethod.getInstance()
        binding.termsText.highlightColor = Color.TRANSPARENT
    }

    companion object {
        @JvmStatic
        fun newInstance() = SignUpPasswordFragment()

    }
}