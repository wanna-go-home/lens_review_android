package com.wannagohome.lens_review_android.ui.review.review_list

import androidx.lifecycle.MutableLiveData
import com.wannagohome.lens_review_android.network.lensapi.LensApiClient
import com.wannagohome.lens_review_android.support.baseclass.BaseViewModel
import com.wannagohome.lens_review_android.extension.addTo
import com.wannagohome.lens_review_android.network.model.review.Review
import org.koin.core.inject


class ReviewPreviewViewModel : BaseViewModel(){

    private val lensApiClient : LensApiClient by inject()

    val reviewPreviewList = MutableLiveData<List<Review>>()

    fun fetchReviewPreview(){
        lensApiClient.getAllReviews()
            .subscribe{
                val body = it.body()

                body?.let{
                    reviewPreviewList.value = it
                }
            }.addTo(compositeDisposable)
    }

    fun like(articleId: Int) {
        lensApiClient.postReviewLike(articleId)
            .subscribe( {
                val newReview = it.body()
                var oldArticleList = reviewPreviewList.value?.toMutableList()
                if (oldArticleList!=null && newReview !=null){
                    val idx = oldArticleList.indexOfFirst { review ->  review.id == newReview.id }
                    oldArticleList[idx] = newReview
                }
                reviewPreviewList.value = oldArticleList
            }, {
            })
            .addTo(compositeDisposable)
    }

    fun unlike(articleId: Int) {
        lensApiClient.deleteReviewLike(articleId)
            .subscribe( {
                val newReview = it.body()
                var oldArticleList = reviewPreviewList.value?.toMutableList()
                if (oldArticleList!=null && newReview !=null){
                    val idx = oldArticleList.indexOfFirst { review ->  review.id == newReview.id }
                    oldArticleList[idx] = newReview
                }
                reviewPreviewList.value = oldArticleList
            }, {
            })
            .addTo(compositeDisposable)
    }

}