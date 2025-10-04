package uz.akbarovdev.safechat.core.extension

import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

fun CoroutineScope.showImmediateSnackBar(
    snackBarHostState: SnackbarHostState,
    message: String,
    durationMillis: Long = 1500L,
    actionLabel: String? = null
) {
    snackBarHostState.currentSnackbarData?.dismiss()
    launch {

        launch {
            snackBarHostState.showSnackbar(
                message = message,
                actionLabel = actionLabel,
                duration = SnackbarDuration.Indefinite
            )
        }


        launch {
            delay(durationMillis)
            snackBarHostState.currentSnackbarData?.dismiss()
        }
    }
}
