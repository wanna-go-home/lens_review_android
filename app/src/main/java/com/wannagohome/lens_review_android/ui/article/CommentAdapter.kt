package com.wannagohome.lens_review_android.ui.article

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.wannagohome.lens_review_android.R
import com.wannagohome.lens_review_android.network.model.Article
import com.wannagohome.lens_review_android.network.model.Comment
import kotlinx.android.synthetic.main.article_list_item.view.*

class CommentAdapter() : RecyclerView.Adapter<CommentAdapter.CommentViewHolder>() {

    var commentList = ArrayList<Comment>()

    set(shops) {
        field = shops

        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        return CommentViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.comment_list_item, parent, false))
    }

    override fun getItemCount() = commentList.size

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        holder.bind(commentList[position])
    }


    inner class CommentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        lateinit var currentComment : Comment

        init{
        }

        fun bind(comment: Comment) {
            currentComment = comment
            itemView.content.text = comment.content
            itemView.author.text = comment.nickName
            itemView.likes.text = comment.likes.toString()
            itemView.createdAt.text = comment.createdAt
        }
    }
}