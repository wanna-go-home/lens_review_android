package com.wannagohome.lens_review_android.ui.article.article.comment

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.wannagohome.lens_review_android.R
import com.wannagohome.lens_review_android.databinding.ActivityCommentBinding
import com.wannagohome.lens_review_android.support.Utils
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class CommentActivity : AppCompatActivity() {

    companion object {
        const val COMMENT_ID = "commentId"
        const val ARTICLE_ID = "articleId"
    }
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
        addCommentPostListener(articleId, commentId)
        addOnRefreshListener(articleId, commentId)
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

    private fun addCommentPostListener(articleId: Int, parentId: Int) {
        binding.writeBtn.setOnClickListener{
            val content = binding.commentInput.text.toString()
            if (content.isEmpty()) {
                Utils.showToast(getString(R.string.write_need_content))
            }
            else {
                binding.swiperefresh.isRefreshing = true
                commentViewModel.postComment(articleId, parentId, content)
            }
        }
    }

    private fun addOnRefreshListener(articleId: Int, commentId: Int) {
        binding.swiperefresh.setOnRefreshListener{
            commentViewModel.refreshComment(articleId, commentId)
        }
    }
    private fun observeEvent(){
        commentViewModel.comments.observe(this, {
            commentAdapter.commentList = ArrayList(it)
        })

        commentViewModel.refreshSuccess.observe(this, {
            if (it) {
                binding.swiperefresh.isRefreshing = false
            }
        })

        commentViewModel.postCommentSuccess.observe(this, {
            if (it) {
                binding.commentInput.text.clear()
                commentViewModel.postCommentSuccess.value = false
            }
        })
    }
}