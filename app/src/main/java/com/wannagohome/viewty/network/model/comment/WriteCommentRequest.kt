package com.wannagohome.viewty.network.model.comment

import com.google.gson.annotations.SerializedName

class WriteCommentRequest(
    @SerializedName("content")
    val contents: String,

    @SerializedName("bundleId")
    val bundleId: Int?=null
)