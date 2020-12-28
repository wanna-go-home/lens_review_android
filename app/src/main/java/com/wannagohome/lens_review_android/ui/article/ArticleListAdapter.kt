package com.wannagohome.lens_review_android.ui.article

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.wannagohome.lens_review_android.network.model.helper.dateHelper
import com.wannagohome.lens_review_android.databinding.ArticleListItemBinding
import com.wannagohome.lens_review_android.network.model.ArticlePreview
import com.wannagohome.lens_review_android.support.baseclass.BaseSimpleAdapter


class ArticleListAdapter : BaseSimpleAdapter<ArticlePreview, ArticleListAdapter.ArticleListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleListViewHolder {
        val binding = ArticleListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ArticleListViewHolder(binding)
    }


    override fun onBindViewHolder(holder: ArticleListViewHolder, position: Int) {
        holder.bind(items[position])
    }


    inner class ArticleListViewHolder(private val itemBinding: ArticleListItemBinding) : RecyclerView.ViewHolder(itemBinding.root) {

        init {
            itemView.setOnClickListener {
                onItemClick?.invoke(adapterPosition)
            }
        }

        fun bind(article: ArticlePreview) {
            itemBinding.articleTitle.text = article.title
            itemBinding.content.text = article.content
            itemBinding.author.text = article.author
            itemBinding.views.text = article.views.toString()
            itemBinding.likes.text = article.likes.toString()
            itemBinding.comments.text = article.comments.toString()
            itemBinding.createdAt.text = dateHelper.calcCreatedBefore(article.createdAt)
        }
    }
}