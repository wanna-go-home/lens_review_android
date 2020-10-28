package com.wannagohome.lens_review_android.network.model

import com.google.gson.annotations.SerializedName

data class ChildComment(
    @SerializedName("id")
    val commentId: Int = 0,

    @SerializedName("account")
    val authorId: Int = 0,

    @SerializedName("articleId")
    val articleId: Int = 0,

    @SerializedName("parentId")
    val parentCommentId: Int = 0,

    @SerializedName("content")
    val content: String = "",

    @SerializedName("likeCnt")
    val likes: Int = 0,

    @SerializedName("createdAt")
    val createdAt:  String = ""

)