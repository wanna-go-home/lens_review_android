package com.wannagohome.lens_review_android.network.model.user

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


//{
//    "available": true,
//    "check_code": 0
//}
data class CheckDuplicateResponse(

    @SerializedName("checkCode")
    val checkCode: Int,

    @SerializedName("available")
    val available: Boolean

)