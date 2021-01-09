package com.wannagohome.lens_review_android.ui.article.article.comment

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.jakewharton.rxbinding4.view.clicks
import com.wannagohome.lens_review_android.R
import com.wannagohome.lens_review_android.databinding.ActivityCommentBinding
import com.wannagohome.lens_review_android.extension.hideKeyboard
import com.wannagohome.lens_review_android.support.Utils
import com.wannagohome.lens_review_android.support.baseclass.BaseAppCompatActivity
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import java.util.concurrent.TimeUnit

class CommentActivity : BaseAppCompatActivity() {

    companion object {
        const val ARTICLE_ID = "articleId"
        const val COMMENT_ID = "commentId"
    }
    private var articleId = -1
    private var commentId = -1

    private val commentViewModel: CommentViewModel by viewModel {parametersOf(articleId, commentId)}
    private lateinit var commentAdapter: CommentMultiViewAdapter
    private lateinit var binding: ActivityCommentBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCommentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        commentId = intent.getIntExtra(COMMENT_ID, -1)
        articleId = intent.getIntExtra(ARTICLE_ID, -1)
        if (articleId == -1 || commentId == -1) {
            //TODO error handling with UI
        }

        commentAdapter = CommentMultiViewAdapter(supportFragmentManager, commentViewModel)

        initCommentRecyclerView()

        addBackListener()

        addCommentPostListener()

        addOnRefreshListener()

        observeEvent()
    }

    override fun onStart() {
        super.onStart()
        commentViewModel.getComments()
    }

    private fun initCommentRecyclerView() {
        binding.commentRecyclerView.run {
            addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
            layoutManager = LinearLayoutManager(context)
            adapter = commentAdapter
        }
    }

    private fun addCommentPostListener() {
        binding.writeBtn.clicks()
            .observeOn(AndroidSchedulers.mainThread())
            .throttleFirst(300, TimeUnit.MILLISECONDS)
            .subscribe {
                val content = binding.commentInput.text.toString()
                if (content.isEmpty()) {
                    Utils.showToast(getString(R.string.write_need_content))
                    return@subscribe
                }
                commentViewModel.postComment(content)
            }
    }

    private fun addBackListener() {
        binding.backBtn.clicks()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                finishActivityToRight()
            }
    }

    private fun addOnRefreshListener() {
        binding.swiperefresh.setOnRefreshListener {
            commentViewModel.refreshComment()
        }
    }

    private fun observeEvent() {
        commentViewModel.comments.observe(this, {
            commentAdapter.commentList = ArrayList(it)
        })

        commentViewModel.refreshSuccess.observe(this, {
            binding.swiperefresh.isRefreshing = !it
        })

        commentViewModel.postCommentSuccess.observe(this, {
            if (it) {
                binding.commentInput.text.clear()
                binding.scrollView.fullScroll(View.FOCUS_DOWN)
                hideKeyboard()
            }
        })
        commentViewModel.deleteCommentSuccess.observe(this, {
            if (it) {
                Utils.showToast(getString(R.string.delete_success))
            }
        })
        commentViewModel.modifyCommentSuccess.observe(this, {
            if (it) {
                Utils.showToast(getString(R.string.modify_success))
            }
        })
    }

    override fun onBackPressed() {
        super.onBackPressed()

        finishActivityToRight()

    }
}