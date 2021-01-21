package com.wannagohome.lens_review_android.ui.mypage.myarticlecomment

import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.jakewharton.rxbinding4.view.clicks
import com.wannagohome.lens_review_android.databinding.ActivityMyCommentBinding
import com.wannagohome.lens_review_android.network.model.Comment
import com.wannagohome.lens_review_android.network.model.CommentType
import com.wannagohome.lens_review_android.support.baseclass.BaseAppCompatActivity
import com.wannagohome.lens_review_android.ui.article.article.ArticleActivity
import com.wannagohome.lens_review_android.ui.review.review_detail.ReviewDetailActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class MyCommentActivity : BaseAppCompatActivity() {

    lateinit var binding: ActivityMyCommentBinding

    private val myCommentViewModel: MyCommentViewModel by viewModel()

    private val myCommentAdapter = MyCommentAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMyCommentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initRecyclerView()

        observeEvents()

        addListener()

        myCommentViewModel.fetchMyCommentList()

    }

    private fun initRecyclerView() {
        binding.myCommentRecyclerView.run {
            myCommentAdapter.onItemClick = { pos ->
                val clickedComment = myCommentAdapter.getItem(pos)

                if(clickedComment.type == CommentType.ARTICLE.typeName){
                    val intent = Intent(context, ArticleActivity::class.java)
                    intent.putExtra(ArticleActivity.ARTICLE_ID, clickedComment.postId)
                    startActivityFromRight(intent)
                }
                else if(clickedComment.type == CommentType.REVIEW.typeName){
                    val intent = Intent(context, ReviewDetailActivity::class.java)
                    intent.putExtra(ReviewDetailActivity.REVIEW_ID, clickedComment.postId)
                    startActivityFromRight(intent)
                }
            }

            adapter = myCommentAdapter
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
            myCommentAdapter.items = it
        })
    }

    override fun onBackPressed() {
        super.onBackPressed()

        finishActivityToRight()
    }
}