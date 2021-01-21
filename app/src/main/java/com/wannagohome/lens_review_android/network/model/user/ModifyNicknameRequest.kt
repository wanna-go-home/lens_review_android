package com.wannagohome.lens_review_android.network.model.user

import com.google.gson.annotations.SerializedName

data class ModifyNicknameRequest(
    @SerializedName("nickName")
    val nickname: String

)