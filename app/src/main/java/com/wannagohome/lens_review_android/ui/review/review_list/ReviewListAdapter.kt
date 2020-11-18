package com.wannagohome.lens_review_android.ui.review.review_list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.wannagohome.lens_review_android.R
import com.wannagohome.lens_review_android.network.model.review.ReviewPreview
import com.wannagohome.lens_review_android.support.baseclass.BaseSimpleAdapter
import kotlinx.android.synthetic.main.review_list_item.view.*


class ReviewListAdapter : BaseSimpleAdapter<ReviewPreview, ReviewListAdapter.BookListViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookListViewHolder {
        return BookListViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.review_list_item, parent, false))
    }

    override fun onBindViewHolder(holder: BookListViewHolder, position: Int) {
        holder.bind(items[position])
    }

    interface OnItemClickListener {
        fun onItemClick(clickedReviewPreview: ReviewPreview)
    }

    inner class BookListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        init {
            itemView.setOnClickListener {
                onItemClick?.invoke(adapterPosition)
            }
        }

        fun bind(reviewPreview: ReviewPreview) {

            itemView.reviewTitle.text = reviewPreview.title

            itemView.reviewContents.text = reviewPreview.content

            itemView.pageviewNum.text = "0"

            itemView.commentNum.text = reviewPreview.replyCnt.toString()

            itemView.likeNum.text = reviewPreview.likeCnt.toString()

            itemView.reviewWriter.text = reviewPreview.nickname

            itemView.time.text = reviewPreview.getDateTime() ?: ""

        }
    }
}