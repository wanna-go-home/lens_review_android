package com.wannagohome.lens_review_android.ui.review.review_detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.jakewharton.rxbinding4.view.clicks
import com.wannagohome.lens_review_android.R
import com.wannagohome.lens_review_android.databinding.ActivityReviewDetailBinding
import com.wannagohome.lens_review_android.extension.hideKeyboard
import com.wannagohome.lens_review_android.network.model.helper.dateHelper
import com.wannagohome.lens_review_android.support.Utils
import com.wannagohome.lens_review_android.support.baseclass.BaseAppCompatActivity
import com.wannagohome.lens_review_android.ui.BottomSheetFragment
import com.wannagohome.lens_review_android.ui.review.write.WriteReviewActivity
import com.wannagohome.lens_review_android.ui.review.review_detail.comment.CommentMultiViewAdapter
import com.wannagohome.lens_review_android.ui.review.review_detail.comment.ReviewCommentViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import java.util.concurrent.TimeUnit

class ReviewDetailActivity : BaseAppCompatActivity(), BottomSheetFragment.OnClickListener {

    companion object{
        const val REVIEW_ID = "reviewId"
        const val IS_REVIEW = true
        fun startReviewDetailActivity(context: Context, reviewId : Int){
            val intent = Intent(context, ReviewDetailActivity::class.java)
            intent.putExtra(REVIEW_ID, reviewId)
            context.startActivity(intent)
        }
    }

    private var reviewId = -1
    private val commentId = null
    private val reviewDetailViewModel: ReviewDetailViewModel by viewModel { parametersOf(reviewId) }
    private val reviewCommentViewModel: ReviewCommentViewModel by viewModel { parametersOf(reviewId, commentId) }
    private lateinit var commentAdapter: CommentMultiViewAdapter
    private lateinit var binding : ActivityReviewDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReviewDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        reviewId = intent.getIntExtra(REVIEW_ID, -1)

        if(reviewId == -1){
            finish()
        }
        initCommentRecyclerView()

        //@todo : implement isAuthor
        addDialogListener(reviewId, isAuthor = true)

        addBackListener()

        addCommentPostListener()

        addOnRefreshListener()

        addGoToBottomListener()

        observeEvent()
    }

    override fun onStart() {
        super.onStart()
        reviewDetailViewModel.getReview()
        reviewCommentViewModel.getCommentsByReviewId()
    }


    private fun addDialogListener(reviewId: Int, isAuthor: Boolean) {
        binding.moreImg.setOnClickListener {
            BottomSheetFragment.newInstance(reviewId, isAuthor).run{
                setOnClickListener(this@ReviewDetailActivity)
                show(supportFragmentManager, null)
            }
        }
    }

    private fun addGoToBottomListener() {
        binding.goToBottom.setOnClickListener {
            binding.scrollView.fullScroll(View.FOCUS_DOWN)
        }
    }

    private fun addBackListener() {
        binding.backBtn.clicks()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                finishActivityToRight()
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
                } else {
                    reviewCommentViewModel.postComment(content)
                }
            }
    }

    private fun addOnRefreshListener() {
        binding.swiperefresh.setOnRefreshListener {
            refreshreview()
        }
    }

    private fun initCommentRecyclerView() {
        binding.commentRecyclerView.run {
            addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
            layoutManager = LinearLayoutManager(context)
            commentAdapter = CommentMultiViewAdapter(supportFragmentManager, reviewCommentViewModel, IS_REVIEW)
            adapter = commentAdapter
        }
    }

    private fun observeEvent() {
        reviewDetailViewModel.review.observe(this, {
            binding.reviewTitle.text = it.title
            binding.reviewContents.text = it.content
            binding.reviewWriter.text = it.nickname
            binding.likeNum.text = it.likeCnt.toString()
            binding.commentNum.text = it.replyCnt.toString()
            binding.time.text = dateHelper.calcCreatedBefore(it.createdAt)
        })
        reviewCommentViewModel.comments.observe(this, {
            commentAdapter.commentList = ArrayList(it)
            if (binding.swiperefresh.isRefreshing){
                binding.swiperefresh.isRefreshing = false
            }
        })

        reviewDetailViewModel.deleteSuccess.observe(this, {
            if (it) {
                Utils.showToast(getString(R.string.delete_success))
                finishActivityToRight()
            }
        })
        reviewCommentViewModel.postCommentSuccess.observe(this, {
            if (it) {
                refreshreview()
                binding.commentInput.text.clear()
                binding.scrollView.fullScroll(View.FOCUS_DOWN)
                hideKeyboard()

            }
        })
        reviewCommentViewModel.deleteCommentSuccess.observe(this, {
            if (it) {
                refreshreview()
                Utils.showToast(getString(R.string.delete_success))
            }
        })
        reviewCommentViewModel.modifyCommentSuccess.observe(this, {
            if (it) {
                refreshreview()
                Utils.showToast(getString(R.string.modify_success))
                hideKeyboard()
            }
        })
    }
    private fun refreshreview() {
        binding.swiperefresh.isRefreshing = true
        reviewDetailViewModel.getReview()
        reviewCommentViewModel.getCommentsByReviewId()
    }

    override fun onClickDeleteBtn(targetId: Int) {
        reviewDetailViewModel.deleteReview()
    }

    override fun onClickModifyBtn(targetId: Int) {
        val intent = Intent(this@ReviewDetailActivity, WriteReviewActivity::class.java)
        intent.putExtra(REVIEW_ID, targetId)
        startActivity(intent)
    }

    override fun onClickReportBtn(targetId: Int) {
        TODO("Not yet implemented")
    }

    override fun onBackPressed() {
        super.onBackPressed()

        finishActivityToRight()
    }
}