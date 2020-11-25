package com.wannagohome.lens_review_android.ui.board

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.wannagohome.lens_review_android.R
import com.wannagohome.lens_review_android.network.model.ArticlePreview
import com.wannagohome.lens_review_android.support.baseclass.BaseSimpleAdapter
import kotlinx.android.synthetic.main.article_list_item.view.*

class BoardListAdapter() : BaseSimpleAdapter<ArticlePreview, BoardListAdapter.BookListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookListViewHolder {
        return BookListViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.article_list_item,
                parent,
                false
            )
        )
    }


    override fun onBindViewHolder(holder: BookListViewHolder, position: Int) {
        holder.bind(items[position])
    }


    inner class BookListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        init{
            itemView.setOnClickListener{
                onItemClick?.invoke(adapterPosition)
            }
        }

        fun bind(article: ArticlePreview) {
            itemView.articleTitle.text = article.title
            itemView.content.text = article.content
            itemView.author.text = article.author
            itemView.views.text = article.views.toString()
            itemView.likes.text = article.likes.toString()
            itemView.comments.text = article.comments.toString()
            itemView.createdAt.text = article.createdAt
        }
    }
}