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
//    Comment TEST
    private val comments = mutableListOf<Comment>().apply{
        add(Comment(1,"damon",1,"댓글이야",1,"232412424214",0,0))
        add(Comment(3,"afafa",1,"대댓글이야",1,"232412424214",1,1))
        add(Comment(2,"dawd",1,"댓글이야",1,"232412424214",0,0))
        add(Comment(4,"afw",1,"댓글이야",1,"232412424214",0,0))
        add(Comment(6,"adw",1,"대댓글이야",1,"232412424214",1,4))
        add(Comment(7,"grfg",1,"대댓글이야",1,"232412424214",1,4))
        add(Comment(5,"adwadw",1,"댓글이야",1,"232412424214",0,0))
    }

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
            commentAdapter.commentList = ArrayList(comments)
        })
    }
}