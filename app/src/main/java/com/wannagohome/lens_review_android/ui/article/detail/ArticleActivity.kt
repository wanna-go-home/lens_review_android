package com.wannagohome.lens_review_android.ui.article.detail

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
import com.wannagohome.lens_review_android.ui.BottomSheetFragment
import com.wannagohome.lens_review_android.ui.article.detail.comment.ArticleCommentViewModel
import com.wannagohome.lens_review_android.ui.article.detail.comment.CommentActivity
import com.wannagohome.lens_review_android.ui.article.detail.comment.CommentMultiViewAdapter
import com.wannagohome.lens_review_android.ui.article.write.WriteArticleActivity
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import timber.log.Timber
import java.util.concurrent.TimeUnit

class ArticleActivity : BaseAppCompatActivity(), BottomSheetFragment.OnClickListener {

    companion object {
        const val ARTICLE_ID = "articleId"
        const val COMMENT_ID = "commentId"
        const val IS_ARTICLE = true
    }

    private var articleId = -1
    private val commentId = null
    private val articleViewModel: ArticleViewModel by viewModel { parametersOf(articleId) }
    private val articleCommentViewModel: ArticleCommentViewModel by viewModel { parametersOf(articleId, commentId) }
    private lateinit var commentAdapter: CommentMultiViewAdapter
    private lateinit var binding: ActivityArticleBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityArticleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        articleId = intent.getIntExtra(ARTICLE_ID, -1)
        if (articleId == -1) {
            Timber.d("article Id $articleId")
            //TODO error handling with UI
        }

        initCommentRecyclerView()

        addBackListener()

        addCommentPostListener()

        addOnRefreshListener()

        addGoToBottomListener()

        addLikeListener()

        observeEvent()
    }

    override fun onStart() {
        super.onStart()
        articleViewModel.getArticle()
        articleCommentViewModel.getCommentsByArticleId()
    }


    private fun addDialogListener(articleId: Int, isAuthor: Boolean) {
        binding.optionBtn.setOnClickListener {
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

    private fun addLikeListener() {
        binding.likesIcon.clicks()
            .observeOn(AndroidSchedulers.mainThread())
            .throttleFirst(300, TimeUnit.MILLISECONDS)
            .subscribe {
                if (binding.likesIcon.isChecked){
                    articleViewModel.unlike()
                }
                else{
                    articleViewModel.like()
                }
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
                    articleCommentViewModel.postComment(content)
                }
            }
    }

    private fun addOnRefreshListener() {
        binding.swiperefresh.setOnRefreshListener {
            refreshArticle()
        }
    }

    private fun initCommentRecyclerView() {
        binding.commentRecyclerView.run {
            addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
            layoutManager = LinearLayoutManager(context)
            commentAdapter = CommentMultiViewAdapter(supportFragmentManager, articleCommentViewModel, IS_ARTICLE)

            commentAdapter.onLikeClick = { pos ->
                val targetComment = commentAdapter.commentList[pos]
                if (targetComment.isLiked){
                    articleCommentViewModel.unlike(targetComment.commentId)
                }
                else{
                    articleCommentViewModel.like(targetComment.commentId)
                }
            }

            commentAdapter.onMoreCommentClick = { pos ->
                val targetComment = commentAdapter.commentList[pos]
                val intent = Intent(context, CommentActivity::class.java)
                intent.putExtra(ARTICLE_ID, targetComment.postId)
                intent.putExtra(COMMENT_ID, targetComment.commentId)
                startActivityFromRight(intent)
            }

            commentAdapter.onOptionClick = { pos ->
                val targetComment = commentAdapter.commentList[pos]
                BottomSheetFragment.newInstance(targetComment.commentId, targetComment.isAuthor).run {
                    setOnClickListener(commentAdapter)
                    show(supportFragmentManager, null)
                }
            }

            commentAdapter.onCommentsClick = { pos ->
                val targetComment = commentAdapter.commentList[pos]
                val intent = Intent(context, CommentActivity::class.java)
                intent.putExtra(ARTICLE_ID, targetComment.postId)
                intent.putExtra(COMMENT_ID, targetComment.commentId)
                startActivityFromRight(intent)
            }

            adapter = commentAdapter
        }
    }

    private fun observeEvent() {
        articleViewModel.article.observe(this, {
            binding.articleTitle.text = it.title
            binding.content.text = it.content
            binding.nickname.text = it.nickname
            binding.likesIcon.isChecked = it.isLiked
            binding.likes.text = it.likes.toString()
            binding.comments.text = it.comments.toString()
            binding.createdAt.text = dateHelper.calcCreatedBefore(it.createdAt)
            addDialogListener(articleId, it.isAuthor)
        })

        articleCommentViewModel.comments.observe(this, {
            commentAdapter.commentList = ArrayList(it)
            if (binding.swiperefresh.isRefreshing) {
                binding.swiperefresh.isRefreshing = false
            }
        })

        articleViewModel.deleteSuccess.observe(this, {
            if (it) {
                Utils.showToast(getString(R.string.delete_success))
                finishActivityToRight()
            }
        })
        articleViewModel.reportSuccess.observe(this, {
            if (it) {
                Utils.showToast(getString(R.string.report_success))
            }
        })
        articleCommentViewModel.postCommentSuccess.observe(this, {
            if (it) {
                refreshArticle()
                binding.commentInput.text.clear()
                binding.scrollView.fullScroll(View.FOCUS_DOWN)
                hideKeyboard()

            }
        })
        articleCommentViewModel.deleteCommentSuccess.observe(this, {
            if (it) {
                refreshArticle()
                Utils.showToast(getString(R.string.delete_success))
            }
        })
        articleCommentViewModel.modifyCommentSuccess.observe(this, {
            if (it) {
                refreshArticle()
                Utils.showToast(getString(R.string.modify_success))
                hideKeyboard()
            }
        })
        articleCommentViewModel.reportCommentSuccess.observe(this, {
            if (it) {
                Utils.showToast(getString(R.string.report_success))
            }
        })
    }
    private fun refreshArticle() {
        binding.swiperefresh.isRefreshing = true
        articleViewModel.getArticle()
        articleCommentViewModel.getCommentsByArticleId()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finishActivityToRight()
    }

    override fun onClickDeleteBtn(targetId: Int) {
        articleViewModel.deleteArticle()
    }

    override fun onClickModifyBtn(targetId: Int) {
        val intent = Intent(this@ArticleActivity, WriteArticleActivity::class.java)
        intent.putExtra(ARTICLE_ID, targetId)
        startActivity(intent)
    }

    override fun onClickReportBtn(targetId: Int) {
        //todo:  implement report
        articleViewModel.reportArticle()
    }
}