package com.wannagohome.lens_review_android.ui.review.review_list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.wannagohome.lens_review_android.databinding.ReviewListItemBinding
import com.wannagohome.lens_review_android.network.model.review.ReviewPreview
import com.wannagohome.lens_review_android.support.baseclass.BaseSimpleAdapter


class ReviewListAdapter : BaseSimpleAdapter<ReviewPreview, ReviewListAdapter.BookListViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookListViewHolder {
        val binding = ReviewListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BookListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BookListViewHolder, position: Int) {
        holder.bind(items[position])
    }

    inner class BookListViewHolder(private val itemBinding: ReviewListItemBinding) : RecyclerView.ViewHolder(itemBinding.root) {

        init {
            itemView.setOnClickListener {
                onItemClick?.invoke(adapterPosition)
            }
        }

        fun bind(reviewPreview: ReviewPreview) {

            itemBinding.reviewTitle.text = reviewPreview.title

            itemBinding.reviewContents.text = reviewPreview.content

            itemBinding.pageviewNum.text = reviewPreview.viewCnt.toString()

            itemBinding.commentNum.text = reviewPreview.replyCnt.toString()

            itemBinding.likeNum.text = reviewPreview.likeCnt.toString()

            itemBinding.reviewWriter.text = reviewPreview.nickname

            itemBinding.time.text = reviewPreview.getDateTime()

//            Glide.with(itemView.context).load(reviewPreview.lens.productImages[0])
//                .into(itemBinding.lensImage)


        }
    }
}