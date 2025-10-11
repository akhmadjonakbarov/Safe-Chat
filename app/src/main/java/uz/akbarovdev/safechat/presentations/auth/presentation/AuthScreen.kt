package uz.akbarovdev.safechat.presentations.auth.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import uz.akbarovdev.safechat.app.navigation.Routes
import uz.akbarovdev.safechat.presentations.auth.presentation.view_model.AuthAction
import uz.akbarovdev.safechat.presentations.auth.presentation.view_model.AuthEvent
import uz.akbarovdev.safechat.presentations.auth.presentation.view_model.AuthState
import uz.akbarovdev.safechat.presentations.auth.presentation.view_model.AuthViewModel
import uz.akbarovdev.safechat.core.design_system.ui.theme.SafeChatTheme

@Composable
fun AuthRoot(
    navController: NavController,
    viewModel: AuthViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    val snackBarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    AuthScreen(
        state = state,
        snackBarHostState = snackBarHostState,
        onAction = viewModel::onAction
    )


    LaunchedEffect(Unit) {
        viewModel.events.collect { event ->
            when (event) {
                is AuthEvent.LoginSuccess -> {
                    coroutineScope.launch {
                        snackBarHostState.showSnackbar("You sign in successfully")
                        delay(1000)
                        navController.navigate(Routes.Home)
                    }
                }

                is AuthEvent.RegisterSuccess -> {
                    navController.navigate(Routes.Home)
                }

                is AuthEvent.HasError -> {
                    coroutineScope.launch {
                        snackBarHostState.showSnackbar(state.error ?: "Unknown Error")
                    }
                }
            }
        }
    }


}

@Composable
fun AuthScreen(
    state: AuthState,
    snackBarHostState: SnackbarHostState,
    onAction: (AuthAction) -> Unit,
) {
    val focusManager = LocalFocusManager.current
    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackBarHostState) { data ->
                Snackbar(
                    snackbarData = data,
                    containerColor = if (state.error != null) Color(0xFFD32F2F) else MaterialTheme.colorScheme.primary,
                    contentColor = Color.White
                )
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // App title
                Text(
                    text = "SafeChat",
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.ExtraBold,
                    color = MaterialTheme.colorScheme.primary
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Screen title (Login / Sign Up)
                Text(
                    text = if (state.isLogin) "Login" else "Sign Up",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(20.dp))

                // Email field
                OutlinedTextField(
                    value = state.email,
                    onValueChange = { onAction(AuthAction.EmailChanged(it)) },
                    label = { Text("Email") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                // Password field
                OutlinedTextField(
                    value = state.password,
                    onValueChange = { onAction(AuthAction.PasswordChanged(it)) },
                    label = { Text("Password") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
                )

                if (!state.isLogin) {
                    // Confirm password field
                    OutlinedTextField(
                        value = state.password2,
                        onValueChange = { onAction(AuthAction.ConfirmPasswordChanged(it)) },
                        label = { Text("Confirm Password") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth(),
                        visualTransformation = PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Submit button
                Button(
                    onClick = {
                        onAction(AuthAction.Submit)
                        focusManager.clearFocus()
                    },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = !state.isLoading
                ) {
                    if (state.isLoading) {
                        CircularProgressIndicator(
                            color = Color.White,
                            modifier = Modifier.size(20.dp),
                            strokeWidth = 2.dp
                        )
                    } else {
                        Text(if (state.isLogin) "Login" else "Sign Up")
                    }

                }

                Spacer(modifier = Modifier.height(18.dp))

                TextButton(onClick = { onAction(AuthAction.ToggleMode) }) {
                    Text(
                        text = if (state.isLogin)
                            "Don’t have an account? Sign Up"
                        else
                            "Already have an account? Login"
                    )
                }
            }
        }
    }
}


@Preview
@Composable
private fun Preview() {
    SafeChatTheme {
        AuthScreen(
            state = AuthState(
                isLogin = true
            ),
            snackBarHostState = SnackbarHostState(),
            onAction = {}
        )
    }
}