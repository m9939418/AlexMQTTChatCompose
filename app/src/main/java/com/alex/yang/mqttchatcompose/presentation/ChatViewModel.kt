package com.alex.yang.mqttchatcompose.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alex.yang.mqttchatcompose.TAG
import com.alex.yang.mqttchatcompose.domain.model.ChatMessage
import com.alex.yang.mqttchatcompose.domain.usecase.ConnectMqttUseCase
import com.alex.yang.mqttchatcompose.domain.usecase.ObserveMessagesUseCase
import com.alex.yang.mqttchatcompose.domain.usecase.PublishMessageUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by AlexYang on 2026/1/7.
 *
 *
 */
@HiltViewModel
class ChatViewModel @Inject constructor(
    private val connectMqttUseCase: ConnectMqttUseCase,
    private val observeMessagesUseCase: ObserveMessagesUseCase,
    private val publishMessageUseCase: PublishMessageUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            runCatching { connectMqttUseCase() }
                .onSuccess {
                    _uiState.update { it.copy(isConnected = true) }
                }.onFailure { e ->
                    _uiState.update { it.copy(error = e.message ?: "connect failed") }
                }

            observeMessagesUseCase().collect { message ->
                Log.d(TAG, "[DEBUG] New message received: $message")
                _uiState.update { it.copy(messages = it.messages + message) }
            }
        }
    }

    fun sendMessage(text: String) {
        if (text.isBlank()) return

        viewModelScope.launch {
            runCatching { publishMessageUseCase(text) }
                .onFailure { e ->
                    _uiState.update { it.copy(error = e.message ?: "send message failed") }
                }
        }
    }

    data class UiState(
        val isConnected: Boolean = false,
        val messages: List<ChatMessage> = emptyList(),
        val error: String? = null
    )
}