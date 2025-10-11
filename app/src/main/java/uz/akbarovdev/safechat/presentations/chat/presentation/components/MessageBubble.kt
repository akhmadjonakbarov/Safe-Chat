package uz.akbarovdev.safechat.presentations.chat.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AttachFile
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import uz.akbarovdev.safechat.presentations.chat.domain.models.Message

@Composable
fun MessageBubble(message: Message, currentUserId: Int) {
    val isReceiver = message.senderId == currentUserId
    val bubbleColor = if (isReceiver) Color(0xFFF3E5F5) else MaterialTheme.colorScheme.primary

    val textColor = if (isReceiver) Color.Black else MaterialTheme.colorScheme.onPrimary

    val bubbleShape = if (isReceiver) {
        RoundedCornerShape(topStart = 0.dp, topEnd = 12.dp, bottomEnd = 12.dp, bottomStart = 12.dp)
    } else {
        RoundedCornerShape(topStart = 12.dp, topEnd = 0.dp, bottomEnd = 12.dp, bottomStart = 12.dp)
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp, horizontal = 8.dp),
        contentAlignment = if (isReceiver) Alignment.CenterStart else Alignment.CenterEnd
    ) {
        Box(
            modifier = Modifier
                .background(bubbleColor, shape = bubbleShape)
                .padding(horizontal = 10.dp, vertical = 8.dp)
                .widthIn(max = LocalConfiguration.current.screenWidthDp.dp * 0.7f)
        ) {
            when (message.type) {
                "text" -> {
                    Text(
                        text = message.message,
                        color = textColor,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }

                "message" -> {
                    Text(
                        text = message.message,
                        color = textColor,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }

                "image" -> {
                    message.fileUrl?.let { imageUrl ->
                        AsyncImage(
                            model = "http://10.0.2.2:8000/$imageUrl",
                            contentDescription = "Image message",
                            modifier = Modifier
                                .clip(RoundedCornerShape(10.dp))
                                .sizeIn(maxWidth = 220.dp, maxHeight = 220.dp),
                            contentScale = ContentScale.Crop
                        )
                    } ?: Text("Image unavailable", color = textColor)
                }

                "file" -> {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .clickable {
                                // handle file open or download
                            }
                            .padding(8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.AttachFile,
                            contentDescription = "File attachment",
                            tint = textColor,
                            modifier = Modifier.size(32.dp)
                        )
                        Text(
                            text = message.fileUrl?.substringAfterLast('/') ?: "Unknown file",
                            color = textColor,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }

                else -> Text("Unsupported message type", color = textColor)
            }
        }
    }
}


@Preview
@Composable
private fun MessageBubblePreview() {
    val message = Message(
        "Check this out!",
        1,
        1,
        "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQ4OgBuC_EJ401qKWo9ZK9vhouZ6Xw5qQqSuw&s",
        "image", isRead = false
    )
    MessageBubble(
        message, currentUserId = 2
    )
}