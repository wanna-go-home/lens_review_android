package com.wannagohome.lens_review_android.network.model

import com.google.gson.annotations.SerializedName

data class Lens(
    @SerializedName("lensId")
    val lensId: Int = 0,

    @SerializedName("name")
    val name: String = "",

    @SerializedName("graphicDia")
    val graphicDia: Float,

    @SerializedName("perPackage")
    val perPackage: Int,

    @SerializedName("price")
    val price: Int,

    @SerializedName("reviewCnt")
    val reviewCnt: Int,

    @SerializedName("bc")
    val bc: Float,

    @SerializedName("dia")
    val dia: Float,

    @SerializedName("url")
    val url: String,

    @SerializedName("per")
    val per: String
)