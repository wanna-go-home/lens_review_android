package com.wannagohome.viewty.ui.mypage.myreview

import androidx.lifecycle.MutableLiveData
import com.wannagohome.viewty.R
import com.wannagohome.viewty.extension.addTo
import com.wannagohome.viewty.network.lensapi.LensApiClient
import com.wannagohome.viewty.network.model.review.Review
import com.wannagohome.viewty.support.Utils
import com.wannagohome.viewty.support.baseclass.BaseViewModel
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