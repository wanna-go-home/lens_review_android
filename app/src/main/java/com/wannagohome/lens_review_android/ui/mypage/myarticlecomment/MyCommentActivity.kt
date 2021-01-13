package com.wannagohome.lens_review_android.ui.mypage.myarticlecomment

import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.jakewharton.rxbinding4.view.clicks
import com.wannagohome.lens_review_android.R
import com.wannagohome.lens_review_android.databinding.ActivityMyCommentBinding
import com.wannagohome.lens_review_android.databinding.ActivityMyReviewBinding
import com.wannagohome.lens_review_android.support.baseclass.BaseAppCompatActivity
import com.wannagohome.lens_review_android.ui.review.review_detail.ReviewDetailActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class MyCommentActivity : BaseAppCompatActivity() {

    lateinit var binding: ActivityMyCommentBinding

    val myCommentViewModel: MyCommentViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMyCommentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initRecyclerView()

        observeEvents()

        addListener()


    }
    private fun initRecyclerView(){
        binding.myCommentRecyclerView.run{
//            reviewListAdapter.onItemClick = { pos ->
//                val clickedComment = reviewListAdapter.getItem(pos)
//
//                val intent = Intent(context, ReviewDetailActivity::class.java)
//                intent.putExtra(ReviewDetailActivity.REVIEW_ID, clickedReviewPreview.id)
//
//                startActivityFromRight(intent)
//            }

//            adapter = reviewListAdapter
            addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
            layoutManager = LinearLayoutManager(context)
        }
    }

    private fun addListener() {
        binding.titleBar.leftBtn.clicks()
            .subscribe {
                finishActivityToRight()
            }

    }

    private fun observeEvents() {
        myCommentViewModel.myCommentList.observe(this, {

        })
    }

    override fun onBackPressed() {
        super.onBackPressed()

        finishActivityToRight()
    }
}