package com.alex.yang.mqttchatcompose.domain.repository

import com.alex.yang.mqttchatcompose.domain.model.ChatMessage
import kotlinx.coroutines.flow.Flow

/**
 * Created by AlexYang on 2026/1/7.
 *
 *
 */
interface ChatRepository {
    suspend fun connect()
    fun observeMessages(): Flow<ChatMessage>
    suspend fun publish(message: String)
    suspend fun disconnect()
}