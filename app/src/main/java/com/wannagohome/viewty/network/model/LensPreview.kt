package com.wannagohome.viewty.network.model

import com.google.gson.annotations.SerializedName

class LensPreview(
    @SerializedName("lensId")
    val lensId: Int,

    @SerializedName("name")
    val name: String,

    @SerializedName("price")
    val price: Int,

    @SerializedName("productImage")
    val productImages: ArrayList<String>,

    var selected: Boolean = false
) {
    fun clone(): LensPreview = LensPreview(lensId, name, price, productImages,selected)
}

