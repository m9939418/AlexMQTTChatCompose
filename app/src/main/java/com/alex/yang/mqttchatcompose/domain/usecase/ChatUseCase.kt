package com.alex.yang.mqttchatcompose.domain.usecase

import com.alex.yang.mqttchatcompose.domain.model.ChatMessage
import com.alex.yang.mqttchatcompose.domain.repository.ChatRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Created by AlexYang on 2026/1/7.
 *
 *
 */
class ConnectMqttUseCase @Inject constructor(private val repository: ChatRepository) {
    suspend operator fun invoke() = repository.connect()
}

class ObserveMessagesUseCase @Inject constructor(private val repository: ChatRepository) {
    operator fun invoke(): Flow<ChatMessage> = repository.observeMessages()
}

class PublishMessageUseCase @Inject constructor(private val repository: ChatRepository) {
    suspend operator fun invoke(message: String) = repository.publish(message)
}