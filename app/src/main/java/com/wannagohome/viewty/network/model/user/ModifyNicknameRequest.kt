package com.wannagohome.viewty.network.model.user

import com.google.gson.annotations.SerializedName

data class ModifyNicknameRequest(
    @SerializedName("nickName")
    val nickname: String

)