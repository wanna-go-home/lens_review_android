package com.wannagohome.lens_review_android.ui.review.write

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import com.wannagohome.lens_review_android.R
import kotlinx.android.synthetic.main.activity_write_review.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class WriteReviewActivity : AppCompatActivity() {

    private val writeReviewViewModel: WriteReviewViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_write_review)

        //TODO 중복클릭 처리
        writeReview.setOnClickListener {
            val title = titleEdit.text.toString()
            val content = contentsEdit.text.toString()
            val lensId = lensIdEdit.text.toString().toInt()

            writeReviewViewModel.writeReview(title, content, lensId)

        }

        writeReviewViewModel.writeSuccess.observe(this, Observer {
            if (it) {
                finish()
            }
        })
    }
}