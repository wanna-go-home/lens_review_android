package com.wannagohome.lens_review_android.network.model

import com.google.gson.annotations.SerializedName

data class DetailedArticle(
    @SerializedName("id")
    val articleId: Int = 0,

    @SerializedName("account")
    val author: String = "",

    @SerializedName("nickname")
    val nickName: String = "",

    @SerializedName("title")
    val title: String = "",

    @SerializedName("content")
    val content: String = "",

    @SerializedName("viewCnt")
    val views: Int = 0,

    @SerializedName("likeCnt")
    val likes: Int = 0,

    @SerializedName("replyCnt")
    val comments: Int = 0,

    @SerializedName("created_at")
    val createdAt:  String = "",

    @SerializedName("uri")
    val uri:  String = ""
)