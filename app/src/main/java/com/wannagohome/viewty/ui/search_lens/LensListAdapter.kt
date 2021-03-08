package com.wannagohome.viewty.ui.search_lens

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.wannagohome.viewty.databinding.LensListItemBinding
import com.wannagohome.viewty.network.model.LensPreview
import com.wannagohome.viewty.support.baseclass.BaseSimpleAdapter

class LensListAdapter : BaseSimpleAdapter<LensPreview, LensListAdapter.LensListViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LensListViewHolder {
        val binding = LensListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LensListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LensListViewHolder, position: Int) {
        holder.bind(items[position])
    }

    inner class LensListViewHolder(private val itemBinding: LensListItemBinding) : RecyclerView.ViewHolder(itemBinding.root) {

        init {
            itemView.setOnClickListener {
                onItemClick?.invoke(absoluteAdapterPosition)
            }
        }

        fun bind(lens: LensPreview) {

            bindProductImage(lens.productImages[0])

            bindName(lens.name)


//            //TODO 제조사 정리
//            itemBinding.maker.text = "메이커"

            //TODO 함수분리
            itemBinding.lensNumber.text = "${lens.lensId}"
//            itemView.graphicDia.text = "${lens.graphicDia}"
            itemBinding.price.text = "${lens.price}원"
//            itemBinding.score.text = "4.15"

        }

        private fun bindName(name: String) {
            itemBinding.lensName.text = name
        }

        private fun bindProductImage(imageUrl: String) {
            Glide.with(itemView.context).load(imageUrl).into(itemBinding.productImage)
        }
    }
}