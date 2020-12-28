package com.wannagohome.lens_review_android.ui.mypage.myarticle

import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.jakewharton.rxbinding4.view.clicks
import com.wannagohome.lens_review_android.databinding.ActivityMyArticleBinding
import com.wannagohome.lens_review_android.support.baseclass.BaseAppCompatActivity
import com.wannagohome.lens_review_android.ui.article.ArticleListAdapter
import com.wannagohome.lens_review_android.ui.article.article.ArticleActivity
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import org.koin.androidx.viewmodel.ext.android.viewModel

class MyArticleActivity : BaseAppCompatActivity() {

    private lateinit var binding: ActivityMyArticleBinding

    private val myArticleViewModel: MyArticleViewModel by viewModel()

    private val myArticleListAdapter = ArticleListAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMyArticleBinding.inflate(layoutInflater)

        setContentView(binding.root)

        observeEvents()

        initListener()

        initBoardListRecyclerView()

        myArticleViewModel.getMyArticle()
    }
    private fun initListener(){
        binding.titleBar.leftBtn.clicks()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                finishActivityToRight()
            }
    }
    private fun observeEvents(){
        myArticleViewModel.myArticleList.observe(this, {
            myArticleListAdapter.items = it
        })
    }

    private fun initBoardListRecyclerView() {
        binding.myArticleListRecyclerView.run {
            addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
            layoutManager = LinearLayoutManager(context)

            myArticleListAdapter.onItemClick = { pos ->
                val clickedArticle = myArticleListAdapter.getItem(pos)

                val intent = Intent(this@MyArticleActivity, ArticleActivity::class.java)
                intent.putExtra(ArticleActivity.ARTICLE_ID, clickedArticle.articleId)
                startActivity(intent)
            }

            adapter = myArticleListAdapter
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()

        finishActivityToRight()

    }
}