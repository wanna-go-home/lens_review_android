package com.wannagohome.lens_review_android.network.model.review

import android.os.Build
import com.google.gson.annotations.SerializedName
import com.wannagohome.lens_review_android.network.model.LensPreview
import java.time.ZoneId
import java.time.ZonedDateTime

data class ReviewPreview(
    @SerializedName("account")
    val account: String,

    @SerializedName("content")
    val content: String,

    @SerializedName("createdAt")
    val createdAt: String,

    @SerializedName("id")
    val id: Int,

    @SerializedName("lensId")
    val lensId: Int,

    @SerializedName("likeCnt")
    val likeCnt: Int,

    @SerializedName("nickname")
    val nickname: String,

    @SerializedName("replyCnt")
    val replyCnt: Int,

    @SerializedName("title")
    val title: String,

    @SerializedName("viewCnt")
    val viewCnt: Int,

    @SerializedName("lensEntity")
    val lens: LensPreview


) {

    fun getDateTime(): String {


        return if (Build.VERSION_CODES.O <= Build.VERSION.SDK_INT) {
            val dateTime = ZonedDateTime.parse(createdAt)
                .withZoneSameInstant(ZoneId.systemDefault())

            val month = dateTime.monthValue
            val date = dateTime.dayOfMonth
            val hours = dateTime.hour
            val minutes = dateTime.minute

            "${month}/$date-$hours:$minutes"
        } else {
            val dateTime = org.threeten.bp.ZonedDateTime.parse(createdAt)
                .withZoneSameInstant(org.threeten.bp.ZoneId.systemDefault())

            val month = dateTime.monthValue
            val date = dateTime.dayOfMonth
            val hours = dateTime.hour
            val minutes = dateTime.minute

            "${month}/$date-$hours:$minutes"
        }
    }
}