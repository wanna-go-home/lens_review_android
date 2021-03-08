package com.wannagohome.viewty.ui.mypage.myreview

import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.jakewharton.rxbinding4.view.clicks
import com.wannagohome.viewty.databinding.ActivityMyReviewBinding
import com.wannagohome.viewty.support.Utils
import com.wannagohome.viewty.support.baseclass.BaseAppCompatActivity
import com.wannagohome.viewty.ui.review.review_detail.ReviewDetailActivity
import com.wannagohome.viewty.ui.review.review_list.ReviewListAdapter
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import org.koin.androidx.viewmodel.ext.android.viewModel

class MyReviewActivity : BaseAppCompatActivity() {

    private val myReviewViewModel: MyReviewViewModel by viewModel()

    lateinit var binding: ActivityMyReviewBinding

    private val reviewListAdapter = ReviewListAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMyReviewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        addListener()

        initRecyclerView()

        observeEvents()

        myReviewViewModel.fetchMyReviewPreview()
    }

    private fun initRecyclerView() {
        binding.myReviewListRecyclerView.run {

            reviewListAdapter.onItemClick = { pos ->
                val clickedReviewPreview = reviewListAdapter.getItem(pos)

                val intent = Intent(context, ReviewDetailActivity::class.java)
                intent.putExtra(ReviewDetailActivity.REVIEW_ID, clickedReviewPreview.id)

                startActivityFromRight(intent)
            }

            adapter = reviewListAdapter
            addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
            layoutManager = LinearLayoutManager(context)


        }
    }

    private fun addListener() {
        binding.titleBar.leftBtn.clicks()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                finishActivityToRight()
            }
    }

    private fun observeEvents() {
        myReviewViewModel.reviewPreviewList.observe(this, {
            reviewListAdapter.items = it
        })

        myReviewViewModel.errorMessage.observe(this, {
            if (it.isNotEmpty()) {
                Utils.showToast(it)
            }
        })
    }

    override fun onBackPressed() {
        super.onBackPressed()

        finishActivityToRight()
    }
}