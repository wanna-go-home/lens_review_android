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
import okhttp3.internal.notify
import timber.log.Timber

class SelectLensAdapter : RecyclerView.Adapter<SelectLensAdapter.LensPreviewViewHolder>() {
    var onItemClick: ((Int) -> Unit)? = null

    var items: MutableList<LensPreview> = mutableListOf()
        set(list) {

            field = list
            notifyDataSetChanged()
        }

    override fun getItemCount(): Int {
        return items.size
    }

    fun getItem(pos: Int): LensPreview {
        return items[pos]
    }

    var previousItems = items.toMutableList().map{it.clone()}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LensPreviewViewHolder {
        val binding = ItemSelectReviewLensBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return LensPreviewViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LensPreviewViewHolder, position: Int) {
        holder.bindItems(items[position])
        Timber.d("holder kgp hash " + items.hashCode())
    }

    fun discardSelectedLens(){
        Timber.d("kgp hash1 " + items.hashCode())
        Timber.d("kgp hash2 " + previousItems.hashCode())
        Timber.d("kgp sel1 " + items.first { it.selected }.lensId)
        Timber.d("kgp sel2 " + previousItems.first { it.selected }.lensId)
        //items = previousItems.map{it.clone()}
        items.clear()
        items.addAll(previousItems.map{it.clone()})

        Timber.d("kgp hash11 " + items.hashCode())
        Timber.d("kgp hash22 " + previousItems.hashCode())
        Timber.d("kgp sel1 " + items.first { it.selected }.lensId)
        Timber.d("kgp sel2 " + previousItems.first { it.selected }.lensId)
        notifyDataSetChanged()
    }

    fun confirmSelectedLens(){
        previousItems = items.toMutableList().map{it.clone()}
    }

    inner class LensPreviewViewHolder(private val itemBinding: ItemSelectReviewLensBinding) : RecyclerView.ViewHolder(itemBinding.root) {

        fun bindItems(lensPreview: LensPreview) {
            Glide.with(AppComponents.applicationContext).load(lensPreview.productImages[0]).into(itemBinding.selectedLensImage)

            itemBinding.selectedLensName.text = lensPreview.name

            if(lensPreview.selected){
                Timber.d("kgp selected !! " + lensPreview.lensId)
                itemBinding.root.setBackgroundResource(R.drawable.select_lens_border)
            }
            else{
                itemBinding.root.setBackgroundResource(0)
            }
        }
    }
}