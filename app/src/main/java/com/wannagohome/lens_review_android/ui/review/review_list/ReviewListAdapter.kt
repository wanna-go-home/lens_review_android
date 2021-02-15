package com.wannagohome.lens_review_android.ui.review.review_list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jakewharton.rxbinding4.view.clicks
import com.wannagohome.lens_review_android.databinding.ReviewListItemBinding
import com.wannagohome.lens_review_android.network.model.helper.dateHelper
import com.wannagohome.lens_review_android.network.model.review.Review
import com.wannagohome.lens_review_android.support.baseclass.BaseSimpleAdapter
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import java.util.concurrent.TimeUnit


class ReviewListAdapter : BaseSimpleAdapter<Review, ReviewListAdapter.LenseListViewHolder>() {

    var onLikeClick: ((Int) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LenseListViewHolder {
        val binding = ReviewListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LenseListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LenseListViewHolder, position: Int) {
        holder.bind(items[position])
    }

    inner class LenseListViewHolder(private val itemBinding: ReviewListItemBinding) : RecyclerView.ViewHolder(itemBinding.root) {

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

        fun bind(review: Review) {

            itemBinding.reviewTitle.text = review.title

            itemBinding.reviewContents.text = review.content

            itemBinding.pageviewNum.text = review.viewCnt.toString()

            itemBinding.commentNum.text = review.replyCnt.toString()

            itemBinding.likesIcon.isChecked = review.isLiked

            itemBinding.likeNum.text = review.likeCnt.toString()

            itemBinding.reviewWriter.text = review.nickname

            itemBinding.time.text = dateHelper.calcCreatedBefore(review.createdAt)

            Glide.with(itemView.context).load(review.lens.productImages[0])
                .into(itemBinding.lensImage)


        }
    }
}