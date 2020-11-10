package com.wannagohome.lens_review_android.ui.review.review_detail

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.wannagohome.lens_review_android.R

class ReviewDetailActivity : AppCompatActivity() {

    companion object{
        const val REVIEW_ID = "reviewId"

        fun startReviewDetailActivity(context: Context, reviewId : Int){
            val intent = Intent(context, ReviewDetailActivity::class.java)
            intent.putExtra(REVIEW_ID, reviewId)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_review_detail)

        val reviewId = intent.getIntExtra(REVIEW_ID, -1)

        if(reviewId == -1){
            finish()
        }



    }
}