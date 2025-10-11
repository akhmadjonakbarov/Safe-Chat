package uz.akbarovdev.safechat.core.design_system.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80
)


private val LightColorScheme = lightColorScheme(
    primary = Primary,
    onPrimary = Color.White,               // Text/icons on primary
    primaryContainer = PrimaryVariant,     // Optional container color

    secondary = Secondary,
    onSecondary = Color.White,             // Text/icons on secondary
    secondaryContainer = SecondaryVariant, // Optional container

    tertiary = Info,
    onTertiary = Color.White,              // Text/icons on tertiary

    background = Background,
    onBackground = TextPrimary,

    surface = Surface,
    onSurface = TextPrimary,
    onSurfaceVariant = TextSecondary,   // 👈 perfect place for secondary text color

    error = Error,
    onError = Color.White,

    // Optional status colors
    // success = Success, // Not in default M3 color scheme, can handle separately
    // warning = Warning
)


@Composable
fun SafeChatTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
//    val colorScheme = when {
//        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
//            val context = LocalContext.current
//            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
//        }
//
//        darkTheme -> DarkColorScheme
//        else -> LightColorScheme
//    }

    MaterialTheme(
        colorScheme = LightColorScheme,
        typography = Typography,
        content = content
    )
}