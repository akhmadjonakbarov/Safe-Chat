package uz.akbarovdev.safechat.presentations.home.presenation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.compose.viewmodel.koinViewModel
import uz.akbarovdev.safechat.core.design_system.ui.theme.SafeChatTheme
import uz.akbarovdev.safechat.presentations.home.domain.models.ChatRoom
import uz.akbarovdev.safechat.presentations.home.domain.models.ChatRoomDetails
import uz.akbarovdev.safechat.presentations.home.domain.models.ChatRoomUser
import uz.akbarovdev.safechat.presentations.home.presenation.components.ChatItem
import uz.akbarovdev.safechat.presentations.home.presenation.components.UserItem
import uz.akbarovdev.safechat.presentations.home.presenation.view_model.HomeAction
import uz.akbarovdev.safechat.presentations.home.presenation.view_model.HomeState
import uz.akbarovdev.safechat.presentations.home.presenation.view_model.HomeViewModel

@Composable
fun HomeRoot(
    onNavigateToChat: (chatRoomDetails: ChatRoomDetails) -> Unit,
    viewModel: HomeViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    HomeScreen(
        state = state, onNavigateToChat = onNavigateToChat, onAction = viewModel::onAction
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    state: HomeState,
    onNavigateToChat: (chatRoomDetails: ChatRoomDetails) -> Unit,
    onAction: (HomeAction) -> Unit,
) {

    LaunchedEffect(Unit) {
        onAction(HomeAction.OnLoadChatRooms)
    }


    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Home",
                        color = MaterialTheme.colorScheme.onPrimary,
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
                    )
                },
                actions = {
                    IconButton(onClick = {}) {
                        Icon(
                            imageVector = Icons.Outlined.Notifications,
                            contentDescription = "Notifications",
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            )
        }
    ) { innerPadding ->
        Box(
            Modifier
                .background(MaterialTheme.colorScheme.primary)
                .padding(innerPadding)
                .fillMaxSize(),
            contentAlignment = Alignment.BottomCenter
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .clip(RoundedCornerShape(topStart = 50.dp, topEnd = 50.dp))
                    .background(MaterialTheme.colorScheme.onPrimary)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 20.dp, vertical = 16.dp)
                ) {
                    OutlinedTextField(
                        value = state.query,
                        onValueChange = {
                            onAction(HomeAction.OnSearchUsers(it))
                        },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = "Search",
                                tint = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        },
                        placeholder = {
                            Text(
                                text = "Search chats...",
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        },
                        shape = RoundedCornerShape(25.dp),
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = MaterialTheme.colorScheme.primary,
                            unfocusedBorderColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(
                                alpha = 0.3f
                            ),
                            cursorColor = MaterialTheme.colorScheme.primary
                        )
                    )
                    Spacer(modifier = Modifier.height(20.dp))

                    if (state.chatRooms.isNotEmpty() && state.users.isEmpty()) {
                        LazyColumn {
                            items(state.chatRooms) { chatRoom ->
                                ChatItem(
                                    Modifier.padding(vertical = 10.dp),
                                    chatRoom
                                ) {
                                    onNavigateToChat(
                                        ChatRoomDetails(
                                            roomId = chatRoom.id,
                                            receiverId = chatRoom.receiver.id,
                                            receiverName = chatRoom.receiver.email,

                                        )
                                    )
                                }
                            }
                        }
                    } else {
                        LazyColumn {
                            items(state.users) { user ->
                                UserItem(
                                    Modifier.padding(vertical = 10.dp),
                                    user
                                ) {
                                    onAction(HomeAction.OnCreateChatRoom(user.id))
                                    if (state.createdChatRoomId != -1) {
                                        onNavigateToChat(
                                            ChatRoomDetails(
                                                roomId = state.createdChatRoomId,
                                                receiverId = user.id,
                                                receiverName = user.email,

                                            )
                                        )
                                    }
                                }
                            }
                        }
                    }


                }
            }
        }
    }
}


@Preview
@Composable
private fun PreviewHome() {
    SafeChatTheme {
        HomeScreen(
            state = HomeState(
                chatRooms = mutableListOf(
                    ChatRoom(
                        1, "Salom qalesiz?", 0, ChatRoomUser(
                            1,
                            "akhmad@gmail.com", "admin"
                        )
                    )
                )
            ), onNavigateToChat = {}, onAction = {})
    }
}