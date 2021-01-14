package com.wannagohome.lens_review_android.ui.review.review_detail

import androidx.lifecycle.MutableLiveData
import com.wannagohome.lens_review_android.extension.addTo
import com.wannagohome.lens_review_android.network.lensapi.LensApiClient
import com.wannagohome.lens_review_android.network.model.review.Review
import com.wannagohome.lens_review_android.support.baseclass.BaseViewModel
import org.koin.core.KoinComponent
import org.koin.core.inject
import retrofit2.HttpException
import timber.log.Timber


class ReviewDetailViewModel (private val reviewId: Int): BaseViewModel(), KoinComponent {

    val review = MutableLiveData<Review>()
    val deleteSuccess = MutableLiveData<Boolean>(false)

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

    fun deleteReview() {
        lensClient.deleteReviewById(reviewId)
            .subscribe( {
                deleteSuccess.value = true
            }, {
            })
            .addTo(compositeDisposable)
    }
}