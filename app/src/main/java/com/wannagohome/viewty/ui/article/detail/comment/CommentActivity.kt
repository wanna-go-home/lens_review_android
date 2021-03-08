package com.wannagohome.viewty.ui.article.detail.comment

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.jakewharton.rxbinding4.view.clicks
import com.wannagohome.viewty.R
import com.wannagohome.viewty.databinding.ActivityCommentBinding
import com.wannagohome.viewty.extension.hideKeyboard
import com.wannagohome.viewty.support.Utils
import com.wannagohome.viewty.support.baseclass.BaseAppCompatActivity
import com.wannagohome.viewty.ui.BottomSheetFragment
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import java.util.concurrent.TimeUnit

class CommentActivity : BaseAppCompatActivity() {

    companion object {
        const val ARTICLE_ID = "articleId"
        const val COMMENT_ID = "commentId"
        const val IS_ARTICLE = false
    }
    private var articleId = -1
    private var commentId = -1

    private val articleCommentViewModel: ArticleCommentViewModel by viewModel {parametersOf(articleId, commentId)}
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
            finishActivityToRight()
        }

        commentAdapter = CommentMultiViewAdapter(supportFragmentManager, articleCommentViewModel, IS_ARTICLE)

        initCommentRecyclerView()

        addBackListener()

        addCommentPostListener()

        addOnRefreshListener()

        observeEvent()
    }

    override fun onStart() {
        super.onStart()
        articleCommentViewModel.getCommentsByCommentId()
    }

    private fun initCommentRecyclerView() {
        binding.commentRecyclerView.run {
            commentAdapter.onLikeClick = { pos ->
                val targetComment = commentAdapter.commentList[pos]
                if (targetComment.isLiked){
                    articleCommentViewModel.unlike(targetComment.commentId)
                }
                else{
                    articleCommentViewModel.like(targetComment.commentId)
                }
            }
            commentAdapter.onOptionClick = { pos ->
                val targetComment = commentAdapter.commentList[pos]
                BottomSheetFragment.newInstance(targetComment.commentId, targetComment.isAuthor).run {
                    setOnClickListener(commentAdapter)
                    show(supportFragmentManager, null)
                }
            }

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
                articleCommentViewModel.postComment(content)
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
            articleCommentViewModel.refreshComment()
        }
    }

    private fun observeEvent() {
        articleCommentViewModel.comments.observe(this, {
            commentAdapter.commentList = ArrayList(it)
        })
        articleCommentViewModel.refreshSuccess.observe(this, {
            binding.swiperefresh.isRefreshing = !it
        })

        articleCommentViewModel.postCommentSuccess.observe(this, {
            if (it) {
                articleCommentViewModel.refreshComment()
                binding.commentInput.text.clear()
                binding.scrollView.fullScroll(View.FOCUS_DOWN)
                hideKeyboard()
            }
        })

        articleCommentViewModel.deleteCommentSuccess.observe(this, {
            if (it) {
                articleCommentViewModel.refreshComment()
                Utils.showToast(getString(R.string.delete_success))
            }
        })
        articleCommentViewModel.reportCommentSuccess.observe(this, {
            if (it) {
                Utils.showToast(getString(R.string.report_success))
            }
        })
        articleCommentViewModel.finishActivity.observe(this, {
            if (it) {
                finishActivityToRight()
            }
        })
        articleCommentViewModel.modifyCommentSuccess.observe(this, {
            if (it) {
                articleCommentViewModel.refreshComment()
                Utils.showToast(getString(R.string.modify_success))
                hideKeyboard()
            }
        })
    }

    override fun onBackPressed() {
        super.onBackPressed()

        finishActivityToRight()

    }
}