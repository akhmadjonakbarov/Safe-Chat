package uz.akbarovdev.safechat

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import uz.akbarovdev.safechat.app.navigation.NavigationRoot
import uz.akbarovdev.safechat.ui.theme.SafeChatTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SafeChatTheme {
                NavigationRoot(rememberNavController())
            }
        }
    }
}
