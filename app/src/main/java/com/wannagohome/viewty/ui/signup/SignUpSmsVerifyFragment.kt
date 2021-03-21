package com.wannagohome.viewty.ui.signup

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.wannagohome.viewty.R

class SignUpSmsVerifyFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_sign_up_sms_verify, container, false)
    }

    companion object {

        @JvmStatic
        fun newInstance() = SignUpSmsVerifyFragment()
    }
}