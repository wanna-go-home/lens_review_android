package com.wannagohome.lens_review_android.ui.search_lens

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.wannagohome.lens_review_android.R
import com.wannagohome.lens_review_android.network.model.Lens
import kotlinx.android.synthetic.main.lens_list_item.view.*

class LensListAdapter(val itemClickListener: OnItemClickListener) : RecyclerView.Adapter<LensListAdapter.BookListViewHolder>() {

    var lensList = ArrayList<Lens>()
        set(shops) {
            field = shops

            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookListViewHolder {
        return BookListViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.lens_list_item, parent, false))
    }

    override fun getItemCount() = lensList.size

    override fun onBindViewHolder(holder: BookListViewHolder, position: Int) {
        holder.bind(lensList[position])
    }

    interface OnItemClickListener {
        fun onItemClick(clickedLens: Lens)
    }

    inner class BookListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        lateinit var currentLens : Lens

        init{
            itemView.setOnClickListener {
                itemClickListener.onItemClick(currentLens)
            }
        }

        fun bind(lens: Lens) {
            currentLens = lens

            bindProductImage(lens.productImage[0])

            bindName(lens.name)
        }

        private fun bindName(name: String) {
            itemView.lensName.text = name
        }

        private fun bindProductImage(imageUrl: String) {
            Glide.with(itemView.context).load(imageUrl).into(itemView.productImage)
        }
    }
}