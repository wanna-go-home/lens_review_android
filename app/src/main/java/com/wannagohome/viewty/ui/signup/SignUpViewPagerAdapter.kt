package com.wannagohome.viewty.ui.signup

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class SignUpViewPagerAdapter(fa : FragmentActivity) : FragmentStateAdapter(fa){
    private val SIGNUP_PAGE_SIZE = 3

    override fun getItemCount() = SIGNUP_PAGE_SIZE

    override fun createFragment(position: Int): Fragment {
        return when(position){
            1 -> SignUpSmsVerifyFragment.newInstance()
            2 -> SignUpPasswordFragment.newInstance()
            else -> SignUpPhoneNumberFragment.newInstance()
        }
    }
}