package com.wannagohome.lens_review_android.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

import com.wannagohome.lens_review_android.databinding.ActivityMainBinding
import org.koin.core.KoinComponent
import timber.log.Timber

class MainActivity : AppCompatActivity(), KoinComponent {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViewPager()

    }

    private fun initViewPager() {
        binding.mainViewPager.adapter = MainViewPagerAdapter(supportFragmentManager)
        binding.mainTabLayout.setupWithViewPager(binding.mainViewPager)
    }
}