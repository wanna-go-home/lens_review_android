package com.wannagohome.lens_review_android.network.model.user

import com.google.gson.annotations.SerializedName

class LoginRequest(
    @SerializedName("account")
    val account: String,

    @SerializedName("pw")
    val pw: String
)

class LoginResponse(
    @SerializedName("Authorization")
    val token: String
)