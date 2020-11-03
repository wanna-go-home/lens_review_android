package com.wannagohome.lens_review_android.network.model

import com.google.gson.annotations.SerializedName

data class ReviewPreview(
    @SerializedName("account")
    val account: String,

    @SerializedName("content")
    val content: String,

    @SerializedName("createdAt")
    val createdAt: String,

    @SerializedName("id")
    val id: Int,

    @SerializedName("lensId")
    val lensId: Int,

    @SerializedName("likeCnt")
    val likeCnt: Int,

    @SerializedName("nickname")
    val nickname: String,

    @SerializedName("replyCnt")
    val replyCnt: Int,

    @SerializedName("title")
    val title: String

)