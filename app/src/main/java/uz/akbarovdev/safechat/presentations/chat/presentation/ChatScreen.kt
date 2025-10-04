package uz.akbarovdev.safechat.presentations.chat.presentation

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send

import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import uz.akbarovdev.safechat.presentations.chat.domain.models.Message
import uz.akbarovdev.safechat.presentations.chat.presentation.components.MessageBubble
import uz.akbarovdev.safechat.presentations.chat.presentation.view_model.ChatAction
import uz.akbarovdev.safechat.presentations.chat.presentation.view_model.ChatState
import uz.akbarovdev.safechat.presentations.chat.presentation.view_model.ChatViewModel
import uz.akbarovdev.safechat.ui.theme.SafeChatTheme

@Composable
fun ChatRoot(
    viewModel: ChatViewModel = viewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    ChatScreen(
        state = state,
        onAction = viewModel::onAction
    )
}

@SuppressLint("ConfigurationScreenWidthHeight")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(
    state: ChatState,
    onAction: (ChatAction) -> Unit,
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text("Akhmadjon")
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            LazyColumn(
                Modifier.fillMaxWidth()
            ) {
                items(state.messages) { message ->
                    MessageBubble(message, 1)
                }
            }
            TextField(
                value = "", onValueChange = {},
                modifier = Modifier.fillMaxWidth(), suffix = {
                    Icon(
                        Icons.AutoMirrored.Filled.Send,
                        contentDescription = "Send"
                    )
                }
            )
        }
    }

}

@Preview
@Composable
private fun Preview() {
    val messages: List<Message> = listOf(
        Message(message = "Hey! How are you?", senderId = 1),
        Message(message = "I’m good, thanks! You?", senderId = 2),
        Message(message = "Doing great! Working on the new app.", senderId = 1),
        Message(message = "Nice! Is it the chat project?", senderId = 2),
        Message(message = "Yep, trying WebSocket with Ktor now.", senderId = 1),
        Message(message = "Cool! Send me the repo link when done.", senderId = 2),
        Message(message = "Sure thing 🔥", senderId = 1),
    )

    SafeChatTheme {
        ChatScreen(
            state = ChatState(
                messages = messages
            ),
            onAction = {}
        )
    }
}