package com.wannagohome.viewty.network.model

import com.google.gson.annotations.SerializedName

data class Lens(
    @SerializedName("lensId")
    val lensId: Int = 0,

    @SerializedName("name")
    val name: String = "",

    @SerializedName("graphicDia")
    val graphicDia: Float = 0f,

    @SerializedName("perPackage")
    val perPackage: Int = 0,

    @SerializedName("price")
    val price: Int = 0,

    @SerializedName("reviewCnt")
    val reviewCnt: Int = 0,

//    @SerializedName("bc")
//    val bc: Float = 0f,

//    @SerializedName("dia")
//    val dia: Float = 0f

//    @SerializedName("url")
//    val url: String,

//    @SerializedName("per")
//    val per: Float

    @SerializedName("productImage")
    val productImage: List<String> = listOf()
)