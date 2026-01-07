package com.alex.yang.mqttchatcompose.utils

import com.alex.yang.mqttchatcompose.domain.model.ChatMessage
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * Created by AlexYang on 2026/1/7.
 *
 *
 */
val ChatMessage.formatTimeAgo: String
    get() {
        val diff = Date().time - timestamp
        val minutes = diff / 60_000
        val hours = diff / 3_600_000

        return when {
            minutes < 1 -> "剛剛"
            minutes < 60 -> "$minutes 分鐘前"
            hours < 24 -> "$hours 小時前"
            else -> {
                val sdf = SimpleDateFormat("yyyy/MM/dd", Locale.getDefault())
                sdf.format(Date(timestamp))
            }
        }
    }