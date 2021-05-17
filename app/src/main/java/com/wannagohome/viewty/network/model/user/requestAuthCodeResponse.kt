package com.wannagohome.viewty.network.model.user

import com.google.gson.annotations.SerializedName


//{
//    "available": true,
//    "check_code": 0
//}
data class requestAuthCodeResponse(

    @SerializedName("pin")
    val pin: String,

    @SerializedName("requestId")
    val requestId: Int

)