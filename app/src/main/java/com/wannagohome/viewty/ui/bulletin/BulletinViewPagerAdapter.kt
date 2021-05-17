package com.wannagohome.viewty.ui.bulletin

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.wannagohome.viewty.ui.bulletin.TabBulletins
import com.wannagohome.viewty.ui.bulletin.article.list.TabArticle
import com.wannagohome.viewty.ui.bulletin.review.review_list.TabReview
import com.wannagohome.viewty.ui.lips.TabLips
import com.wannagohome.viewty.ui.mypage.TabMypage
import com.wannagohome.viewty.ui.search_lens.TabSearch
import com.wannagohome.viewty.ui.video.TabVideo


class BulletinViewPagerAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {

        return when (position) {
            1-> TabReview.newInstance()
            else -> TabArticle.newInstance()
        }
    }
}