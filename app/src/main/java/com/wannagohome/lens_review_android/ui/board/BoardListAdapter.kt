package com.wannagohome.lens_review_android.ui.board

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.wannagohome.lens_review_android.R
import com.wannagohome.lens_review_android.network.model.Article
import kotlinx.android.synthetic.main.article_list_item.view.*

class BoardListAdapter(val itemClickListener: OnItemClickListener) : RecyclerView.Adapter<BoardListAdapter.BookListViewHolder>() {

    var articleList = ArrayList<Article>()
        set(shops) {
            field = shops

            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookListViewHolder {
        return BookListViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.article_list_item, parent, false))
    }

    override fun getItemCount() = articleList.size

    override fun onBindViewHolder(holder: BookListViewHolder, position: Int) {
        holder.bind(articleList[position])
    }

    interface OnItemClickListener {
        fun onItemClick(clickedArticle: Article)
    }

    inner class BookListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        lateinit var currentArticle : Article

        init{
            itemView.setOnClickListener{
                itemClickListener.onItemClick(currentArticle)
            }
        }

        fun bind(article: Article) {
            currentArticle = article
            itemView.title.text = article.title
            itemView.content.text = article.content
            itemView.author.text = article.nickName
            itemView.views.text = article.views.toString()
            itemView.likes.text = article.likes.toString()
            itemView.comments.text = article.comments.toString()
            itemView.createdAt.text = article.createdAt
        }
    }
}