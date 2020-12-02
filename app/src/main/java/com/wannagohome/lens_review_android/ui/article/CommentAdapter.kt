package com.wannagohome.lens_review_android.ui.article

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.wannagohome.lens_review_android.databinding.ChildCommentListItemBinding
import com.wannagohome.lens_review_android.databinding.CommentListItemBinding
import com.wannagohome.lens_review_android.network.model.Comment

class CommentMultiViewAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var commentList = ArrayList<Comment>()
        set(shops) {
            field = shops

            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            0 -> {
                val binding = CommentListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                CommentViewHolder(binding)
            }
            1 -> {
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
            0 -> (holder as CommentViewHolder).bind(obj)
            1 -> (holder as ChildCommentViewHolder).bind(obj)
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
            itemBinding.author.text = comment.authorId
            itemBinding.likes.text = comment.likes.toString()
            itemBinding.createdAt.text = comment.createdAt
        }
    }

    inner class ChildCommentViewHolder(private val itemBinding: ChildCommentListItemBinding) : RecyclerView.ViewHolder(itemBinding.root) {
        private lateinit var currentComment: Comment

        fun bind(comment: Comment) {
            currentComment = comment
            itemBinding.content.text = comment.content
            itemBinding.author.text = comment.authorId
            itemBinding.createdAt.text = comment.createdAt
        }
    }
}