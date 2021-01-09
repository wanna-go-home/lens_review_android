package com.wannagohome.lens_review_android.ui.article.article

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.jakewharton.rxbinding4.view.clicks
import com.wannagohome.lens_review_android.R
import com.wannagohome.lens_review_android.databinding.ActivityArticleBinding
import com.wannagohome.lens_review_android.extension.hideKeyboard
import com.wannagohome.lens_review_android.network.model.helper.dateHelper
import com.wannagohome.lens_review_android.support.Utils
import com.wannagohome.lens_review_android.support.baseclass.BaseAppCompatActivity
import com.wannagohome.lens_review_android.ui.article.article.modify.ModifyArticleActivity
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber
import java.util.concurrent.TimeUnit

class ArticleActivity : BaseAppCompatActivity(), BottomSheetFragment.OnClickListener {

    companion object {
        const val ARTICLE_ID = "articleId"
    }

    private val articleViewModel: ArticleViewModel by viewModel()
    private lateinit var commentAdapter: CommentMultiViewAdapter
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

        initCommentRecyclerView(articleId)

        //@todo : implement isAuthor
        addDialogListener(articleId, isAuthor = true)

        addBackListener()

        addCommentPostListener(articleId)

        addOnRefreshListener(articleId)

        addGoToBottomListener()

        observeEvent(articleId)
    }

    override fun onStart() {
        super.onStart()
        val articleId = intent.getIntExtra(ARTICLE_ID, -1)
        if (articleId == -1) {
            //TODO error handling with UI
        }
        articleViewModel.getArticle(articleId)
        articleViewModel.getComments(articleId)
    }


    private fun addDialogListener(articleId: Int, isAuthor: Boolean) {
        binding.moreImg.setOnClickListener {
            BottomSheetFragment.newInstance(articleId, isAuthor).run{
                setOnClickListener(this@ArticleActivity)
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

    private fun addCommentPostListener(articleId: Int) {
        binding.writeBtn.clicks()
            .observeOn(AndroidSchedulers.mainThread())
            .throttleFirst(300, TimeUnit.MILLISECONDS)
            .subscribe {
                val content = binding.commentInput.text.toString()
                if (content.isEmpty()) {
                    Utils.showToast(getString(R.string.write_need_content))
                } else {
                    articleViewModel.postComment(articleId, content)
                }
            }
    }

    private fun addOnRefreshListener(articleId: Int) {
        binding.swiperefresh.setOnRefreshListener {
            articleViewModel.refreshArticle(articleId)
        }
    }

    private fun initCommentRecyclerView(articleId: Int) {
        binding.commentRecyclerView.run {
            addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
            layoutManager = LinearLayoutManager(context)
            commentAdapter = CommentMultiViewAdapter(supportFragmentManager, articleViewModel, articleId)
            adapter = commentAdapter
        }
    }

    private fun observeEvent(articleId: Int) {
        articleViewModel.article.observe(this, {

            binding.articleTitle.text = it.title
            binding.content.text = it.content
            binding.author.text = it.author
            binding.likes.text = it.likes.toString()
            binding.comments.text = it.comments.toString()
            binding.createdAt.text = dateHelper.calcCreatedBefore(it.createdAt)
        })
        articleViewModel.comments.observe(this, {
            commentAdapter.commentList = ArrayList(it)
        })

        articleViewModel.refreshSuccess.observe(this, {
                binding.swiperefresh.isRefreshing = !it
        })

        articleViewModel.deleteSuccess.observe(this, {
            if (it) {
                Utils.showToast(getString(R.string.delete_success))
                finishActivityToRight()
            }
        })
        articleViewModel.postCommentSuccess.observe(this, {
            if (it) {
                binding.commentInput.text.clear()
                binding.scrollView.fullScroll(View.FOCUS_DOWN)
                hideKeyboard()

            }
        })
        articleViewModel.deleteCommentSuccess.observe(this, {
            if (it) {
                Utils.showToast(getString(R.string.delete_success))
            }
        })
        articleViewModel.modifyCommentSuccess.observe(this, {
            if (it) {
                Utils.showToast(getString(R.string.modify_success))
                hideKeyboard()
            }
        })
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finishActivityToRight()
    }

    override fun onClickDeleteBtn(targetId: Int) {
        articleViewModel.deleteArticle(targetId)
    }

    override fun onClickModifyBtn(targetId: Int) {
        val intent = Intent(this@ArticleActivity, ModifyArticleActivity::class.java)
        intent.putExtra(ARTICLE_ID, targetId)
        startActivity(intent)
    }

    override fun onClickReportBtn(targetId: Int) {
        TODO("Not yet implemented")
    }
}