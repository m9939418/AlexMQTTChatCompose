package com.alex.yang.mqttchatcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.alex.yang.mqttchatcompose.presentation.ChatScreen
import com.alex.yang.mqttchatcompose.presentation.ChatViewModel
import com.alex.yang.mqttchatcompose.ui.theme.AlexMQTTChatComposeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AlexMQTTChatComposeTheme {
                val viewModel = hiltViewModel<ChatViewModel>()
                val state by viewModel.uiState.collectAsStateWithLifecycle()

                ChatScreen(
                    state = state,
                    onSendClick = viewModel::sendMessage
                )
            }
        }
    }
}