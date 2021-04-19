package com.wannagohome.viewty.network.model.user

import com.google.gson.annotations.SerializedName

data class SignUpRequest(
    @SerializedName("pin")
    val pin: String,

    @SerializedName("requestId")
    val  requestId: Int
)