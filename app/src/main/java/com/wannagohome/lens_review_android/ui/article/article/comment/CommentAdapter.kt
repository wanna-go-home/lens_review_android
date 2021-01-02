package com.wannagohome.lens_review_android.ui.article.article.comment

import android.view.LayoutInflater
import com.wannagohome.lens_review_android.network.model.helper.dateHelper
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.wannagohome.lens_review_android.databinding.ChildCommentListItemBinding
import com.wannagohome.lens_review_android.databinding.CommentListItemBinding
import com.wannagohome.lens_review_android.network.model.article.Comment

const val COMMENT = 0
const val INNER_COMMENT = 1
class CommentMultiViewAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var commentList = ArrayList<Comment>()
        set(shops) {
            field = shops

            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            COMMENT -> {
                val binding = CommentListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                CommentViewHolder(binding)
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

    inner class CommentViewHolder(private val itemBinding: CommentListItemBinding) : RecyclerView.ViewHolder(itemBinding.root) {
        private lateinit var currentComment: Comment

        fun bind(comment: Comment) {
            currentComment = comment
            itemBinding.content.text = comment.content
            itemBinding.author.text = comment.author
            itemBinding.likes.text = comment.likes.toString()
            itemBinding.createdAt.text = dateHelper.calcCreatedBefore(comment.createdAt)
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
        }
    }
}