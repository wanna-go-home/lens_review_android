package com.wannagohome.lens_review_android.support.baseclass

import androidx.recyclerview.widget.RecyclerView
import timber.log.Timber

abstract class BaseSimpleAdapter<T, VH : RecyclerView.ViewHolder> : RecyclerView.Adapter<VH>() {

    var onItemClick: ((Int) -> Unit)? = null

    var items: List<T> = emptyList()
        set(list) {

            field = list
            notifyDataSetChanged()
        }

    override fun getItemCount(): Int {
        return items.size
    }

    fun getItem(pos: Int): T {
        return items[pos]
    }
}