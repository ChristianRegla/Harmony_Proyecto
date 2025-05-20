package com.example.harmony.ui.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.example.harmony.R

@Composable
fun ConfirmationDialog(
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    val context = LocalContext.current

    val eliminarConversacion = context.getString(R.string.eliminar_conversacion)
    val confirmacionEliminarConversacion = context.getString(R.string.confirmacion_eliminar_conversacion)
    val eliminar = context.getString(R.string.eliminar)
    val cancelar = context.getString(R.string.cancelar)

    AlertDialog(
        onDismissRequest = onDismiss, // Acción cuando se toca fuera del diálogo o se presiona "atrás"
        title = {
            Text(text = eliminarConversacion)
        },
        text = {
            Text(text = confirmacionEliminarConversacion)
        },
        confirmButton = {
            TextButton(
                onClick = onConfirm
            ) {
                Text(text = eliminar, color = MaterialTheme.colorScheme.error) // Usar un color distintivo para la acción destructiva
            }
        },
        dismissButton = {
            TextButton(
                onClick = onDismiss
            ) {
                Text(text = cancelar)
            }
        },
        containerColor = MaterialTheme.colorScheme.surface,
        titleContentColor = MaterialTheme.colorScheme.onSurface,
        textContentColor = MaterialTheme.colorScheme.onSurfaceVariant
    )
}