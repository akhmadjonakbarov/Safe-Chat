package uz.akbarovdev.safechat.presentations.auth.presentation.view_model
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import uz.akbarovdev.safechat.presentations.auth.presentation.view_model.AuthAction

class AuthViewModel : ViewModel() {

    private var hasLoadedInitialData = false

    private val _state = MutableStateFlow(AuthState())
    val state = _state
        .onStart {
            if (!hasLoadedInitialData) {
                // Load any saved user credentials or preferences here
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
                // Start login/signup simulation
                val currentState = _state.value

                if (currentState.email.isBlank() || currentState.password.isBlank()) {
                    _state.update { it.copy(error = "Email and password cannot be empty.") }
                    return
                }

                viewModelScope.launch {
                    _state.update { it.copy(isLoading = true, error = null) }

                    // Simulate a network call (Retrofit or similar)
                    kotlinx.coroutines.delay(1500L)

                    if (currentState.isLogin) {
                        // Fake login success check
                        if (currentState.email == "test@example.com" && currentState.password == "1234") {
                            _state.update { it.copy(isLoading = false, error = null) }
                            // You could navigate to the home screen here
                        } else {
                            _state.update { it.copy(isLoading = false, error = "Invalid credentials") }
                        }
                    } else {
                        // Fake sign-up logic
                        _state.update { it.copy(isLoading = false, error = "Account created (fake)") }
                    }
                }
            }
        }
    }
}