package com.wannagohome.lens_review_android.network.model.user

import com.google.gson.annotations.SerializedName


//{
//    "available": true,
//    "check_code": 0
//}
data class CheckDuplicateResponse(
    @SerializedName("available")
    val available: Boolean,

    @SerializedName("check_code")
    val checkCode: Int
)