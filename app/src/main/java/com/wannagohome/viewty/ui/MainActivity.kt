package com.wannagohome.viewty.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

import com.wannagohome.viewty.databinding.ActivityMainBinding
import org.koin.core.KoinComponent

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