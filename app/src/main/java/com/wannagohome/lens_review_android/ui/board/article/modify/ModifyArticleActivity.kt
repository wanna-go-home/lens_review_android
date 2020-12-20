package com.wannagohome.lens_review_android.ui.board.article.modify

import android.os.Bundle
import android.text.SpannableStringBuilder
import androidx.appcompat.app.AppCompatActivity
import com.wannagohome.lens_review_android.databinding.ActivityWriteArticleBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class ModifyArticleActivity : AppCompatActivity() {

    companion object {
        const val ARTICLE_ID = "articleId"
    }
    private val modifyArticleViewModel: ModifyArticleViewModel by viewModel()

    private lateinit var binding: ActivityWriteArticleBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWriteArticleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val articleId = intent.getIntExtra(ARTICLE_ID, -1)
        if (articleId == -1) {
            Timber.d("article Id $articleId")
            //TODO error handling with UI

        }
        //TODO 중복클릭 처리
        binding.writeBtn.setOnClickListener {
            val title = binding.title.text.toString()
            val content = binding.content.text.toString()

            modifyArticleViewModel.modifyArticle(articleId, title, content)

        }

        modifyArticleViewModel.writeSuccess.observe(this, {
            if (it) {
                finish()
            }
        })

        observeEvent()
        modifyArticleViewModel.getArticle(articleId)
    }
    private fun observeEvent() {
        modifyArticleViewModel.article.observe(this, {
            binding.title.text = SpannableStringBuilder(it.title)
            binding.content.text = SpannableStringBuilder(it.content)
        })
    }
}