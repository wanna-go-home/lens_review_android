package com.wannagohome.lens_review_android.ui.article

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.wannagohome.lens_review_android.databinding.ActivityArticleBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class ArticleActivity : AppCompatActivity() {

    companion object {
        const val ARTICLE_ID = "articleId"
    }

    private val articleViewModel: ArticleViewModel by viewModel()

    private val commentAdapter = CommentMultiViewAdapter()

    private lateinit var binding: ActivityArticleBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityArticleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val articleId = intent.getIntExtra(ARTICLE_ID, -1)

        if (articleId == -1) {
            Timber.d("article Id $articleId")
            //TODO error handling with UI

        }
        initCommentRecyclerView()
        observeEvent()
        articleViewModel.getArticle(articleId)
        articleViewModel.getComments(articleId)
    }

    private fun initCommentRecyclerView() {
        binding.commentRecyclerView.run {
            addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
            layoutManager = LinearLayoutManager(context)
            adapter = commentAdapter
        }
    }

    private fun observeEvent() {
        articleViewModel.article.observe(this, {

            binding.articleTitle.text = it.title
            binding.content.text = it.content
            binding.author.text = it.author
            binding.likes.text = it.likes.toString()
            binding.createdAt.text = it.createdAt
        })
        articleViewModel.comments.observe(this, {
            commentAdapter.commentList = ArrayList(it)
        })
    }
}