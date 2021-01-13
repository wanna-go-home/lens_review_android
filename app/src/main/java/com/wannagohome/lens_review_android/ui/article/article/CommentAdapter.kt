package com.wannagohome.lens_review_android.ui.article.article

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.wannagohome.lens_review_android.R
import com.wannagohome.lens_review_android.databinding.ChildCommentListItemBinding
import com.wannagohome.lens_review_android.databinding.CommentListItemBinding
import com.wannagohome.lens_review_android.extension.invisible
import com.wannagohome.lens_review_android.extension.visible
import com.wannagohome.lens_review_android.network.model.Comment
import com.wannagohome.lens_review_android.network.model.helper.dateHelper
import com.wannagohome.lens_review_android.support.Utils.getString
import com.wannagohome.lens_review_android.ui.article.article.comment.CommentActivity


const val COMMENT = 0
const val INNER_COMMENT = 1

class CommentMultiViewAdapter(private val fm: FragmentManager, private val articleViewModel: ArticleViewModel, private val articleId: Int) : RecyclerView.Adapter<RecyclerView.ViewHolder>(),
    BottomSheetFragment.OnClickListener,
    CommentEditFragment.OnClickListener {

    companion object {
        const val MAX_CHILDREN_IN_ARTICLE = 3
        const val ARTICLE_ID = "articleId"
        const val COMMENT_ID = "commentId"
    }

    var commentList = ArrayList<Comment>()
        set(shops) {
            field = shops

            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            COMMENT -> {
                val binding = CommentListItemBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
                CommentViewHolder(parent, binding)
            }
            INNER_COMMENT -> {
                val binding = ChildCommentListItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                ChildCommentViewHolder(binding)
            }
            else -> throw RuntimeException("알 수 없는 뷰 타입 에러")
        }
    }

    override fun getItemCount() = commentList.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val obj = commentList[position]
        when (obj.depth) {
            COMMENT -> (holder as CommentViewHolder).bind(obj)
            INNER_COMMENT -> (holder as ChildCommentViewHolder).bind(obj)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return commentList[position].depth
    }

    inner class CommentViewHolder(private val parent: ViewGroup, private val itemBinding: CommentListItemBinding) : RecyclerView.ViewHolder(itemBinding.root) {
        lateinit var currentComment: Comment

        fun bind(comment: Comment) {
            currentComment = comment
            itemBinding.content.text = comment.content

            itemBinding.author.text = comment.author

            itemBinding.likes.text = comment.likes.toString()

            itemBinding.createdAt.text = dateHelper.calcCreatedBefore(comment.createdAt)

            itemBinding.comments.setOnClickListener {
                val intent = Intent(parent.context, CommentActivity::class.java)
                intent.putExtra(ARTICLE_ID, comment.articleId)
                intent.putExtra(COMMENT_ID, comment.commentId)
                parent.context.startActivity(intent)
            }

            itemBinding.moreImg.setOnClickListener {
                BottomSheetFragment.newInstance(comment.commentId, true).run {
                    setOnClickListener(this@CommentMultiViewAdapter)
                    show(fm, null)
                }
            }

            if (comment.bundleSize > MAX_CHILDREN_IN_ARTICLE) {
                itemBinding.moreComment.visible()
                val nOfComments = String.format(
                    getString(R.string.show_more_comments),
                    comment.bundleSize - MAX_CHILDREN_IN_ARTICLE
                )
                itemBinding.moreComment.text = nOfComments
                itemBinding.moreComment.setOnClickListener {
                    val intent = Intent(parent.context, CommentActivity::class.java)
                    intent.putExtra(ARTICLE_ID, comment.articleId)
                    intent.putExtra(COMMENT_ID, comment.commentId)
                    parent.context.startActivity(intent)
                }
            }

        }
    }

    inner class ChildCommentViewHolder(private val itemBinding: ChildCommentListItemBinding) : RecyclerView.ViewHolder(itemBinding.root) {
        lateinit var currentComment: Comment
        fun bind(comment: Comment) {
            currentComment = comment
            itemBinding.content.text = comment.content
            itemBinding.author.text = comment.author
            itemBinding.createdAt.text = dateHelper.calcCreatedBefore(comment.createdAt)
            itemBinding.likes.text = comment.likes.toString()
            itemBinding.moreImg.invisible()
        }
    }

    override fun onClickDeleteBtn(targetId: Int) {
        articleViewModel.deleteComment(articleId, targetId)
    }

    override fun onClickModifyBtn(targetId: Int) {
        val content = commentList.find { it.commentId == targetId }?.content
        val fragment = CommentEditFragment.newInstance(targetId, content)
        fragment.setOnClickListener(this)
        fragment.show(fm, "comment")
    }

    override fun onClickReportBtn(targetId: Int) {
        TODO("Not yet implemented")
    }

    override fun onClickModifyPostBtn(targetId: Int, content: String) {
        articleViewModel.modifyComment(articleId, targetId, content)
    }
}