package com.wannagohome.lens_review_android.ui.review.review_list

import androidx.lifecycle.MutableLiveData
import com.wannagohome.lens_review_android.network.lensapi.LensApiClient
import com.wannagohome.lens_review_android.network.model.review.ReviewPreview
import com.wannagohome.lens_review_android.support.baseclass.BaseViewModel
import com.wannagohome.lens_review_android.extension.addTo
import org.koin.core.inject
import timber.log.Timber


class ReviewPreviewViewModel : BaseViewModel(){

    private val lensApiClient : LensApiClient by inject()

    val reviewPreviewList = MutableLiveData<List<ReviewPreview>>()

    fun fetchReviewPreview(){
        lensApiClient.getAllReviews()
            .subscribe{
                val body = it.body()

                body?.let{
                    reviewPreviewList.value = it
                }
            }.addTo(compositeDisposable)
    }

}