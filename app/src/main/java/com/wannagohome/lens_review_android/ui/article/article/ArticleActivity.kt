package com.wannagohome.lens_review_android.ui.article.article

import android.os.Bundle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.wannagohome.lens_review_android.network.model.helper.dateHelper
import com.wannagohome.lens_review_android.databinding.ActivityArticleBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.jakewharton.rxbinding4.view.clicks
import com.wannagohome.lens_review_android.R
import com.wannagohome.lens_review_android.support.Utils
import com.wannagohome.lens_review_android.support.baseclass.BaseAppCompatActivity
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import timber.log.Timber
import java.util.concurrent.TimeUnit

class ArticleActivity : BaseAppCompatActivity() {

    companion object {
        const val ARTICLE_ID = "articleId"
    }
    private val articleViewModel: ArticleViewModel by viewModel()
    private val fm = getSupportFragmentManager()
    private val commentAdapter = CommentMultiViewAdapter()
    private lateinit var binding: ActivityArticleBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityArticleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val articleId = intent.getIntExtra(ARTICLE_ID, -1)
        if (articleId == -1) {
            Timber.d("article Id $articleId")
            //TODO error handling with UI
        }
        initCommentRecyclerView()
        addDialogListener(articleId)
        addBackListener()
        addCommentPostListener(articleId)
        addOnRefreshListener(articleId)
        observeEvent()
    }

    override fun onStart(){
        super.onStart()
        val articleId = intent.getIntExtra(ARTICLE_ID, -1)
        if (articleId == -1) {
            //TODO error handling with UI
        }
        articleViewModel.getArticle(articleId)
        articleViewModel.getComments(articleId)
    }


    private fun addDialogListener(articleId: Int) {
        binding.moreImg.setOnClickListener{
            val moreDialogFragment = MoreDialog.newInstance(articleId)
            moreDialogFragment.show(fm, null)
        }
    }
    private fun addBackListener() {
        binding.backBtn.clicks()
            .observeOn(AndroidSchedulers.mainThread())
            .throttleFirst(300, TimeUnit.MILLISECONDS)
            .subscribe {
                finishActivityToRight()
            }
    }
    private fun addCommentPostListener(articleId: Int) {
        binding.writeBtn.setOnClickListener{
            val content = binding.commentInput.text.toString()
            if (content.isEmpty()) {
                Utils.showToast(getString(R.string.write_need_content))
            }
            else {
                binding.swiperefresh.isRefreshing = true
                articleViewModel.postComment(articleId, content)
            }
        }
    }

    private fun addOnRefreshListener(articleId: Int) {
        binding.swiperefresh.setOnRefreshListener{
            articleViewModel.refreshArticle(articleId)
        }
    }

    private fun initCommentRecyclerView() {
        binding.commentRecyclerView.run {
            addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
            layoutManager = LinearLayoutManager(context)
            adapter = commentAdapter
        }
    }

    private fun observeEvent() {
        articleViewModel.article.observe(this, {

            binding.articleTitle.text = it.title
            binding.content.text = it.content
            binding.author.text = it.author
            binding.likes.text = it.likes.toString()
            binding.createdAt.text = dateHelper.calcCreatedBefore(it.createdAt)
        })
        articleViewModel.comments.observe(this, {
            commentAdapter.commentList = ArrayList(it)
        })

        articleViewModel.refreshSuccess.observe(this, {
            if (it) {
                binding.swiperefresh.isRefreshing = false
            }
        })

        articleViewModel.deleteSuccess.observe(this, {
            if (it) {
                finishActivityToRight()
            }
        })
        articleViewModel.postCommentSuccess.observe(this, {
            if (it) {
                binding.commentInput.text.clear()
                articleViewModel.postCommentSuccess.value = false
            }
        })
    }
    override fun onBackPressed() {
        super.onBackPressed()

        finishActivityToRight()

    }
}