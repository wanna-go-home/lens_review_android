package com.wannagohome.lens_review_android.network.model

import android.os.Build
import com.google.gson.annotations.SerializedName
import java.time.Duration
import java.time.ZoneId
import java.time.ZonedDateTime
import java.util.*

data class Article(
    @SerializedName("id")
    val articleId: Int = 0,

    @SerializedName("account")
    val author: String = "",

    @SerializedName("title")
    val title: String = "",

    @SerializedName("content")
    val content: String = "",

    @SerializedName("viewCnt")
    val views: Int = 0,

    @SerializedName("likeCnt")
    val likes: Int = 0,

    @SerializedName("commentCnt")
    val comments: Int = 0,

    @SerializedName("createdAt")
    val createdAt:  String = ""

)

