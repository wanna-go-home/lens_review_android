package com.wannagohome.viewty.network.model.user

import com.google.gson.annotations.SerializedName

data class MyInfo(
    @SerializedName("email")
    val email: String = "",

    @SerializedName("freeCommentCount")
    val articleCommentCount: Int,

    @SerializedName("freeCount")
    val articleCount: Int,

    @SerializedName("likeCount")
    val likeCount: Int,

    @SerializedName("nickName")
    val nickname: String,

    @SerializedName("reviewCommentCount")
    val reviewCommentCount: Int,

    @SerializedName("reviewCount")
    val reviewCount: Int
)