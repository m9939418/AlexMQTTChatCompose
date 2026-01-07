package com.alex.yang.mqttchatcompose.domain.model

/**
 * Created by AlexYang on 2026/1/7.
 *
 *
 */
data class ChatMessage(
    val text: String,
    val isMine: Boolean,
    val timestamp: Long = System.currentTimeMillis()
)