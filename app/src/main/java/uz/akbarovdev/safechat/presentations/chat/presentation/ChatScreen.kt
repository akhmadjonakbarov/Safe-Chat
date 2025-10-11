package uz.akbarovdev.safechat.presentations.chat.presentation

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.compose.viewmodel.koinViewModel
import uz.akbarovdev.safechat.presentations.chat.domain.models.Message
import uz.akbarovdev.safechat.presentations.chat.presentation.components.MessageBubble
import uz.akbarovdev.safechat.presentations.chat.presentation.view_model.ChatAction
import uz.akbarovdev.safechat.presentations.chat.presentation.view_model.ChatState
import uz.akbarovdev.safechat.presentations.chat.presentation.view_model.ChatViewModel
import uz.akbarovdev.safechat.core.design_system.ui.theme.SafeChatTheme
import uz.akbarovdev.safechat.presentations.chat.presentation.components.ChatInputField
import uz.akbarovdev.safechat.presentations.home.domain.models.ChatRoomUser

@Composable
fun ChatRoot(
    navController: NavController,
    viewModel: ChatViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    ChatScreen(
        state = state,
        navController = navController,
        onAction = viewModel::onAction
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(
    state: ChatState,
    navController: NavController,
    onAction: (ChatAction) -> Unit,
) {
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    // Auto-scroll when new message added
    LaunchedEffect(state.messages.size) {
        if (state.messages.isNotEmpty()) {
            coroutineScope.launch {
                listState.scrollToItem(0)
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = navController::navigateUp) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            Modifier
                                .size(45.dp)
                                .clip(CircleShape)
                                .background(Color.Red)
                        )
                        state.receiver?.let {
                            Text(
                                modifier = Modifier.padding(start = 8.dp),
                                text = it.email,
                                style = MaterialTheme.typography.titleMedium.copy(fontSize = 18.sp)
                            )
                        }
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .safeDrawingPadding(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Messages
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                state = listState,
                reverseLayout = true,
                contentPadding = PaddingValues(vertical = 12.dp, horizontal = 8.dp)
            ) {
                items(state.messages.reversed()) { message ->
                    state.receiver?.let {
                        Log.d("MessageBubble", "${message.fileUrl}")
                        MessageBubble(message, it.id)
                    }
                }
            }

            // Input Field
            ChatInputField(
                value = state.messageText,
                messageType = state.messageType,
                onValueChange = { onAction(ChatAction.OnInputMessage(it)) },
                onClickSelectFile = { file ->
                    onAction(ChatAction.OnSelectFile(file))
                },
                isFileSelected = state.selectFile != null,
                onSendMessage = {
                    onAction(ChatAction.OnSendMessage)
                    coroutineScope.launch {
                        delay(100)
                        listState.scrollToItem(0)
                    }
                }, onSendFile = {
                    onAction(ChatAction.OnSendFile)
                }
            )
        }
    }
}


@Preview(
    showSystemUi = true
)
@Composable
private fun Preview() {
    val dummyMessages = listOf(
        Message(
            "Hey! How are you?", 1, 1,
            type = "text",
            isRead = false, fileUrl = ""
        ),
        Message(
            "Check this out!",
            1,
            1,
            "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQ4OgBuC_EJ401qKWo9ZK9vhouZ6Xw5qQqSuw&s",
            "image",
            false
        ),
    )


    SafeChatTheme {
        ChatScreen(
            state = ChatState(
                messages = dummyMessages,
                receiver = ChatRoomUser(
                    1, "akhmad@gmail.com"
                )
            ),
            onAction = {},
            navController = rememberNavController()
        )
    }
}