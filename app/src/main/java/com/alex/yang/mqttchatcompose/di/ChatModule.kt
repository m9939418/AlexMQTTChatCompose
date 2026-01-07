package com.alex.yang.mqttchatcompose.di

import com.alex.yang.mqttchatcompose.BuildConfig
import com.alex.yang.mqttchatcompose.data.core.MqttConfig
import com.alex.yang.mqttchatcompose.data.repository.ChatRepositoryImpl
import com.alex.yang.mqttchatcompose.domain.repository.ChatRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Created by AlexYang on 2026/1/7.
 *
 *
 */
@Module
@InstallIn(SingletonComponent::class)
object ChatModule {
    @Provides
    @Singleton
    fun provideMqttConfig(): MqttConfig =
        MqttConfig(
            serverUri = BuildConfig.SERVER_URI,
            myTopic = "demo/alex/chat/android",
            otherTopic = "demo/alex/chat/web",
            username = BuildConfig.USERNAME,
            password = BuildConfig.PASSWORD
        )

    @Provides
    @Singleton
    fun provideChatRepositoryImpl(config: MqttConfig): ChatRepository =
        ChatRepositoryImpl(config)
}