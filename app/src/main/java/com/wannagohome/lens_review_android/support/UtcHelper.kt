package com.wannagohome.lens_review_android.support

import android.os.Build
import java.time.ZoneId
import java.time.ZonedDateTime

object UtcHelper {
    fun convertUTCtoDateString(createdAt: String): String {
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