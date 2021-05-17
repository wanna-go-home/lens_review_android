package com.wannagohome.viewty.ui.bulletin.review.review_list

import androidx.lifecycle.MutableLiveData
import com.wannagohome.viewty.extension.addTo
import com.wannagohome.viewty.network.lensapi.LensApiClient
import com.wannagohome.viewty.network.model.review.Review
import com.wannagohome.viewty.support.baseclass.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class ReviewPreviewViewModel @Inject constructor() : BaseViewModel() {

    @Inject
    lateinit var lensApiClient: LensApiClient

    val reviewPreviewList = MutableLiveData<List<Review>>()

    fun fetchReviewPreview() {
        lensApiClient.getAllReviews()
            .subscribe {
                val body = it.body()

                body?.let {
                    reviewPreviewList.value = it
                }
            }.addTo(compositeDisposable)
    }

    fun like(articleId: Int) {
        lensApiClient.postReviewLike(articleId)
            .subscribe({
                val newReview = it.body()
                var oldArticleList = reviewPreviewList.value?.toMutableList()
                if (oldArticleList != null && newReview != null) {
                    val idx = oldArticleList.indexOfFirst { review -> review.id == newReview.id }
                    oldArticleList[idx] = newReview
                }
                reviewPreviewList.value = oldArticleList
            }, {
            })
            .addTo(compositeDisposable)
    }

    fun unlike(articleId: Int) {
        lensApiClient.deleteReviewLike(articleId)
            .subscribe({
                val newReview = it.body()
                var oldArticleList = reviewPreviewList.value?.toMutableList()
                if (oldArticleList != null && newReview != null) {
                    val idx = oldArticleList.indexOfFirst { review -> review.id == newReview.id }
                    oldArticleList[idx] = newReview
                }
                reviewPreviewList.value = oldArticleList
            }, {
            })
            .addTo(compositeDisposable)
    }

}