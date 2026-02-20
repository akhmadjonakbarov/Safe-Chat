package uz.akbarovdev.safechat.presentations.home.presenation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import uz.akbarovdev.safechat.core.design_system.ui.theme.SafeChatTheme
import uz.akbarovdev.safechat.presentations.home.domain.models.ChatRoom
import uz.akbarovdev.safechat.presentations.home.domain.models.ChatRoomUser
import uz.akbarovdev.safechat.presentations.home.domain.models.User


@Composable
fun UserItem(
    modifier: Modifier = Modifier,
    user: User,
    onClickOpenChatRoom: (user: User) -> Unit
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Max)
            .clickable(onClick = {
                onClickOpenChatRoom(user)
            }),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent)
    ) {
        Row(
            Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row {
                Box(
                    modifier = Modifier
                        .size(50.dp)
                        .clip(CircleShape)
                        .background(Color.Red)
                )
                Spacer(Modifier.widthIn(8.dp, 15.dp))
                Column(
                    Modifier.fillMaxHeight(),
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        user.email,
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.W500,
                            fontSize = 20.sp
                        ),
                    )
                    Spacer(Modifier.height(6.dp))
                    Text(
                        user.username,
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                }
            }
//            Column(
//                horizontalAlignment = Alignment.End
//            ) {
//                Text(
//                    "2 min ago",
//                    style = MaterialTheme.typography.labelLarge,
//                    color = MaterialTheme.colorScheme.onSurfaceVariant,
//                )
//                Spacer(Modifier.height(3.dp))
//                if (chatRoom.unReadMessageCount > 0) {
//                    Box(
//                        Modifier
//                            .size(25.dp)
//                            .clip(CircleShape)
//                            .background(MaterialTheme.colorScheme.primary),
//                        contentAlignment = Alignment.Center
//                    ) {
//                        Text(
//                            "${chatRoom.unReadMessageCount}",
//                            color = MaterialTheme.colorScheme.onPrimary
//                        )
//                    }
//                }
//            }
        }
    }
}

@Preview
@Composable
private fun ChatPreview() {
    SafeChatTheme {
        UserItem(
            user = User(
                1, "blackhat", "blackhat@gmail.com"
            ), onClickOpenChatRoom = {}
        )
    }
}