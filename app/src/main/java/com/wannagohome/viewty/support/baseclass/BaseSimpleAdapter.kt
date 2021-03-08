package com.wannagohome.viewty.support.baseclass

import androidx.recyclerview.widget.RecyclerView

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