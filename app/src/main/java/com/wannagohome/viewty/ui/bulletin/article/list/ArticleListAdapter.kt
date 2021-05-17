package com.wannagohome.viewty.ui.bulletin.article.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jakewharton.rxbinding4.view.clicks
import com.wannagohome.viewty.network.model.helper.dateHelper
import com.wannagohome.viewty.databinding.ArticleListItemBinding
import com.wannagohome.viewty.network.model.article.Article
import com.wannagohome.viewty.support.baseclass.BaseSimpleAdapter
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import java.util.concurrent.TimeUnit


class ArticleListAdapter : BaseSimpleAdapter<Article, ArticleListAdapter.ArticleListViewHolder>() {

    var onLikeClick: ((Int) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleListViewHolder {
        val binding = ArticleListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ArticleListViewHolder(binding)
    }


    override fun onBindViewHolder(holder: ArticleListViewHolder, position: Int) {
        holder.bind(items[position])
    }


    inner class ArticleListViewHolder(private val itemBinding: ArticleListItemBinding) : RecyclerView.ViewHolder(itemBinding.root) {

        init {
            itemBinding.mainContentView.setOnClickListener {
                onItemClick?.invoke(absoluteAdapterPosition)
            }

            itemBinding.likesIcon.clicks()
                .observeOn(AndroidSchedulers.mainThread())
                .throttleFirst(300, TimeUnit.MILLISECONDS)
                .subscribe {
                    onLikeClick?.invoke(absoluteAdapterPosition)
                }
        }

        fun bind(article: Article) {
            itemBinding.articleTitle.text = article.title
            itemBinding.content.text = article.content
            itemBinding.nickname.text = article.nickname
            itemBinding.views.text = article.views.toString()
            itemBinding.likesIcon.isChecked = article.isLiked
            itemBinding.likes.text = article.likes.toString()
            itemBinding.comments.text = article.comments.toString()
            itemBinding.createdAt.text = dateHelper.calcCreatedBefore(article.createdAt)
        }
    }
}