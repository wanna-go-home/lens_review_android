package com.wannagohome.lens_review_android.ui.review.review_detail

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.wannagohome.lens_review_android.R
import com.wannagohome.lens_review_android.databinding.ActivityReviewDetailBinding
import com.wannagohome.lens_review_android.support.baseclass.BaseAppCompatActivity

class ReviewDetailActivity : BaseAppCompatActivity() {

    companion object{
        const val REVIEW_ID = "reviewId"
    }

    private lateinit var binding : ActivityReviewDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReviewDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val reviewId = intent.getIntExtra(REVIEW_ID, -1)

        if(reviewId == -1){
            finish()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()

        finishActivityToRight()
    }
}