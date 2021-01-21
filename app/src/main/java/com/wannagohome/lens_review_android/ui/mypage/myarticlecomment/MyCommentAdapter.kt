package com.wannagohome.lens_review_android.ui.mypage.myarticlecomment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jakewharton.rxbinding4.view.clicks
import com.wannagohome.lens_review_android.databinding.ItemMyCommentBinding
import com.wannagohome.lens_review_android.network.model.Comment
import com.wannagohome.lens_review_android.support.baseclass.BaseSimpleAdapter
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers

class MyCommentAdapter : BaseSimpleAdapter<Comment, MyCommentAdapter.CommentViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        val binding = ItemMyCommentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CommentViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        holder.bind(items[position])
    }

    inner class CommentViewHolder(private val itemBinding: ItemMyCommentBinding) : RecyclerView.ViewHolder(itemBinding.root) {
        init {
            itemView.clicks()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    onItemClick?.invoke(absoluteAdapterPosition)
                }
        }

        fun bind(comment: Comment) {
            itemBinding.comment.text = comment.content

        }
    }
}