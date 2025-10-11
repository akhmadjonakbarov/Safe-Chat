package uz.akbarovdev.safechat.presentations.home.presenation.view_model

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import uz.akbarovdev.safechat.core.wrapper.Handler
import uz.akbarovdev.safechat.presentations.home.domain.models.ChatRoom
import uz.akbarovdev.safechat.presentations.home.domain.repositories.ChatRoomRepository


class HomeViewModel(
    private val chatRoomRepository: ChatRoomRepository,
) : ViewModel() {


    private var hasLoadedInitialData = false

    private val _state = MutableStateFlow(HomeState())
    val state = _state
        .onStart {
            if (!hasLoadedInitialData) {
                onAction(HomeAction.OnLoadChatRooms)
                hasLoadedInitialData = true
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = HomeState()
        )


    fun onAction(action: HomeAction) {
        when (action) {
            HomeAction.OnLoadChatRooms -> getChatRooms()
            is HomeAction.OnSearchUsers -> searchUsers(action.query)
        }
    }


    private fun searchUsers(query: String) = viewModelScope.launch {
        _state.update { it.copy(query = query) }
        val filteredChatRooms = state.value.chatRooms.filter { it.receiver.email.contains(query) }
        if (filteredChatRooms.isNotEmpty()) {
            _state.update { it.copy(chatRooms = filteredChatRooms) }
        } else {
            // TODO: send to backend to search users
        }
    }

    private fun getChatRooms() = viewModelScope.launch {
        _state.update { it.copy(loading = true) }

        when (val result = chatRoomRepository.getChatRooms()) {
            is Handler.Error -> {
                Log.e("HomeViewModel", result.message)
                _state.update { it.copy(loading = false) }
            }

            is Handler.Success -> {
                result.data.collect { chatRooms ->
                    _state.update {
                        it.copy(
                            chatRooms = chatRooms,
                            loading = false
                        )
                    }
                }
            }
        }
    }


}