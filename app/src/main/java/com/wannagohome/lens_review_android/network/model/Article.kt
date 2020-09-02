package com.wannagohome.lens_review_android.network.model

import com.google.gson.annotations.SerializedName

data class Article(
    @SerializedName("articleId")
    val articleId: Int = 0,

    @SerializedName("authorId")
    val authorId: Int = 0,

    @SerializedName("title")
    val title: String = "",

    @SerializedName("contentText")
    val contentText: String = "",

//    @SerializedName("contentImage")
//    val contentImage: List<String> = listOf(""),
//
//    @SerializedName("contentLink")
//    val contentLink: List<String> = listOf(""),

    @SerializedName("views")
    val views: Int = 0,

    @SerializedName("likes")
    val likes: Int = 0,

    @SerializedName("comments")
    val comments: Int = 0,

    @SerializedName("createdAt")
    val createdAt:  String = "",

    @SerializedName("uri")
    val uri: String = ""

)