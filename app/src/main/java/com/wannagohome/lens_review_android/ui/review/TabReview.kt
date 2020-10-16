package com.wannagohome.lens_review_android.ui.review

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.wannagohome.lens_review_android.R
import kotlinx.android.synthetic.main.fragment_tab_review.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class TabReview : Fragment() {

    private val reviewPreviewViewModel: ReviewPreviewViewModel by viewModel()

    private val reviewPreviewAdapter = ReviewListAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_tab_review, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initReviewPreviewRecyclerView()

        observeEvents()

        reviewPreviewViewModel.fetchReviewPreview()
    }

    private fun initReviewPreviewRecyclerView(){
        reviewRecyclerView.run{
            addItemDecoration((DividerItemDecoration(context, LinearLayoutManager.VERTICAL)))
            layoutManager = LinearLayoutManager(context)
            adapter = reviewPreviewAdapter
        }
    }

    private fun observeEvents(){
        reviewPreviewViewModel.reviewPreviewList.observe(viewLifecycleOwner, Observer{
            reviewPreviewAdapter.reviewPreviewList = it
        })
    }

    companion object {
        val instance = TabReview()
    }
}