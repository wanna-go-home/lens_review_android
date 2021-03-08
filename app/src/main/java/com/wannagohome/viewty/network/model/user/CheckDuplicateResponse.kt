package com.wannagohome.viewty.network.model.user

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