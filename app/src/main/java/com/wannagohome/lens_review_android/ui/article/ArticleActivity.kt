package com.wannagohome.lens_review_android.ui.article

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.wannagohome.lens_review_android.R
import com.wannagohome.lens_review_android.network.model.Comment
import kotlinx.android.synthetic.main.activity_article.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class ArticleActivity : AppCompatActivity() {

    companion object {
        const val ARTICLE_ID = "articleId"
    }

    private val articleViewModel : ArticleViewModel by viewModel()

    private val commentAdapter = CommentMultiViewAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_article)

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
        commentRecyclerView.run {
            addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
            layoutManager = LinearLayoutManager(context)
            adapter = commentAdapter
        }
    }
    private fun observeEvent(){
        articleViewModel.article.observe(this, Observer {

            articleTitle.text = it.title
            content.text = it.content
            author.text = it.author
            likes.text = it.likes.toString()
            createdAt.text = it.createdAt
        })
        articleViewModel.comments.observe(this, Observer {
            commentAdapter.commentList = ArrayList(it)
        })
    }
}