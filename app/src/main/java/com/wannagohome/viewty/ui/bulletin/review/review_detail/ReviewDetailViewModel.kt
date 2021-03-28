package com.wannagohome.viewty.ui.bulletin.review.review_detail

import androidx.lifecycle.MutableLiveData
import com.wannagohome.viewty.extension.addTo
import com.wannagohome.viewty.network.lensapi.LensApiClient
import com.wannagohome.viewty.network.model.review.Review
import com.wannagohome.viewty.support.baseclass.BaseViewModel
import org.koin.core.KoinComponent
import org.koin.core.inject
import retrofit2.HttpException
import timber.log.Timber


class ReviewDetailViewModel (private val reviewId: Int): BaseViewModel(), KoinComponent {

    val review = MutableLiveData<Review>()
    val deleteSuccess = MutableLiveData<Boolean>(false)
    val reportSuccess = MutableLiveData<Boolean>(false)

    private val lensClient: LensApiClient by inject()

    fun getReview() {
        compositeDisposable.add(lensClient.getReviewById(reviewId).subscribe({
            review.value = it.body()
        }, {
            //TODO error notification
            if (it is HttpException) {
                val exception = it
                Timber.e("HTTP Exception ${exception.response()}")
            }

        }))
    }

    fun like() {
        lensClient.postReviewLike(reviewId)
            .subscribe( {
                review.value = it.body()
            }, {
            })
            .addTo(compositeDisposable)
    }

    fun unlike() {
        lensClient.deleteReviewLike(reviewId)
            .subscribe( {
                review.value = it.body()
            }, {
            })
            .addTo(compositeDisposable)
    }

    fun deleteReview() {
        lensClient.deleteReviewById(reviewId)
            .subscribe( {
                deleteSuccess.value = true
            }, {
            })
            .addTo(compositeDisposable)
    }

    fun reportReview(){
        reportSuccess.value = true
    }
}