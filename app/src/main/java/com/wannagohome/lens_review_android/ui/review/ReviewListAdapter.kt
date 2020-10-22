package com.wannagohome.lens_review_android.ui.review

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.wannagohome.lens_review_android.R
import com.wannagohome.lens_review_android.network.model.ReviewPreview
import kotlinx.android.synthetic.main.review_list_item.view.*


class ReviewListAdapter(val itemClickListener: OnItemClickListener? = null) : RecyclerView.Adapter<ReviewListAdapter.BookListViewHolder>() {

    var reviewPreviewList = listOf<ReviewPreview>()
        set(shops) {
            field = shops

            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookListViewHolder {
        return BookListViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.review_list_item, parent, false))
    }

    override fun getItemCount() = reviewPreviewList.size

    override fun onBindViewHolder(holder: BookListViewHolder, position: Int) {
        holder.bind(reviewPreviewList[position])
    }

    interface OnItemClickListener {
        fun onItemClick(clickedReviewPreview: ReviewPreview)
    }

    inner class BookListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        lateinit var currentReviewPreview: ReviewPreview

        init {
            itemView.setOnClickListener {
                itemClickListener?.onItemClick(currentReviewPreview)
            }
        }

        fun bind(reviewPreview: ReviewPreview) {
            currentReviewPreview = reviewPreview

            itemView.reviewTitle.text = reviewPreview.title

            itemView.reviewContents.text = reviewPreview.content

            itemView.pageviewNum.text = "0"
            itemView.commentNum.text = "0"
            itemView.likeNum.text = "0"


        }


    }
}