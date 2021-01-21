package com.wannagohome.lens_review_android.network.model.review

import android.os.Build
import com.google.gson.annotations.SerializedName
import com.wannagohome.lens_review_android.network.model.LensPreview
import java.time.ZoneId
import java.time.ZonedDateTime

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
    val title: String,

    @SerializedName("viewCnt")
    val viewCnt: Int,

    @SerializedName("lensPreviewEntity")
    val lens: LensPreview


)