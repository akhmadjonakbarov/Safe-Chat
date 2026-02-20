package uz.akbarovdev.safechat.presentations.auth.presentation.view_model

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import uz.akbarovdev.safechat.core.enums.PreferenceKeys
import uz.akbarovdev.safechat.core.extension.sharedPreferences
import uz.akbarovdev.safechat.core.wrapper.Handler
import uz.akbarovdev.safechat.presentations.auth.domain.models.LoginRequest
import uz.akbarovdev.safechat.presentations.auth.domain.models.RegisterRequest
import uz.akbarovdev.safechat.presentations.auth.domain.models.User
import uz.akbarovdev.safechat.presentations.auth.domain.repository.AuthRepository

class AuthViewModel(
    private val applicationContext: Context,
    private val authRepository: AuthRepository
) : ViewModel() {

    var token: String by applicationContext.sharedPreferences(PreferenceKeys.Token.name)
    var user: String by applicationContext.sharedPreferences(PreferenceKeys.User.name)

    private val eventChannels = Channel<AuthEvent>()
    val events = eventChannels.receiveAsFlow()

    private var hasLoadedInitialData = false
    private val _state = MutableStateFlow(AuthState())
    val state = _state
        .onStart {
            if (!hasLoadedInitialData) {
                // Load saved user credentials if needed
                hasLoadedInitialData = true
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = AuthState()
        )

    fun onAction(action: AuthAction) {
        when (action) {
            is AuthAction.EmailChanged -> {
                _state.update { it.copy(email = action.value, error = null) }
            }

            is AuthAction.PasswordChanged -> {
                _state.update { it.copy(password = action.value, error = null) }
            }

            AuthAction.ToggleMode -> {
                _state.update {
                    it.copy(
                        isLogin = !it.isLogin,
                        error = null,
                        email = "",
                        password = ""
                    )
                }
            }

            AuthAction.Submit -> {
                viewModelScope.launch {

                    performAuth()
                }
            }

            is AuthAction.ConfirmPasswordChanged -> {
                _state.update { it.copy(password2 = action.value) }
            }

            is AuthAction.UsernameChanged -> {
                _state.update { it.copy(username = action.username) }
            }
        }
    }


    private suspend fun performAuth() {
        try {
            _state.update { it.copy(isLoading = true, error = null) }
            val currentState = _state.value

            // Validate input fields
            if (currentState.email.isBlank() || currentState.password.isBlank()) {
                _state.update { it.copy(error = "Email and password cannot be empty.") }
                eventChannels.send(AuthEvent.HasError)
                return
            }

            // Registration-specific validation
            if (!currentState.isLogin) {
                if (currentState.password2.isBlank()) {
                    _state.update { it.copy(error = "Confirm password cannot be empty.") }
                    eventChannels.send(AuthEvent.HasError)
                    return
                }
                if (currentState.password != currentState.password2) {
                    _state.update { it.copy(error = "Passwords do not match.") }
                    eventChannels.send(AuthEvent.HasError)
                    return
                }
            }

            // Make request (login or register)
            val result: Handler<User> = if (currentState.isLogin) {
                val loginReq = LoginRequest(
                    email = currentState.email,
                    password = currentState.password
                )
                authRepository.login(loginReq)
            } else {
                val registerReq = RegisterRequest(
                    email = currentState.email,
                    password = currentState.password,
                    username = currentState.username
                )
                authRepository.register(registerReq)
            }

            // Handle API response
            when (result) {
                is Handler.Success -> {
                    val userData = result.data
                    user = Json.encodeToString(userData)
                    token = userData.token
                    _state.update { it.copy(isLoading = false, error = null) }

                    if (currentState.isLogin) {
                        eventChannels.send(AuthEvent.LoginSuccess)
                    } else {
                        eventChannels.send(AuthEvent.RegisterSuccess)
                    }
                }

                is Handler.Error -> {
                    _state.update { it.copy(isLoading = false, error = result.message) }
                    eventChannels.send(AuthEvent.HasError)
                }
            }
        } catch (e: Exception) {
            _state.update {
                it.copy(
                    isLoading = false,
                    error = e.localizedMessage ?: "Unexpected error"
                )
            }
            eventChannels.send(AuthEvent.HasError)
        } finally {
            _state.update { it.copy(isLoading = false) }
        }
    }

}
