package com.wannagohome.lens_review_android.network.model

import com.google.gson.annotations.SerializedName

data class ReviewPreview(
    @SerializedName("id")
    val id: Int,

    @SerializedName("title")
    val title: String,

    @SerializedName("content")
    val content: String,

    @SerializedName("userId")
    val userId: Int
)