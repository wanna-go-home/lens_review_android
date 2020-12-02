package com.wannagohome.lens_review_android.ui.review.review_detail

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.wannagohome.lens_review_android.R
import com.wannagohome.lens_review_android.databinding.ActivityReviewDetailBinding

class ReviewDetailActivity : AppCompatActivity() {

    companion object{
        const val REVIEW_ID = "reviewId"

        fun startReviewDetailActivity(context: Context, reviewId : Int){
            val intent = Intent(context, ReviewDetailActivity::class.java)
            intent.putExtra(REVIEW_ID, reviewId)
            context.startActivity(intent)
        }
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
}