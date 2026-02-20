package uz.akbarovdev.safechat.presentations.auth.presentation.view_model

sealed interface AuthAction {
    data class EmailChanged(val value: String) : AuthAction
    data class UsernameChanged(val username: String) : AuthAction
    data class PasswordChanged(val value: String) : AuthAction
    data class ConfirmPasswordChanged(val value: String) : AuthAction
    object Submit : AuthAction
    object ToggleMode : AuthAction
}