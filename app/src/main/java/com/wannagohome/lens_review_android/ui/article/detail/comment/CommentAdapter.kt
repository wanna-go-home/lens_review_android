package com.wannagohome.lens_review_android.ui.article.detail.comment

import android.content.Intent
import android.view.LayoutInflater
import com.wannagohome.lens_review_android.network.model.helper.dateHelper
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.wannagohome.lens_review_android.R
import com.wannagohome.lens_review_android.databinding.ChildCommentListItemBinding
import com.wannagohome.lens_review_android.databinding.CommentListItemBinding
import com.wannagohome.lens_review_android.extension.gone
import com.wannagohome.lens_review_android.extension.invisible
import com.wannagohome.lens_review_android.extension.visible
import com.wannagohome.lens_review_android.network.model.comment.Comment
import com.wannagohome.lens_review_android.support.Utils
import com.wannagohome.lens_review_android.ui.BottomSheetFragment

class CommentMultiViewAdapter(private val fm: FragmentManager, private val articleCommentViewModel: ArticleCommentViewModel, private val IS_ARTICLE: Boolean) : RecyclerView.Adapter<RecyclerView.ViewHolder>(),
    BottomSheetFragment.OnClickListener,
    com.wannagohome.lens_review_android.ui.review.review_detail.comment.CommentEditFragment.OnClickListener {

    companion object {
        const val MAX_CHILDREN_IN_ARTICLE = 3
        const val ARTICLE_ID = "articleId"
        const val COMMENT_ID = "commentId"
        const val COMMENT = 0
        const val INNER_COMMENT = 1
    }

    var commentList = ArrayList<Comment>()
        set(shops) {
            field = shops

            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            COMMENT -> {
                val binding = CommentListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                CommentViewHolder(parent, binding)
            }
            INNER_COMMENT -> {
                val binding = ChildCommentListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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
        private lateinit var currentComment: Comment

        fun bind(comment: Comment) {
            currentComment = comment
            itemBinding.content.text = comment.content
            itemBinding.author.text = comment.author
            itemBinding.likes.text = comment.likes.toString()
            itemBinding.createdAt.text = dateHelper.calcCreatedBefore(comment.createdAt)

            itemBinding.moreImg.setOnClickListener {
                BottomSheetFragment.newInstance(comment.commentId, true).run{
                    setOnClickListener(this@CommentMultiViewAdapter)
                    show(fm, null)
                }
            }
            if (IS_ARTICLE) {
                itemBinding.comments.setOnClickListener {
                    val intent = Intent(parent.context, CommentActivity::class.java)
                    intent.putExtra(ARTICLE_ID, comment.postId)
                    intent.putExtra(COMMENT_ID, comment.commentId)
                    parent.context.startActivity(intent)
                }
            }
            //@todo : let "더 보기" be recyclerview item
            if (IS_ARTICLE && comment.bundleSize > MAX_CHILDREN_IN_ARTICLE) {
                    val nOfComments = String.format(
                        Utils.getString(R.string.show_more_comments),
                        comment.bundleSize - MAX_CHILDREN_IN_ARTICLE
                    )
                    itemBinding.moreComment.text = nOfComments
                    itemBinding.moreComment.setOnClickListener {
                        val intent = Intent(parent.context, CommentActivity::class.java)
                        intent.putExtra(ARTICLE_ID, comment.postId)
                        intent.putExtra(COMMENT_ID, comment.commentId)
                        parent.context.startActivity(intent)
                    }
                    itemBinding.moreComment.visible()
            }
            else{
                itemBinding.moreComment.gone()
            }
        }
    }

    inner class ChildCommentViewHolder(private val itemBinding: ChildCommentListItemBinding) : RecyclerView.ViewHolder(itemBinding.root) {
        private lateinit var currentComment: Comment

        fun bind(comment: Comment) {
            currentComment = comment
            itemBinding.content.text = comment.content
            itemBinding.author.text = comment.author
            itemBinding.createdAt.text = dateHelper.calcCreatedBefore(comment.createdAt)
            itemBinding.likes.text = comment.likes.toString()
            if (IS_ARTICLE) {
                itemBinding.optionBtn.invisible()
            }
            else{
                itemBinding.optionBtn.setOnClickListener {
                    BottomSheetFragment.newInstance(comment.commentId, true).run{
                        setOnClickListener(this@CommentMultiViewAdapter)
                        show(fm, null)
                    }
                }
            }

        }
    }

    override fun onClickDeleteBtn(targetId: Int) {
        articleCommentViewModel.deleteComment(targetId)
    }

    override fun onClickModifyBtn(targetId: Int) {
        val content = commentList.find { it.commentId == targetId }?.content
        CommentEditFragment.newInstance(targetId, content).run{
            setOnClickListener(this@CommentMultiViewAdapter)
            show(fm, null)
        }
    }

    override fun onClickReportBtn(targetId: Int) {
        TODO("Not yet implemented")
    }

    override fun onClickModifyPostBtn(targetId: Int, content: String) {
        articleCommentViewModel.modifyComment(targetId, content)
    }
}