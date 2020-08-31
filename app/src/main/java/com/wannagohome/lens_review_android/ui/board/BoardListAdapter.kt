package com.wannagohome.lens_review_android.ui.board

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.wannagohome.lens_review_android.R
import com.wannagohome.lens_review_android.network.model.Article
import kotlinx.android.synthetic.main.article_list_item.view.*

class BoardListAdapter : RecyclerView.Adapter<BoardListAdapter.BookListViewHolder>() {

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

    inner class BookListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(article: Article) {
            itemView.articleTitle.text = article.title
        }
    }
}