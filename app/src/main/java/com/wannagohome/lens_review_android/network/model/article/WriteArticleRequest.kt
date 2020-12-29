package com.wannagohome.lens_review_android.network.model.article

import com.google.gson.annotations.SerializedName

class WriteArticleRequest(
    @SerializedName("title")
    val title: String,

    @SerializedName("content")
    val contents: String
)