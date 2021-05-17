package com.wannagohome.viewty.ui.lips

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.wannagohome.viewty.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TabLips : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_lips, container, false)
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            TabLips()
    }
}