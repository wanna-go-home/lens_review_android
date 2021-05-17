package com.wannagohome.viewty.ui.bulletin.review.write

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.wannagohome.viewty.databinding.ActivityWriteReviewBinding
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class WriteReviewActivity : AppCompatActivity() {

    private val writeReviewViewModel by viewModels<WriteReviewViewModel>()

    private lateinit var binding: ActivityWriteReviewBinding

    private val writeReviewPagerAdapter = WriteReviewPagerAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWriteReviewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViewPager()

        observeEvents()

        writeReviewViewModel.resetStage()

    }

    private fun observeEvents() {
        writeReviewViewModel.curStageLiveData.observe(this, {
            if (it == WriteReviewViewModel.WriteReviewStage.OFF) {
                finish()
                return@observe
            }
            Timber.d("kgp value " + it.ordinal)
            binding.writeReviewViewPager.currentItem = it.ordinal
        })
    }

    private fun initViewPager() {
        binding.writeReviewViewPager.adapter = writeReviewPagerAdapter
        binding.writeReviewViewPager.isUserInputEnabled = false

    }

    override fun onBackPressed() {
        writeReviewViewModel.back()
    }
}