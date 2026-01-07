package com.alex.yang.mqttchatcompose.data.core

/**
 * Created by AlexYang on 2026/1/7.
 *
 *
 */
data class MqttConfig(
    val serverUri: String,
    val myTopic: String,
    val otherTopic: String,
    val username: String,
    val password: String
)
