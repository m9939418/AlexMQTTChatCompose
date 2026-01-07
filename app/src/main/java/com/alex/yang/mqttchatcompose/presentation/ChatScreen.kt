package com.alex.yang.mqttchatcompose.presentation

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alex.yang.mqttchatcompose.domain.model.ChatMessage
import com.alex.yang.mqttchatcompose.presentation.component.MessageCard
import com.alex.yang.mqttchatcompose.presentation.component.SendMessage
import com.alex.yang.mqttchatcompose.ui.theme.AlexMQTTChatComposeTheme

/**
 * Created by AlexYang on 2026/1/7.
 *
 *
 */
@Composable
fun ChatScreen(
    modifier: Modifier = Modifier,
    state: ChatViewModel.UiState,
    onSendClick: (String) -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Spacer(modifier = Modifier.height(40.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            val dot = if (state.isConnected) Color(0xFF4CAF50) else Color(0xFFE53935)

            Box(
                modifier = Modifier
                    .size(12.dp)
                    .background(dot, CircleShape)
            )

            Spacer(modifier = Modifier.width(8.dp))

            Text(text = "MQTT State: ${if (state.isConnected) "Connected" else "Disconnected"}")
        }

        state.error?.let { error ->
            Spacer(modifier = Modifier.height(8.dp))

            Text(
                style = MaterialTheme.typography.bodySmall,
                color = Color(0xFFE53935),
                text = "Error: $error"
            )
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 16.dp)
                .weight(1f),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(state.messages.size) { index ->
                MessageCard(message = state.messages[index])
            }
        }

        SendMessage(onSendClick)

        Spacer(modifier = Modifier.height(40.dp))
    }
}

@Preview(
    showBackground = true,
    showSystemUi = false,
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    name = "Light Mode"
)
@Preview(
    showBackground = true,
    showSystemUi = false,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    name = "Dark Mode"
)
@Composable
fun ChatScreenPreview() {
    AlexMQTTChatComposeTheme {
        ChatScreen(
            state = ChatViewModel.UiState(
                isConnected = true,
                messages = listOf(
                    ChatMessage(
                        text = "Hello!",
                        isMine = false,
                        timestamp = System.currentTimeMillis() - 600000
                    ),
                    ChatMessage(
                        text = "Hi there!",
                        isMine = true,
                        timestamp = System.currentTimeMillis() - 550000
                    ),
                    ChatMessage(
                        text = "How are you?",
                        isMine = false,
                        timestamp = System.currentTimeMillis() - 500000
                    ),
                    ChatMessage(
                        text = "I'm good, thanks!",
                        isMine = true,
                        timestamp = System.currentTimeMillis() - 450000
                    ),
                )
            )
        )
    }
}