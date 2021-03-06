package com.wannagohome.lens_review_android.network.model.user

import com.google.gson.annotations.SerializedName

data class SignUpRequest(
    @SerializedName("accountEmail")
    val userId: String,

    @SerializedName("accountPw")
    val userPw: String,

    @SerializedName("phoneNum")
    val phoneNumber: String,

    @SerializedName("nickname")
    val nickname: String
)