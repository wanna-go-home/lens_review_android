package com.wannagohome.lens_review_android.ui.article.article.write

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.wannagohome.lens_review_android.databinding.ActivityWriteArticleBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class WriteArticleActivity : AppCompatActivity() {

    private val writeArticleViewModel: WriteArticleViewModel by viewModel()

    private lateinit var binding: ActivityWriteArticleBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWriteArticleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //TODO 중복클릭 처리
        binding.writeBtn.setOnClickListener {
            val title = binding.title.text.toString()
            val content = binding.content.text.toString()

            writeArticleViewModel.writeArticle(title, content)

        }

        writeArticleViewModel.writeSuccess.observe(this, {
            if (it) {
                finish()
            }
        })
    }
}