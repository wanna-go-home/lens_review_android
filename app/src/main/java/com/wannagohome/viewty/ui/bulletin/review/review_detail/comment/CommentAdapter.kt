package com.wannagohome.viewty.ui.bulletin.review.review_detail.comment

import android.view.LayoutInflater
import com.wannagohome.viewty.network.model.helper.dateHelper
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.jakewharton.rxbinding4.view.clicks
import com.wannagohome.viewty.R
import com.wannagohome.viewty.databinding.ChildCommentListItemBinding
import com.wannagohome.viewty.databinding.CommentListItemBinding
import com.wannagohome.viewty.extension.gone
import com.wannagohome.viewty.extension.invisible
import com.wannagohome.viewty.extension.visible
import com.wannagohome.viewty.network.model.comment.Comment
import com.wannagohome.viewty.support.Utils.getString
import com.wannagohome.viewty.ui.BottomSheetFragment
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import java.util.concurrent.TimeUnit

class CommentMultiViewAdapter(private val fm: FragmentManager, private val reviewCommentViewModel: ReviewCommentViewModel, private val IS_REVIEW: Boolean) : RecyclerView.Adapter<RecyclerView.ViewHolder>(),
    BottomSheetFragment.OnClickListener,
    CommentEditFragment.OnClickListener{

    companion object {
        const val MAX_CHILDREN_IN_REVIEW = 3
        const val REVIEW_ID = "reviewId"
        const val COMMENT_ID = "commentId"
        const val COMMENT = 0
        const val INNER_COMMENT = 1
    }

    var onLikeClick: ((Int) -> Unit)? = null
    var onMoreCommentClick: ((Int) -> Unit)? = null
    var onOptionClick: ((Int) -> Unit)? = null
    var onCommentsClick: ((Int) -> Unit)? = null

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
        init {
            itemBinding.likesIcon.clicks()
                .observeOn(AndroidSchedulers.mainThread())
                .throttleFirst(300, TimeUnit.MILLISECONDS)
                .subscribe {
                    onLikeClick?.invoke(absoluteAdapterPosition)
                }

            itemBinding.moreComment.clicks()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    onMoreCommentClick?.invoke(absoluteAdapterPosition)
                }

            itemBinding.optionBtn.clicks()
                .observeOn(AndroidSchedulers.mainThread())
                .throttleFirst(300,TimeUnit.MILLISECONDS)
                .subscribe {
                    onOptionClick?.invoke(absoluteAdapterPosition)
                }

            itemBinding.comments.clicks()
                .observeOn(AndroidSchedulers.mainThread())
                .throttleFirst(300,TimeUnit.MILLISECONDS)
                .subscribe {
                    onCommentsClick?.invoke(absoluteAdapterPosition)
                }
        }
        fun bind(comment: Comment) {
            currentComment = comment
            itemBinding.content.text = comment.content
            itemBinding.nickname.text = comment.nickname
            itemBinding.likesIcon.isChecked = comment.isLiked
            itemBinding.likes.text = comment.likes.toString()
            itemBinding.createdAt.text = dateHelper.calcCreatedBefore(comment.createdAt)

            //@todo : let "더 보기" be recyclerview item
            if (IS_REVIEW && comment.bundleSize > MAX_CHILDREN_IN_REVIEW) {
                    val nOfComments = String.format(
                        getString(R.string.show_more_comments),
                        comment.bundleSize - MAX_CHILDREN_IN_REVIEW
                    )
                    itemBinding.moreComment.text = nOfComments
                    itemBinding.moreComment.visible()
            }
            else{
                itemBinding.moreComment.gone()
            }
        }
    }

    inner class ChildCommentViewHolder(private val itemBinding: ChildCommentListItemBinding) : RecyclerView.ViewHolder(itemBinding.root) {
        private lateinit var currentComment: Comment
        init {
            itemBinding.likesIcon.clicks()
                .observeOn(AndroidSchedulers.mainThread())
                .throttleFirst(300,TimeUnit.MILLISECONDS)
                .subscribe {
                    onLikeClick?.invoke(absoluteAdapterPosition)
                }
            itemBinding.optionBtn.clicks()
                .observeOn(AndroidSchedulers.mainThread())
                .throttleFirst(300,TimeUnit.MILLISECONDS)
                .subscribe {
                    onOptionClick?.invoke(absoluteAdapterPosition)
                }
        }
        fun bind(comment: Comment) {
            currentComment = comment
            itemBinding.content.text = comment.content
            itemBinding.nickname.text = comment.nickname
            itemBinding.likesIcon.isChecked = comment.isLiked
            itemBinding.likes.text = comment.likes.toString()
            itemBinding.createdAt.text = dateHelper.calcCreatedBefore(comment.createdAt)

            if (IS_REVIEW) {
                itemBinding.optionBtn.invisible()
            }

        }
    }

    override fun onClickDeleteBtn(targetId: Int) {
        reviewCommentViewModel.deleteComment(targetId)
    }

    override fun onClickModifyBtn(targetId: Int) {
        val content = commentList.find { it.commentId == targetId }?.content
        CommentEditFragment.newInstance(targetId, content).run{
            setOnClickListener(this@CommentMultiViewAdapter)
            show(fm, null)
        }
    }

    override fun onClickReportBtn(targetId: Int) {
        //todo:  implement report
        reviewCommentViewModel.reportComment()
    }

    override fun onClickModifyPostBtn(targetId: Int, content: String) {
        reviewCommentViewModel.modifyComment(targetId, content)
    }
}