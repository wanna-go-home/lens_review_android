package com.wannagohome.lens_review_android.network.model

import com.google.gson.annotations.SerializedName

data class Comment(
    @SerializedName("id")
    val commentId: Int = 0,

    @SerializedName("account")
    val authorId: Int = 0,

    @SerializedName("nickname")
    val nickName: String = "",

    @SerializedName("articleId")
    val articleId: Int = 0,

    @SerializedName("content")
    val content: String = "",

    @SerializedName("likeCnt")
    val likes: Int = 0,

    @SerializedName("createdAt")
    val createdAt:  String = ""
)