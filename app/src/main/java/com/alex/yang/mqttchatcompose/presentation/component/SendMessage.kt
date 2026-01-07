package com.alex.yang.mqttchatcompose.presentation.component

import android.content.res.Configuration
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.clearText
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alex.yang.mqttchatcompose.ui.theme.AlexMQTTChatComposeTheme

/**
 * Created by AlexYang on 2026/1/7.
 *
 *
 */
@Composable
fun SendMessage(
    onSendClick: (String) -> Unit = {}
) {
    val textState: TextFieldState = rememberTextFieldState()

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextField(
            modifier = Modifier.weight(1f),
            state = textState,
            placeholder = { Text("Type a message") },
        )

        Spacer(modifier = Modifier.width(8.dp))

        IconButton(
            enabled = textState.text.isNotBlank(),
            onClick = {
                onSendClick(textState.text.toString())
                textState.clearText()
            }
        ) {
            Icon(
                imageVector = Icons.Default.Send,
                contentDescription = null
            )
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
fun Preview() {
    AlexMQTTChatComposeTheme {
        SendMessage()
    }
}
