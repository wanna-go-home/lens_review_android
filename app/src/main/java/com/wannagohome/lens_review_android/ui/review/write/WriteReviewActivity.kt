package com.wannagohome.lens_review_android.ui.review.write

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.wannagohome.lens_review_android.databinding.ActivityWriteReviewBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class WriteReviewActivity : AppCompatActivity() {

    private val writeReviewViewModel: WriteReviewViewModel by viewModel()

    private lateinit var binding: ActivityWriteReviewBinding

    private val writeReviewPagerAdapter = WriteReviewPagerAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWriteReviewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViewPager()

        observeEvents()
    }
    private fun observeEvents(){
        writeReviewViewModel.curStageLiveData.observe(this, {
            if(it == WriteReviewViewModel.WriteReviewStage.OFF){
                finish()
                return@observe
            }
            binding.writeReviewViewPager.currentItem = it.ordinal
        })
    }

    private fun initViewPager(){
        binding.writeReviewViewPager.adapter = writeReviewPagerAdapter
        binding.writeReviewViewPager.isUserInputEnabled = false
    }
}