package com.wannagohome.viewty.network.model.review

import com.google.gson.annotations.SerializedName
import com.wannagohome.viewty.network.model.LensPreview

data class Review(
    @SerializedName("account")
    val account: String,

    @SerializedName("content")
    val content: String,

    @SerializedName("isAuthor")
    val isAuthor: Boolean = false,

    @SerializedName("createdAt")
    val createdAt: String,

    @SerializedName("id")
    val id: Int,

    @SerializedName("lensId")
    val lensId: Int,

    @SerializedName("isLiked")
    val isLiked: Boolean = false,

    @SerializedName("likeCnt")
    val likeCnt: Int,

    @SerializedName("nickname")
    val nickname: String,

    @SerializedName("replyCnt")
    val replyCnt: Int,

    @SerializedName("title")
    val title: String,

    @SerializedName("viewCnt")
    val viewCnt: Int,

    @SerializedName("lensPreviewEntity")
    val lens: LensPreview
)