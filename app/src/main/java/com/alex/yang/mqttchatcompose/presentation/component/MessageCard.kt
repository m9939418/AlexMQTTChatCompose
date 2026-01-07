package com.alex.yang.mqttchatcompose.presentation.component

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alex.yang.mqttchatcompose.domain.model.ChatMessage
import com.alex.yang.mqttchatcompose.ui.theme.AlexMQTTChatComposeTheme
import com.alex.yang.mqttchatcompose.utils.formatTimeAgo

/**
 * Created by AlexYang on 2026/1/7.
 *
 *
 */
@Composable
fun MessageCard(
    modifier: Modifier = Modifier,
    message: ChatMessage
) {
    val bgColor = if (message.isMine) {
        MaterialTheme.colorScheme.primaryContainer
    } else {
        MaterialTheme.colorScheme.tertiaryContainer
    }

    val horizontalArrangement =
        if (message.isMine) Arrangement.End
        else Arrangement.Start

    val shape = RoundedCornerShape(
        topStart = 16.dp,
        topEnd = 16.dp,
        bottomEnd = if (message.isMine) 0.dp else 16.dp,
        bottomStart = if (message.isMine) 16.dp else 0.dp
    )

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = horizontalArrangement
    ) {
        Box(
            modifier = Modifier
                .background(color = bgColor, shape = shape)
                .padding(10.dp)
        ) {
            Column(Modifier.padding(8.dp)) {
                Text(
                    style = MaterialTheme.typography.bodyLarge,
                    text = message.text
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    style = MaterialTheme.typography.labelSmall,
                    color = Color.Gray,
                    text = message.formatTimeAgo
                )
            }
        }
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
fun MessageCardPreview() {
    AlexMQTTChatComposeTheme {
        MessageCard(
            message = ChatMessage(
                text = "Hello, this is a sample message to demonstrate the MessageCard composable in Jetpack Compose.",
                isMine = false
            )
        )
    }
}