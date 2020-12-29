package com.wannagohome.lens_review_android.ui.review.write

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.wannagohome.lens_review_android.R

class SelectLensFragment : Fragment() {

    companion object {
        val instance = SelectLensFragment()
    }

    private lateinit var viewModel: SelectLensViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.select_lens_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(SelectLensViewModel::class.java)
        // TODO: Use the ViewModel
    }

}