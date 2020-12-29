package com.wannagohome.lens_review_android.network.model.article

import com.google.gson.annotations.SerializedName

class WriteCommentRequest(
    @SerializedName("postId")
    val articleId: Int,
    @SerializedName("bundleId")
    val bundleId: Int,
    @SerializedName("content")
    val contents: String
)