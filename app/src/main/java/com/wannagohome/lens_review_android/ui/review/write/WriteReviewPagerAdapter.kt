package com.wannagohome.lens_review_android.ui.review.write

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class WriteReviewPagerAdapter(fa : FragmentActivity) : FragmentStateAdapter(fa) {
    private val WRITE_REVIEW_PAGE_SIZE = 2

    override fun getItemCount(): Int {
        return WRITE_REVIEW_PAGE_SIZE
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            1 -> WriteReviewFragment.instance
            else -> SelectLensFragment.instance
        }
    }
}