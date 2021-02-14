package com.wannagohome.lens_review_android.ui.review.review_detail.comment

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
import com.wannagohome.lens_review_android.ui.BottomSheetFragment
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import java.util.concurrent.TimeUnit

class CommentActivity : BaseAppCompatActivity() {

    companion object {
        const val REVIEW_ID = "reviewId"
        const val COMMENT_ID = "commentId"
        const val IS_ARTICLE = false
    }
    private var reviewId = -1
    private var commentId = -1

    private val reviewCommentViewModel: ReviewCommentViewModel by viewModel {parametersOf(reviewId, commentId)}
    private lateinit var commentAdapter: CommentMultiViewAdapter
    private lateinit var binding: ActivityCommentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCommentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        commentId = intent.getIntExtra(COMMENT_ID, -1)
        reviewId = intent.getIntExtra(REVIEW_ID, -1)
        if (reviewId == -1 || commentId == -1) {
            //TODO error handling with UI
            finishActivityToRight()
        }

        commentAdapter = CommentMultiViewAdapter(supportFragmentManager, reviewCommentViewModel, IS_ARTICLE)

        initCommentRecyclerView()

        addBackListener()

        addCommentPostListener()

        addOnRefreshListener()

        observeEvent()
    }

    override fun onStart() {
        super.onStart()
        reviewCommentViewModel.getCommentsByCommentId()
    }

    private fun initCommentRecyclerView() {
        binding.commentRecyclerView.run {
            commentAdapter.onLikeClick = { pos ->
                val targetComment = commentAdapter.commentList[pos]
                if (targetComment.isLiked){
                    reviewCommentViewModel.unlike(targetComment.commentId)
                }
                else{
                    reviewCommentViewModel.like(targetComment.commentId)
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
                reviewCommentViewModel.postComment(content)
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
            reviewCommentViewModel.refreshComment()
        }
    }

    private fun observeEvent() {
        reviewCommentViewModel.comments.observe(this, {
            commentAdapter.commentList = ArrayList(it)
        })

        reviewCommentViewModel.refreshSuccess.observe(this, {
            binding.swiperefresh.isRefreshing = !it
        })

        reviewCommentViewModel.postCommentSuccess.observe(this, {
            if (it) {
                reviewCommentViewModel.refreshComment()
                binding.commentInput.text.clear()
                binding.scrollView.fullScroll(View.FOCUS_DOWN)
                hideKeyboard()
            }
        })
        reviewCommentViewModel.deleteCommentSuccess.observe(this, {
            if (it) {
                reviewCommentViewModel.refreshComment()
                Utils.showToast(getString(R.string.delete_success))
            }
        })
        reviewCommentViewModel.finishActivity.observe(this, {
            if (it) {
                finishActivityToRight()
            }
        })
        reviewCommentViewModel.modifyCommentSuccess.observe(this, {
            if (it) {
                reviewCommentViewModel.refreshComment()
                Utils.showToast(getString(R.string.modify_success))
                hideKeyboard()
            }
        })
        reviewCommentViewModel.modifyCommentSuccess.observe(this, {
            if (it) {
                Utils.showToast(getString(R.string.report_success))
            }
        })
    }

    override fun onBackPressed() {
        super.onBackPressed()

        finishActivityToRight()

    }
}