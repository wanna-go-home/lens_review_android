package com.wannagohome.lens_review_android.network.model.article

import com.google.gson.annotations.SerializedName

data class ArticlePreview(
    @SerializedName("id")
    val articleId: Int = 0,

    @SerializedName("nickname")
    val author: String = "",

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

    @SerializedName("createdAt")
    val createdAt:  String = ""

)