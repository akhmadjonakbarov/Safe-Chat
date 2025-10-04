package uz.akbarovdev.safechat.presentations.chat.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import uz.akbarovdev.safechat.presentations.chat.domain.models.Message

@Composable
fun MessageBubble(message: Message, currentUserId: Int) {
    val isMine = message.senderId == currentUserId
    val bubbleColor = if (isMine) Color(0xFFDCF8C6) else Color(0xFFE0E0E0)
    val bubbleShape = if (isMine) {
        RoundedCornerShape(
            topStart = 12.dp,
            topEnd = 0.dp,
            bottomEnd = 12.dp,
            bottomStart = 12.dp
        )
    } else {
        RoundedCornerShape(
            topStart = 0.dp,
            topEnd = 12.dp,
            bottomEnd = 12.dp,
            bottomStart = 12.dp
        )
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp, horizontal = 8.dp),
        contentAlignment = if (isMine) Alignment.CenterEnd else Alignment.CenterStart
    ) {
        Text(
            text = message.message,
            modifier = Modifier
                .background(bubbleColor, shape = bubbleShape)
                .padding(horizontal = 10.dp, vertical = 8.dp)
                .widthIn(max = LocalConfiguration.current.screenWidthDp.dp * 0.6f),
            color = Color.Black,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}
