package uz.akbarovdev.safechat.presentations.auth.presentation.view_model
data class AuthState(
    val email: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val error: String? = null,
    val isLogin: Boolean = true // true = Login screen, false = Sign Up
)