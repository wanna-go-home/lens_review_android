package com.wannagohome.lens_review_android.network.model.user

import com.google.gson.annotations.SerializedName

data class SignUpRequest(
    @SerializedName("account")
    val userId: String,

    @SerializedName("email")
    val userEmail: String,

    @SerializedName("accountPw")
    val userPw: String,

    @SerializedName("phoneNum")
    val phoneNumber: String,

    @SerializedName("nickname")
    val nickname: String
)