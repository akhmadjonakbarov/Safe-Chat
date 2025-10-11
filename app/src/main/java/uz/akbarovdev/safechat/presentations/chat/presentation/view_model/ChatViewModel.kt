package uz.akbarovdev.safechat.presentations.chat.presentation.view_model

import android.content.Context
import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import uz.akbarovdev.safechat.app.navigation.Routes
import uz.akbarovdev.safechat.core.enums.MessageType
import uz.akbarovdev.safechat.core.enums.PreferenceKeys
import uz.akbarovdev.safechat.core.extension.sharedPreferences
import uz.akbarovdev.safechat.core.wrapper.Handler
import uz.akbarovdev.safechat.presentations.auth.domain.models.User
import uz.akbarovdev.safechat.presentations.chat.domain.models.Message
import uz.akbarovdev.safechat.presentations.chat.domain.models.MessageCache
import uz.akbarovdev.safechat.presentations.chat.domain.models.MessageRequest
import uz.akbarovdev.safechat.presentations.chat.domain.repository.MessageRepository
import uz.akbarovdev.safechat.presentations.home.data.web_socket.ChatWebSocketClient
import uz.akbarovdev.safechat.presentations.home.domain.models.ChatRoomUser
import uz.akbarovdev.safechat.presentations.home.utils.toChatRoomDetails
import java.io.File

class ChatViewModel(
    private val applicationContext: Context,
    private val savedStateHandle: SavedStateHandle,
    private val chatWebSocketClient: ChatWebSocketClient,
    private val messageRepository: MessageRepository,
) : ViewModel() {

    private val route: Routes.Chat = savedStateHandle.toRoute()
    val chatRoomDetails = route.toChatRoomDetails()

    private val userJson: String by applicationContext.sharedPreferences(PreferenceKeys.User.name)
    private val currentUser = Json.decodeFromString<User>(userJson)

    private var hasLoadedInitialData = false

    private val _state = MutableStateFlow(ChatState())
    val state = _state
        .onStart {
            if (!hasLoadedInitialData) {

                hasLoadedInitialData = true
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = ChatState()
        )

    init {
        val receiver = ChatRoomUser(chatRoomDetails.receiverId, chatRoomDetails.receiverName)

        connectChatRoom(chatRoomDetails.roomId, receiver)
        loadMessages(chatRoomDetails.roomId)
    }

    fun onAction(action: ChatAction) {
        when (action) {
            is ChatAction.OnConnectChat -> {
                connectChatRoom(action.chatRoomId, action.receiver)
                loadMessages(action.chatRoomId)
            }

            is ChatAction.OnSendMessage -> {
                val messageText = state.value.messageText.trim()
                if (messageText.isNotEmpty()) {
                    sendMessage(messageText)
                    _state.update {
                        it.copy(
                            messageText = "",
                            messageType = MessageType.Image
                        )
                    }
                }
            }

            is ChatAction.OnInputMessage -> viewModelScope.launch {
                _state.update {
                    it.copy(
                        messageText = action.message,
                        messageType = if (action.message.isEmpty()) MessageType.Image else MessageType.Text
                    )
                }
            }

            ChatAction.OnLoadMessages -> loadMessages(chatRoomDetails.roomId)
            ChatAction.OnSendFile -> sendFile()
            is ChatAction.OnSelectFile -> selectFile(action.file)
        }
    }

    private fun selectFile(file: File) = viewModelScope.launch {
        _state.update { it.copy(selectFile = file) }
    }

    private fun sendFile() = viewModelScope.launch {
        val file = state.value.selectFile
        if (file != null) {
            sendMessage(file)
            _state.update {
                it.copy(
                    messageText = "",
                    messageType = MessageType.Text,
                    selectFile = null
                )
            }
        }
    }

    private fun connectChatRoom(roomId: Int, receiver: ChatRoomUser) {
        viewModelScope.launch {
            _state.update { it.copy(receiver = receiver) }

            chatWebSocketClient.connect(applicationContext, roomId)
            chatWebSocketClient.isConnected.collect { isConnected ->
                if (isConnected) {
                    listenMessages()
                    cancel()
                }
            }
        }

    }

    private fun listenMessages() = viewModelScope.launch {
        chatWebSocketClient.incomingMessages.collect { messageJson ->
            try {
                val messageCache =
                    Json.decodeFromString<MessageCache>(messageJson)
                val message = Message(
                    senderId = messageCache.senderId,
                    roomId = messageCache.roomId,
                    message = messageCache.message,
                    fileUrl = messageCache.fileUrl,
                    type = messageCache.type, isRead = messageCache.isRead
                )
                Log.d("ChatViewModel", "📩 Received message: $message")
                _state.update { current ->
                    if (!current.messages.contains(message))
                        current.copy(messages = current.messages + message)
                    else current
                }

            } catch (e: Exception) {
                Log.e(
                    "ChatViewModel",
                    "❌ Failed to parse incoming message: ${e.message}"
                )
            }
        }
    }

    private fun sendMessage(text: String) {
        val msg = MessageRequest(
            message = text,
            senderId = currentUser.id
        )

        val json = Json.encodeToString(msg)
        Log.d("ChatViewModel", "📤 Sending message: $json")

        chatWebSocketClient.sendMessage(json)
    }

    private fun sendMessage(file: File) {
        viewModelScope.launch {
            chatWebSocketClient.sendFile(
                file,
                currentUser.id,
                chatRoomDetails.roomId
            )
        }
    }

    private fun loadMessages(roomId: Int) {
        viewModelScope.launch {
            when (val result = messageRepository.getMessages(roomId)) {
                is Handler.Success -> {
                    _state.update { it.copy(messages = result.data) }
                }

                is Handler.Error -> {
                    Log.e("ChatViewModel", "❌ Load messages error: ${result.message}")
                }
            }
        }
    }

    override fun onCleared() {
        chatWebSocketClient.close()
        super.onCleared()
    }
}
