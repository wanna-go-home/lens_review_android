package com.wannagohome.viewty.ui.video

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.wannagohome.viewty.R

class TabVideo : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tab_video, container, false)
    }

    companion object {

        @JvmStatic
        fun newInstance() =
            TabVideo()
    }
}