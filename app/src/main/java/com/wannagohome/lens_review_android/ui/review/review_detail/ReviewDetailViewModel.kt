package com.wannagohome.lens_review_android.ui.review.review_detail

import com.wannagohome.lens_review_android.network.lensapi.LensApiClient
import com.wannagohome.lens_review_android.support.basemodel.BaseViewModel
import org.koin.core.inject


class ReviewDetailViewModel : BaseViewModel() {

    private val lensApiClient: LensApiClient by inject()

    fun getReview(reviewId: Int) {

    }

}