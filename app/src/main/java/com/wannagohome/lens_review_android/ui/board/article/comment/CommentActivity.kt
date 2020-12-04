package com.wannagohome.lens_review_android.ui.board.article.comment

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.wannagohome.lens_review_android.network.model.Comment
import com.wannagohome.lens_review_android.databinding.ActivityCommentBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class CommentActivity : AppCompatActivity() {

    companion object {
        const val COMMENT_ID = "commentId"
        const val ARTICLE_ID = "commentId"
    }
    //todo : remove after server api enabled
    private val commentViewModel : CommentViewModel by viewModel()

    private val commentAdapter = CommentMultiViewAdapter()
    private lateinit var binding: ActivityCommentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCommentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val commentId = intent.getIntExtra(COMMENT_ID, -1)
        val articleId = intent.getIntExtra(ARTICLE_ID, -1)

        if (commentId == -1) {
            Timber.d("comment Id $commentId")
            //TODO error handling with UI
        }
        initCommentRecyclerView()
        observeEvent()
        commentViewModel.getComments(articleId, commentId)
    }
    private fun initCommentRecyclerView() {
        binding.commentRecyclerView.run {
            addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
            layoutManager = LinearLayoutManager(context)
            adapter = commentAdapter
        }
    }
    private fun observeEvent(){
        commentViewModel.comments.observe(this, Observer {
            commentAdapter.commentList = ArrayList(it)
        })
    }
}