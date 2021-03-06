package com.wannagohome.lens_review_android.ui.review.review_list

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.wannagohome.lens_review_android.databinding.FragmentReviewBinding
import com.wannagohome.lens_review_android.support.baseclass.BaseFragment
import com.wannagohome.lens_review_android.ui.review.review_detail.ReviewDetailActivity
import com.wannagohome.lens_review_android.ui.review.write.WriteReviewActivity
import org.koin.androidx.viewmodel.ext.android.viewModel


class TabReview : BaseFragment() {

    private var _binding: FragmentReviewBinding? = null
    private val binding get() = _binding!!

    private val reviewPreviewViewModel: ReviewPreviewViewModel by viewModel()

    private val reviewPreviewAdapter = ReviewListAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentReviewBinding.inflate(inflater, container, false)
        return binding.root
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
        binding.writeBtn.setOnClickListener {
            val intent = Intent(requireContext(), WriteReviewActivity::class.java)
            startActivity(intent)

        }
    }

    private fun initReviewPreviewRecyclerView() {
        binding.reviewRecyclerView.run {
            addItemDecoration((DividerItemDecoration(context, LinearLayoutManager.VERTICAL)))
            layoutManager = LinearLayoutManager(context)

            reviewPreviewAdapter.onItemClick = { pos ->
                val clickedReviewPreview = reviewPreviewAdapter.getItem(pos)

                val intent = Intent(context, ReviewDetailActivity::class.java)
                intent.putExtra(ReviewDetailActivity.REVIEW_ID, clickedReviewPreview.id)

                startActivityFromRight(intent)
            }

            reviewPreviewAdapter.onLikeClick = { pos ->
                val clickedReview = reviewPreviewAdapter.getItem(pos)
                if (clickedReview.isLiked){
                    reviewPreviewViewModel.unlike(clickedReview.id)
                }
                else{
                    reviewPreviewViewModel.like(clickedReview.id)
                }
            }

            adapter = reviewPreviewAdapter
        }
    }

    private fun observeEvents() {
        reviewPreviewViewModel.reviewPreviewList.observe(viewLifecycleOwner, {
            reviewPreviewAdapter.items = it
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }

    companion object {
        fun newInstance() = TabReview()
    }
}