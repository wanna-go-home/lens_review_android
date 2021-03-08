package com.wannagohome.viewty.ui.review.write

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.wannagohome.viewty.AppComponents
import com.wannagohome.viewty.R
import com.wannagohome.viewty.databinding.ItemSelectReviewLensBinding
import com.wannagohome.viewty.network.model.LensPreview
import com.wannagohome.viewty.support.baseclass.BaseSimpleAdapter

class SelectLensAdapter : BaseSimpleAdapter<LensPreview, SelectLensAdapter.LensPreviewViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LensPreviewViewHolder {
        val binding = ItemSelectReviewLensBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return LensPreviewViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LensPreviewViewHolder, position: Int) {
        holder.bindItems(items[position])
    }

    fun refreshAdapter(selectedLensId : Int){
        items.onEach { it.selected = false }
        items.firstOrNull { it.lensId == selectedLensId }?.selected = true
        notifyDataSetChanged()

    }
    inner class LensPreviewViewHolder(private val itemBinding: ItemSelectReviewLensBinding) : RecyclerView.ViewHolder(itemBinding.root) {

        fun bindItems(lensPreview: LensPreview) {
            Glide.with(AppComponents.applicationContext).load(lensPreview.productImages[0]).into(itemBinding.selectedLensImage)

            itemBinding.selectedLensName.text = lensPreview.name

            if (lensPreview.selected) {
                itemBinding.root.setBackgroundResource(R.drawable.select_lens_border)
            } else {
                itemBinding.root.setBackgroundResource(0)
            }
        }
    }
}