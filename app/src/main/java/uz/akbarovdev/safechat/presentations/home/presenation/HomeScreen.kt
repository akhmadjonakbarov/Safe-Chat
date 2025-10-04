package uz.akbarovdev.safechat.presentations.home.presenation

import android.graphics.drawable.Icon
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SegmentedButtonDefaults.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import uz.akbarovdev.safechat.presentations.home.presenation.view_model.HomeAction
import uz.akbarovdev.safechat.presentations.home.presenation.view_model.HomeState
import uz.akbarovdev.safechat.presentations.home.presenation.view_model.HomeViewModel
import uz.akbarovdev.safechat.ui.theme.SafeChatTheme
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme

@Composable
fun HomeRoot(
    viewModel: HomeViewModel = viewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    HomeScreen(
        state = state,
        onAction = viewModel::onAction
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    state: HomeState,
    onAction: (HomeAction) -> Unit,
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text("Users")
                },
            )
        }
    ) { innerPadding ->
        Column(
            Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(horizontal = 10.dp)
        ) {
            TextField(
                modifier = Modifier.fillMaxWidth(),
                placeholder = {
                    Text("Search users")
                },
                value = "", onValueChange = {},
                suffix = {
                    Icon(
                        imageVector = Icons.Filled.Search,
                        contentDescription = "Search"
                    )
                }
            )

            Card(
                modifier = Modifier.clickable(
                    onClick = {}
                )
            ) {
                Column(
                    Modifier
                        .fillMaxWidth()
                        .padding(vertical = 15.dp, horizontal = 10.dp)
                ) {
                    Row(Modifier.fillMaxWidth()) {
                        Text(
                            "Akhmadjon", style = MaterialTheme.typography.titleMedium,
                        )
                    }
                    Text(
                        "Hello from Akhmad",
                        style = MaterialTheme.typography.bodySmall
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
        HomeScreen(
            state = HomeState(),
            onAction = {}
        )
    }
}