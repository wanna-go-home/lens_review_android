package com.wannagohome.lens_review_android.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.wannagohome.lens_review_android.AppComponents
import com.wannagohome.lens_review_android.R
import com.wannagohome.lens_review_android.ui.article.list.TabArticle
import com.wannagohome.lens_review_android.ui.mypage.TabMypage
import com.wannagohome.lens_review_android.ui.review.review_list.TabReview
import com.wannagohome.lens_review_android.ui.search_lens.TabSearch


class MainViewPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    private val fragmentTitleList = AppComponents.applicationContext.resources.getStringArray(R.array.main_tab_items)

    override fun getItem(position: Int): Fragment {
        return when (position) {
            1-> TabReview.newInstance()
            2 -> TabArticle.newInstance()
            3 -> TabMypage.newInstance()
            else -> TabSearch.newInstance()
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return fragmentTitleList[position]
    }

    override fun getCount(): Int {
        return fragmentTitleList.size
    }
}