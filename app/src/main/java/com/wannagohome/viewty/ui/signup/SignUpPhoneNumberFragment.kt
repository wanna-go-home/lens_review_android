package com.wannagohome.viewty.ui.signup

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.wannagohome.viewty.R


class SignUpPhoneNumberFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_sign_up_phone_number, container, false)
    }

    companion object {

        @JvmStatic
        fun newInstance() = SignUpPhoneNumberFragment()
    }
}