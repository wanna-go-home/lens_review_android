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

    private val tabSearch = TabSearch.instance
    private val tabBoard = TabBoard.instance

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        callFragment(0)

        main_tab.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                callFragment(tab?.position)
            }
        })

    }

    fun callFragment(pos: Int?) {
        val transaction = supportFragmentManager.beginTransaction()
        when (pos) {
            0 -> transaction.replace(R.id.main_tab_container, tabSearch)
            1 -> transaction.replace(R.id.main_tab_container, tabBoard)
        }
        transaction.commitNow()
    }
}