package com.wannagohome.lens_review_android.network.model.review

import com.google.gson.annotations.SerializedName

class WriteReviewRequest(
    @SerializedName("title")
    val title: String,

    @SerializedName("content")
    val contents: String,

    @SerializedName("lensId")
    val lensId: Int
)