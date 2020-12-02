package com.wannagohome.lens_review_android.ui.review.write

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.wannagohome.lens_review_android.databinding.ActivityWriteReviewBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class WriteReviewActivity : AppCompatActivity() {

    private val writeReviewViewModel: WriteReviewViewModel by viewModel()

    private lateinit var binding: ActivityWriteReviewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWriteReviewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //TODO 중복클릭 처리
        binding.writeReview.setOnClickListener {
            val title = binding.titleEdit.text.toString()
            val content = binding.contentsEdit.text.toString()
            val lensId = binding.lensIdEdit.text.toString().toInt()

            writeReviewViewModel.writeReview(title, content, lensId)

        }

        writeReviewViewModel.writeSuccess.observe(this, {
            if (it) {
                finish()
            }
        })
    }
}