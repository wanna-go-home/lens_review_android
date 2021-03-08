package com.wannagohome.viewty.ui.mypage.myarticlecomment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.jakewharton.rxbinding4.view.clicks
import com.wannagohome.viewty.R
import com.wannagohome.viewty.databinding.ItemMyCommentBinding
import com.wannagohome.viewty.network.model.comment.CommentType
import com.wannagohome.viewty.support.UtcHelper
import com.wannagohome.viewty.network.model.comment.Comment
import com.wannagohome.viewty.support.baseclass.BaseSimpleAdapter
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
            if(comment.type == CommentType.ARTICLE.typeName){
                val drawable = ContextCompat.getDrawable(itemView.context, R.drawable.ic_my_comment_article)
                itemBinding.typeImage.setImageDrawable(drawable)

            }
            else if(comment.type == CommentType.REVIEW.typeName){
                val drawable = ContextCompat.getDrawable(itemView.context, R.drawable.ic_my_comment_review)
                itemBinding.typeImage.setImageDrawable(drawable)
            }

            itemBinding.comment.text = comment.content

            itemBinding.commentDate.text = UtcHelper.convertUTCtoDateString(comment.createdAt)
        }
    }
}