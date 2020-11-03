package com.wannagohome.lens_review_android.ui.review

import androidx.lifecycle.MutableLiveData
import com.wannagohome.lens_review_android.network.lensapi.LensApiClient
import com.wannagohome.lens_review_android.network.model.ReviewPreview
import com.wannagohome.lens_review_android.support.basemodel.BaseViewModel
import com.wannagohome.lens_review_android.support.disposableExt.addTo
import org.koin.core.inject


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