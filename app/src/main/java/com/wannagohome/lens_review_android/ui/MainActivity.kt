package com.wannagohome.lens_review_android.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayout
import com.wannagohome.lens_review_android.R
import com.wannagohome.lens_review_android.ui.board.TabBoard
import com.wannagohome.lens_review_android.ui.search_lens.TabSearch
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.core.KoinComponent

class MainActivity : AppCompatActivity(), KoinComponent {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViewPager()

    }
    private fun initViewPager(){
        mainViewPager.adapter = MainViewPagerAdapter(supportFragmentManager)
        mainTabLayout.setupWithViewPager(mainViewPager)
    }
}