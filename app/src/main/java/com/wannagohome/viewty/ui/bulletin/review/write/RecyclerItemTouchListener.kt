package com.wannagohome.viewty.ui.bulletin.review.write

import android.content.Context
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import androidx.recyclerview.widget.RecyclerView


open class RecyclerItemTouchListener(c: Context, listener: OnItemClickListener) : RecyclerView.OnItemTouchListener {
    private val mGestureDetector = GestureDetector(c, object : GestureDetector.SimpleOnGestureListener(){
        override fun onSingleTapUp(e: MotionEvent?): Boolean {
            return true
        }
    })

    private val mListener: OnItemClickListener = listener

    interface OnItemClickListener {
        fun onItemClick(view: View?, position: Int)
    }

    override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
        val childView: View? = rv.findChildViewUnder(e.x, e.y)
        if (childView != null && mGestureDetector.onTouchEvent(e)) {
            mListener.onItemClick(childView, rv.getChildAdapterPosition(childView))
        }
        return false
    }

    override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {
    }

    override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {
    }
}