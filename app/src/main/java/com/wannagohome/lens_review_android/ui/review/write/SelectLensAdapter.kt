package com.wannagohome.lens_review_android.ui.review.write

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.wannagohome.lens_review_android.AppComponents
import com.wannagohome.lens_review_android.R
import com.wannagohome.lens_review_android.databinding.ItemSelectReviewLensBinding
import com.wannagohome.lens_review_android.network.model.LensPreview
import com.wannagohome.lens_review_android.support.baseclass.BaseSimpleAdapter

class SelectLensAdapter : BaseSimpleAdapter<LensPreview, SelectLensAdapter.LensPreviewViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LensPreviewViewHolder {
        val binding = ItemSelectReviewLensBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return LensPreviewViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LensPreviewViewHolder, position: Int) {
        holder.bindItems(items[position])
    }

    inner class LensPreviewViewHolder(private val itemBinding: ItemSelectReviewLensBinding) : RecyclerView.ViewHolder(itemBinding.root) {

        fun bindItems(lensPreview: LensPreview) {
            Glide.with(AppComponents.applicationContext).load(lensPreview.productImages[0]).into(itemBinding.selectedLensImage)

            itemBinding.selectedLensName.text = lensPreview.name

            if(lensPreview.selected){
                itemBinding.root.setBackgroundResource(R.drawable.select_lens_border)
            }
            else{
                itemBinding.root.setBackgroundResource(0)
            }
        }
    }
}