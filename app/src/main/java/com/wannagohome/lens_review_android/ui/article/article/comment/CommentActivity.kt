package com.wannagohome.lens_review_android.ui.article.article.comment

import android.os.Bundle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.jakewharton.rxbinding4.view.clicks
import com.wannagohome.lens_review_android.R
import com.wannagohome.lens_review_android.databinding.ActivityCommentBinding
import com.wannagohome.lens_review_android.support.Utils
import com.wannagohome.lens_review_android.support.baseclass.BaseAppCompatActivity
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.concurrent.TimeUnit

class CommentActivity : BaseAppCompatActivity() {

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

        if (articleId == -1 || commentId ==-1) {
            //TODO error handling with UI
        }
        initCommentRecyclerView()
        addBackListener()
        addCommentPostListener(articleId, commentId)
        addOnRefreshListener(articleId, commentId)
        observeEvent()
    }

    override fun onStart(){
        super.onStart()
        val commentId = intent.getIntExtra(COMMENT_ID, -1)
        val articleId = intent.getIntExtra(ARTICLE_ID, -1)
        if (articleId == -1 || commentId ==-1) {
            //TODO error handling with UI
        }
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
        binding.writeBtn.clicks()
            .observeOn(AndroidSchedulers.mainThread())
            .throttleFirst(300, TimeUnit.MILLISECONDS)
            .subscribe {
                val content = binding.commentInput.text.toString()
                if (content.isEmpty()) {
                    Utils.showToast(getString(R.string.write_need_content))
                    return@subscribe
                }
                binding.swiperefresh.isRefreshing = true
                commentViewModel.postComment(articleId, parentId, content)
            }
    }
    private fun addBackListener() {
        binding.backBtn.clicks()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                finishActivityToRight()
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
    override fun onBackPressed() {
        super.onBackPressed()

        finishActivityToRight()

    }
}