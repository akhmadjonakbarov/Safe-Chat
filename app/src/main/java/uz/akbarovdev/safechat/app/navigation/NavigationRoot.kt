package uz.akbarovdev.safechat.app.navigation


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import uz.akbarovdev.safechat.presentations.auth.presentation.AuthRoot
import uz.akbarovdev.safechat.presentations.chat.presentation.ChatRoot
import uz.akbarovdev.safechat.presentations.home.presenation.HomeRoot


@Composable
fun NavigationRoot(

    navHostController: NavHostController,
) {


    NavHost(
        navController = navHostController, startDestination = Routes.Auth
    ) {
        composable<Routes.Home> {
            HomeRoot()
        }
        composable<Routes.Chat> {
            ChatRoot()
        }
        composable<Routes.Auth> {
            AuthRoot()
        }
        composable<Routes.Splash> {
            Scaffold { innerPadding ->
                Column(
                    modifier = Modifier.padding(innerPadding)
                ) { }
            }
        }

    }
}


