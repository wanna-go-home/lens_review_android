package com.wannagohome.lens_review_android.ui.search_lens

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.wannagohome.lens_review_android.R
import com.wannagohome.lens_review_android.network.model.LensPreview
import com.wannagohome.lens_review_android.support.baseclass.BaseSimpleAdapter
import kotlinx.android.synthetic.main.lens_list_item.view.*

class LensListAdapter : BaseSimpleAdapter<LensPreview, LensListAdapter.LensListViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LensListViewHolder {
        return LensListViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.lens_list_item, parent, false))
    }

    override fun onBindViewHolder(holder: LensListViewHolder, position: Int) {
        holder.bind(items[position])
    }

    inner class LensListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        init {
            itemView.setOnClickListener {
                onItemClick?.invoke(adapterPosition)
            }
        }

        fun bind(lens: LensPreview) {

            bindProductImage(lens.productImages[0])

            bindName(lens.name)



            //TODO 제조사 정리
            itemView.maker.text = "메이커"

            //TODO 함수분리
            itemView.lensNumber.text = "${lens.lensId}"
//            itemView.graphicDia.text = "${lens.graphicDia}"
            itemView.price.text = "${lens.price}원"
            itemView.score.text = "4.15"

        }

        private fun bindName(name: String) {
            itemView.lensName.text = name
        }

        private fun bindProductImage(imageUrl: String) {
            Glide.with(itemView.context).load(imageUrl).into(itemView.productImage)
        }
    }
}