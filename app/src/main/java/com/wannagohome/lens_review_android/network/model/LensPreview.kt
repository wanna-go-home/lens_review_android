package com.wannagohome.lens_review_android.network.model

import com.google.gson.annotations.SerializedName

class LensPreview(
    @SerializedName("lens_id")
    val lensId: Int,

    @SerializedName("name")
    val name: String,

    @SerializedName("price")
    val price: Int,

    @SerializedName("productImage")
    val productImages: ArrayList<String>
)
