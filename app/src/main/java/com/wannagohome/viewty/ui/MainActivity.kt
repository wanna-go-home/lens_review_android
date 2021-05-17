package com.wannagohome.viewty.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.core.graphics.BlendModeColorFilterCompat
import androidx.core.graphics.BlendModeCompat
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import com.google.android.material.tabs.TabLayout
import com.jakewharton.rxbinding4.view.clicks
import com.wannagohome.viewty.R
import com.wannagohome.viewty.databinding.ActivityMainBinding
import com.wannagohome.viewty.extension.addTo
import com.wannagohome.viewty.extension.gone
import com.wannagohome.viewty.extension.visible
import com.wannagohome.viewty.support.baseclass.BaseAppCompatActivity
import com.wannagohome.viewty.ui.bulletin.article.write.WriteArticleActivity
import com.wannagohome.viewty.ui.bulletin.review.write.WriteReviewActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseAppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val iconEnumArray = TabIconEnum.values()

    private val mainViewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViewPager()

        initMainTab()

        initWriteButton()

        observeEvents()

    }

    private fun observeEvents() {

        mainViewModel.tabPosition.observe(this, Observer {
            binding.mainViewPager.setCurrentItem(it, false)
        })

        mainViewModel.mainTitle.observe(this, Observer {
            binding.mainTitleText.text = it
        })
    }

    private fun initMainTab() {
        val unselectedFilterColor =
            ContextCompat.getColor(this@MainActivity, R.color.main_tab_unselected)
        val selectedFilterColor =
            ContextCompat.getColor(this@MainActivity, R.color.main_tab_selected)

        for (icon in iconEnumArray) {
            val v = binding.mainTabLayout.newTab()
            v.icon = ContextCompat.getDrawable(this, icon.icon)
            v.icon?.colorFilter = BlendModeColorFilterCompat.createBlendModeColorFilterCompat(
                unselectedFilterColor,
                BlendModeCompat.SRC_IN
            )

            binding.mainTabLayout.addTab(v)
        }

        binding.mainTabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {

            override fun onTabReselected(p0: TabLayout.Tab?) {

            }

            override fun onTabUnselected(p0: TabLayout.Tab) {
                p0.icon?.colorFilter = BlendModeColorFilterCompat.createBlendModeColorFilterCompat(
                    unselectedFilterColor,
                    BlendModeCompat.SRC_IN
                )
            }

            override fun onTabSelected(p0: TabLayout.Tab) {
                p0.icon?.colorFilter = BlendModeColorFilterCompat.createBlendModeColorFilterCompat(
                    selectedFilterColor,
                    BlendModeCompat.SRC_IN
                )

                mainViewModel.setTabPosition(p0.position)
            }
        })

//        val firstTab = binding.mainTabLayout.getTabAt(0)
//        binding.mainTabLayout.selectTab(firstTab)
//        firstTab?.icon?.colorFilter = BlendModeColorFilterCompat.createBlendModeColorFilterCompat(
//            selectedFilterColor,
//            BlendModeCompat.SRC_IN
//        )
        mainViewModel.setTabPosition(0)


    }

    private fun initWriteButton() {
        binding.writeBtn.post {
            var offset = resources.displayMetrics.widthPixels / 5f / 2f
            offset -= binding.writeBtn.width / 2f

            binding.writeBtn.translationX = -offset
        }

        binding.writeBtn.clicks()
            .subscribe {
                if (binding.writeMenuLayout.isVisible) {
                    binding.writeMenuLayout.gone()
                } else {
                    binding.writeMenuLayout.visible()
                }
            }.addTo(compositeDisposable)

        binding.writeMenuLayout.clicks()
            .subscribe {
                binding.writeMenuLayout.gone()
            }.addTo(compositeDisposable)

        binding.writeReviewBtn.clicks()
            .subscribe {
                startActivityFromRight(this@MainActivity, WriteReviewActivity::class.java)
                binding.writeMenuLayout.gone()

            }.addTo(compositeDisposable)

        binding.writeArticleBtn.clicks()
            .subscribe {
                startActivityFromRight(this@MainActivity, WriteArticleActivity::class.java)
                binding.writeMenuLayout.gone()

            }.addTo(compositeDisposable)

    }

    private fun initViewPager() {
        binding.mainViewPager.adapter = MainViewPagerAdapter(this)
        binding.mainViewPager.isUserInputEnabled = false
    }

    override fun onBackPressed() {

        if (binding.writeMenuLayout.isVisible) {
            binding.writeMenuLayout.gone()
            return
        }

        super.onBackPressed()
    }
}