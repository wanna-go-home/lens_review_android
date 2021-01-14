package com.wannagohome.lens_review_android.network.model.comment

import com.google.gson.annotations.SerializedName

class WriteCommentRequest(
    @SerializedName("bundleId")
    val bundleId: Int?=null,
    @SerializedName("content")
    val contents: String
)