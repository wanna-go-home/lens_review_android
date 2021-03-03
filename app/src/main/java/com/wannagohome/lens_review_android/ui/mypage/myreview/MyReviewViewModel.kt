package com.wannagohome.lens_review_android.ui.mypage.myreview

import androidx.lifecycle.MutableLiveData
import com.wannagohome.lens_review_android.R
import com.wannagohome.lens_review_android.extension.addTo
import com.wannagohome.lens_review_android.network.lensapi.LensApiClient
import com.wannagohome.lens_review_android.network.model.review.Review
import com.wannagohome.lens_review_android.support.Utils
import com.wannagohome.lens_review_android.support.baseclass.BaseViewModel
import org.koin.core.inject

class MyReviewViewModel : BaseViewModel() {
    private val lensApiClient: LensApiClient by inject()

    val reviewPreviewList = MutableLiveData<List<Review>>()

    val errorMessage = MutableLiveData<String>()

    fun fetchMyReviewPreview() {
        lensApiClient.getMyReview()
            .subscribe({
                reviewPreviewList.value = it.body()
            }, {
                errorMessage.value = Utils.getString(R.string.server_error_message)
            }).addTo(compositeDisposable)
    }

}