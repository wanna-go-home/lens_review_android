package com.wannagohome.viewty.ui.bulletin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.fragment.app.activityViewModels
import com.google.android.material.chip.Chip
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.jakewharton.rxbinding4.view.clicks
import com.wannagohome.viewty.databinding.FragmentTabBulletinBinding
import com.wannagohome.viewty.databinding.ItemBulletinTabBinding
import com.wannagohome.viewty.support.baseclass.BaseFragment
import com.wannagohome.viewty.ui.MainTitleEnum
import com.wannagohome.viewty.ui.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers

@AndroidEntryPoint
class TabBulletins : BaseFragment() {

    private val mainViewModel by activityViewModels<MainViewModel>()

    private var _binding: FragmentTabBulletinBinding? = null
    private val binding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentTabBulletinBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val tabName = listOf("VIEWTY 리뷰", "VIEWTY 잡담")

        binding.bulletinViewPager.adapter = BulletinViewPagerAdapter(requireActivity())
        binding.bulletinViewPager.offscreenPageLimit = 2

        TabLayoutMediator(binding.bulletinTab, binding.bulletinViewPager) { tab, position ->
            val tabRoot =
                ItemBulletinTabBinding.inflate(layoutInflater, binding.bulletinTab, false).apply {
                    with(this.root) {
                        text = tabName[position]
                        id = ViewCompat.generateViewId()
                        isClickable = true
                        tag = position
                    }
                }

            tabRoot.root.clicks()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    val checked = tabRoot.root.isChecked
                    if (!checked) {
                        tabRoot.root.isChecked = true
                        return@subscribe
                    }

                    val tagPosition = tabRoot.root.tag as Int

                    binding.bulletinViewPager.currentItem = tagPosition
                }

            tab.customView = tabRoot.root

        }.attach()


        binding.bulletinTab.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {

            override fun onTabReselected(p0: TabLayout.Tab?) {
            }

            override fun onTabUnselected(p0: TabLayout.Tab) {

                val customView = p0.customView
                if (customView is Chip) {
                    customView.isChecked = false
                }
            }

            override fun onTabSelected(p0: TabLayout.Tab) {

                val customView = p0.customView
                if (customView is Chip) {
                    customView.isChecked = true
                }

                setCurrentPageTitle()
            }
        })

        val firstTab = binding.bulletinTab.getTabAt(0)
        val firstCustomView = firstTab!!.customView
        if (firstCustomView is Chip) {
            firstCustomView.isChecked = true
        }
        binding.bulletinTab.selectTab(firstTab)
        mainViewModel.setTitleInfo(MainTitleEnum.REVIEW)


    }

    override fun onResume() {
        super.onResume()

        setCurrentPageTitle()
    }

    private fun setCurrentPageTitle() {
        val selectedTabPosition = binding.bulletinTab.selectedTabPosition
        val titleInfo = MainTitleEnum.values().find { it.ordinal == selectedTabPosition }
        mainViewModel.setTitleInfo(titleInfo!!)
    }

    companion object {

        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() = TabBulletins()
    }
}