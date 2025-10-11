package uz.akbarovdev.safechat.presentations.chat.presentation.components

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import uz.akbarovdev.safechat.R
import uz.akbarovdev.safechat.core.design_system.ui.theme.SafeChatTheme
import uz.akbarovdev.safechat.core.enums.MessageType
import java.io.File
import android.content.Context
import androidx.compose.ui.platform.LocalContext
import java.io.FileOutputStream
import java.io.InputStream
import java.io.OutputStream

fun uriToFile(context: Context, uri: Uri): File? {
    return try {
        val inputStream: InputStream? = context.contentResolver.openInputStream(uri)
        val tempFile = File.createTempFile("picked_", ".jpg", context.cacheDir)
        val outputStream: OutputStream = FileOutputStream(tempFile)

        inputStream?.copyTo(outputStream)
        inputStream?.close()
        outputStream.close()

        tempFile
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}


@Composable
fun ChatInputField(
    value: String,
    isFileSelected: Boolean,
    messageType: MessageType,
    onValueChange: (String) -> Unit,
    onClickSelectFile: (File) -> Unit,
    onSendMessage: () -> Unit,
    onSendFile: () -> Unit,
) {
    val colorScheme = MaterialTheme.colorScheme

    val context = LocalContext.current

    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            val file = uriToFile(context, it)
            file?.let { onClickSelectFile(file) }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 8.dp)
    ) {
        Surface(
            shape = RoundedCornerShape(30.dp),
            tonalElevation = 6.dp,
            shadowElevation = 3.dp,
            color = colorScheme.surface,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 2.dp)
                    .fillMaxWidth()
            ) {
                TextField(
                    value = value,
                    onValueChange = onValueChange,
                    modifier = Modifier
                        .weight(1f)
                        .background(Color.Transparent),
                    placeholder = {
                        Text(
                            text = "Type a message...",
                            color = colorScheme.onSurface.copy(alpha = 0.5f),
                            style = MaterialTheme.typography.bodyMedium
                        )
                    },
                    textStyle = MaterialTheme.typography.bodyLarge.copy(
                        color = colorScheme.onSurface
                    ),
                    singleLine = true,
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        disabledContainerColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent,
                        cursorColor = colorScheme.primary
                    )
                )

                Spacer(modifier = Modifier.width(10.dp))

                IconButton(
                    onClick = {
                        if (isFileSelected) {
                            onSendFile()
                        } else {
                            if (messageType == MessageType.Text) {
                                onSendMessage()
                            } else {
                                galleryLauncher.launch("image/*")
                            }
                        }

                    },
                    modifier = Modifier
                        .size(46.dp)
                        .background(colorScheme.primary, CircleShape)
                ) {
                    if (isFileSelected) {
                        Icon(
                            imageVector = ImageVector.vectorResource(
                                R.drawable.outline_arrow_circle_up_24,
                            ),
                            contentDescription = "Send Message",
                            tint = colorScheme.onPrimary,
                            modifier = Modifier.size(24.dp)
                        )
                    } else {
                        if (messageType == MessageType.Text) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.Send,
                                contentDescription = "Send Message",
                                tint = colorScheme.onPrimary,
                                modifier = Modifier.size(24.dp)
                            )
                        } else {
                            Icon(
                                imageVector = ImageVector.vectorResource(
                                    R.drawable.outline_attach_file_24
                                ),
                                contentDescription = "Send Message",
                                tint = colorScheme.onPrimary,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    }

                }
            }
        }
    }
}


@Preview
@Composable
private fun ChatInputFieldPreview() {
    SafeChatTheme {
        ChatInputField(
            value = "", isFileSelected = true, messageType = MessageType.Image,
            onValueChange = {},
            onClickSelectFile = {}, onSendFile = {}, onSendMessage = {}
        )
    }
}