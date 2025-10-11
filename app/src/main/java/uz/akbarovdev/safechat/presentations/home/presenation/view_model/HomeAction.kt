package uz.akbarovdev.safechat.presentations.home.presenation.view_model

sealed interface HomeAction {
    data object OnLoadChatRooms : HomeAction
    data class OnSearchUsers(val query: String) : HomeAction
}