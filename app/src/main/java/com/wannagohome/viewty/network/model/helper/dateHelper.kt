package com.wannagohome.viewty.network.model.helper

import android.os.Build
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime

class dateHelper {
    companion object {
        fun calcCreatedBefore(createdAt : String): String {
            return if (Build.VERSION_CODES.O <= Build.VERSION.SDK_INT) {
                val dateTime = ZonedDateTime.parse(createdAt)
                    .withZoneSameInstant(ZoneId.systemDefault())
                val now = LocalDateTime.now()

                var monthDiff = now.monthValue - dateTime.monthValue
                val dateDiff = now.dayOfMonth - dateTime.dayOfMonth
                val hoursDiff = now.hour - dateTime.hour
                val minutesDiff = now.minute - dateTime.minute
                if (monthDiff != 0){
                    if (monthDiff < 0) {
                        monthDiff += 12
                    }
                    "$monthDiff 개월 전"
                }
                else if (dateDiff !=0){
                    "$dateDiff 일 전"
                }
                else if (hoursDiff !=0){
                    "$hoursDiff 시간 전"
                }
                else {
                    "$minutesDiff 분 전"
                }

            } else {
                val dateTime = org.threeten.bp.ZonedDateTime.parse(createdAt)
                    .withZoneSameInstant(org.threeten.bp.ZoneId.systemDefault())
                val now = org.threeten.bp.LocalDateTime.now()

                var monthDiff = now.monthValue - dateTime.monthValue
                val dateDiff = now.dayOfMonth - dateTime.dayOfMonth
                val hoursDiff = now.hour - dateTime.hour
                val minutesDiff = now.minute - dateTime.minute
                if (monthDiff != 0){
                    if (monthDiff < 0) {
                        monthDiff += 12
                    }
                    "$monthDiff 개월 전"
                }
                else if (dateDiff !=0){
                    "$dateDiff 일 전"
                }
                else if (hoursDiff !=0){
                    "$hoursDiff 시간 전"
                }
                else {
                    "$minutesDiff 분 전"
                }
            }
        }
    }
}