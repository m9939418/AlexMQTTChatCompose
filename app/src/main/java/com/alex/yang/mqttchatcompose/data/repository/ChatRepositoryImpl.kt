package com.alex.yang.mqttchatcompose.data.repository

import com.alex.yang.mqttchatcompose.data.core.MqttConfig
import com.alex.yang.mqttchatcompose.domain.model.ChatMessage
import com.alex.yang.mqttchatcompose.domain.repository.ChatRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.withContext
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken
import org.eclipse.paho.client.mqttv3.MqttCallback
import org.eclipse.paho.client.mqttv3.MqttClient
import org.eclipse.paho.client.mqttv3.MqttConnectOptions
import org.eclipse.paho.client.mqttv3.MqttMessage
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by AlexYang on 2026/1/7.
 *
 *
 */
@Singleton
class ChatRepositoryImpl @Inject constructor(
    private val config: MqttConfig
) : ChatRepository {
    private val client = MqttClient(
        config.serverUri,
        "android-${System.currentTimeMillis()}",
        MemoryPersistence()
    )

    override suspend fun connect() = withContext(Dispatchers.IO) {
        if (client.isConnected) return@withContext

        val options = MqttConnectOptions().apply {
            userName = config.username
            password = config.password.toCharArray()
            isAutomaticReconnect = true
            isCleanSession = true
        }

        client.connect(options)
        client.subscribe(config.myTopic, 0)
        client.subscribe(config.otherTopic, 0)
    }

    override fun observeMessages(): Flow<ChatMessage> = callbackFlow {
        client.setCallback(
            object : MqttCallback {
                override fun messageArrived(topic: String?, message: MqttMessage?) {
                    if (topic == null || message == null) return

                    val isMine = topic == config.myTopic

                    trySend(
                        ChatMessage(
                            text = message.toString(),
                            isMine = isMine
                        )
                    )
                }

                override fun connectionLost(cause: Throwable?) {}
                override fun deliveryComplete(token: IMqttDeliveryToken?) {}
            }
        )

        awaitClose { client.setCallback(null) }
    }

    override suspend fun publish(message: String) = withContext(Dispatchers.IO) {
        if (!client.isConnected) error("MQTT not connected")

        val message = MqttMessage(message.toByteArray()).apply {
            qos = 0
            isRetained = false
        }

        client.publish(config.myTopic, message)
    }

    override suspend fun disconnect() {
        client.disconnect()
    }
}