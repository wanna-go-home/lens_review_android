package com.wannagohome.lens_review_android.ui.board


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.wannagohome.lens_review_android.R

class TabBoard : Fragment() {
    companion object{
        val instance = TabBoard()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_board, container, false)
    }

}