package com.wannagohome.lens_review_android.ui.review.review_list

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.wannagohome.lens_review_android.R
import com.wannagohome.lens_review_android.network.model.review.ReviewPreview
import com.wannagohome.lens_review_android.ui.review.review_detail.ReviewDetailActivity
import com.wannagohome.lens_review_android.ui.review.write.WriteReviewActivity
import kotlinx.android.synthetic.main.fragment_review.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber


class TabReview : Fragment() {

    private val reviewPreviewViewModel: ReviewPreviewViewModel by viewModel()

    private val reviewPreviewAdapter = ReviewListAdapter(object :
        ReviewListAdapter.OnItemClickListener {
        override fun onItemClick(clickedReviewPreview: ReviewPreview) {
            ReviewDetailActivity.startReviewDetailActivity(context!!, clickedReviewPreview.id)
        }
    })

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_review, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initReviewPreviewRecyclerView()

        addListener()

        observeEvents()
    }

    override fun onStart() {
        super.onStart()

        reviewPreviewViewModel.fetchReviewPreview()
    }

    private fun addListener() {
        writeBtn.setOnClickListener {
            val intent = Intent(requireContext(), WriteReviewActivity::class.java)
            startActivity(intent)
        }
    }

    private fun initReviewPreviewRecyclerView() {
        reviewRecyclerView.run {
            addItemDecoration((DividerItemDecoration(context, LinearLayoutManager.VERTICAL)))
            layoutManager = LinearLayoutManager(context)
            adapter = reviewPreviewAdapter
        }
    }

    private fun observeEvents() {
        reviewPreviewViewModel.reviewPreviewList.observe(viewLifecycleOwner, Observer {
            reviewPreviewAdapter.reviewPreviewList = it
        })
    }

    companion object {
        val instance = TabReview()
    }
}