package com.wannagohome.viewty.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.wannagohome.viewty.AppComponents
import com.wannagohome.viewty.R
import com.wannagohome.viewty.ui.article.list.TabArticle
import com.wannagohome.viewty.ui.mypage.TabMypage
import com.wannagohome.viewty.ui.review.review_list.TabReview
import com.wannagohome.viewty.ui.search_lens.TabSearch


//class MainViewPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
//    private val fragmentTitleList = AppComponents.applicationContext.resources.getStringArray(R.array.main_tab_items)
//
//    override fun getItem(position: Int): Fragment {
//        return when (position) {
//            1-> TabReview.newInstance()
//            2 -> TabArticle.newInstance()
//            3 -> TabMypage.newInstance()
//            else -> TabSearch.newInstance()
//        }
//    }
//
//    override fun getPageTitle(position: Int): CharSequence? {
//        return fragmentTitleList[position]
//    }
//
//    override fun getCount(): Int {
//        return fragmentTitleList.size
//    }
//}


class MainViewPagerAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {

    override fun getItemCount(): Int {
        return TabIconEnum.values().size
    }

    override fun createFragment(position: Int): Fragment {

        return when (position) {
            1-> TabReview.newInstance()
            2 -> TabArticle.newInstance()
            3 -> TabMypage.newInstance()
            else -> TabSearch.newInstance()
        }
    }

}