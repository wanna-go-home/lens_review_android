package com.wannagohome.lens_review_android.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.wannagohome.lens_review_android.R
import com.wannagohome.lens_review_android.network.model.Lens
import kotlinx.android.synthetic.main.lens_list_item.view.*

class LensListAdapter : RecyclerView.Adapter<LensListAdapter.BookListViewHolder>() {

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

    inner class BookListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(lens: Lens) {
            itemView.lensName.text = lens.name
        }
    }
}