package com.wannagohome.lens_review_android.network.model

import com.google.gson.annotations.SerializedName

data class Comment(
    @SerializedName("id")
    val commentId: Int = 0,

    @SerializedName("accountId")
    val authorId: String = "",

    @SerializedName("postId")
    val articleId: Int = 0,

    @SerializedName("content")
    val content: String = "",

    @SerializedName("likeCnt")
    val likes: Int = 0,

    @SerializedName("createdAt")
    val createdAt:  String = "",

    @SerializedName("depth")
    val depth: Int = 0,

    @SerializedName("bundleId")
    val bundleId: Int = 0

)