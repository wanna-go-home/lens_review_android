package com.wannagohome.lens_review_android.ui.review

import androidx.lifecycle.MutableLiveData
import com.wannagohome.lens_review_android.network.lensapi.LensApiClient
import com.wannagohome.lens_review_android.network.model.ReviewPreview
import com.wannagohome.lens_review_android.support.basemodel.BaseViewModel
import org.koin.core.inject


class ReviewPreviewViewModel : BaseViewModel(){
    val lensApiClient : LensApiClient by inject()

    val reviewPreviewList = MutableLiveData<List<ReviewPreview>>()

    fun fetchReviewPreview(){
        val l = mutableListOf(ReviewPreview(1,"1","content 1",2))
        l.add(ReviewPreview(2,"2","content 2",3))

        reviewPreviewList.value = l
    }

}