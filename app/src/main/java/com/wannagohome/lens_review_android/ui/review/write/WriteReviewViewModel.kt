package com.wannagohome.lens_review_android.ui.review.write

import androidx.lifecycle.MutableLiveData
import com.wannagohome.lens_review_android.network.lensapi.LensApiClient
import com.wannagohome.lens_review_android.support.basemodel.BaseViewModel
import com.wannagohome.lens_review_android.support.disposableExt.addTo
import org.koin.core.inject

class WriteReviewViewModel : BaseViewModel() {
    private val lensApiClient: LensApiClient by inject()

    val writeSuccess = MutableLiveData<Boolean>(false)

    fun writeReview(title: String, contents: String, lensId: Int) {
        lensApiClient.writeReview(title, contents, lensId)
            .subscribe {
                writeSuccess.value = true
            }
            .addTo(compositeDisposable)
    }

}