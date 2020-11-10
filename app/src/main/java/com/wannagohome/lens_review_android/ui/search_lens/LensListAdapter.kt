package com.wannagohome.lens_review_android.ui.search_lens

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.wannagohome.lens_review_android.R
import com.wannagohome.lens_review_android.network.model.LensPreview
import kotlinx.android.synthetic.main.lens_list_item.view.*

class LensListAdapter(val itemClickListener: OnItemClickListener) : RecyclerView.Adapter<LensListAdapter.LensListViewHolder>() {

    var lensList = ArrayList<LensPreview>()
        set(shops) {
            field = shops

            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LensListViewHolder {
        return LensListViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.lens_list_item, parent, false))
    }

    override fun getItemCount() = lensList.size

    override fun onBindViewHolder(holder: LensListViewHolder, position: Int) {
        holder.bind(lensList[position])
    }

    interface OnItemClickListener {
        fun onItemClick(clickedLens: LensPreview)
    }

    inner class LensListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        lateinit var currentLens: LensPreview

        init {
            itemView.setOnClickListener {
                itemClickListener.onItemClick(currentLens)
            }
        }

        fun bind(lens: LensPreview) {
            currentLens = lens

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