package com.example.state.todo

import android.accounts.AuthenticatorDescription
import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.state.R
import java.util.*

/**
 * @author AlexisYin
 */
data class TodoItem(
    val task: String,
    val icon: TodoIcon = TodoIcon.Default,
    val id: UUID = UUID.randomUUID()
)

enum class TodoIcon(
    val imageVector: ImageVector,
    @StringRes val contentDescription: Int
) {
    Square(Icons.Default.CropSquare, R.string.expand),
    Done(Icons.Default.Done, R.string.done),
    Event(Icons.Default.Event, R.string.event),
    Privacy(Icons.Default.PrivacyTip, R.string.privacy),
    Trash(Icons.Default.RestoreFromTrash, R.string.restore);

    companion object {
        val Default = Square
    }
}