package com.example.harmony.ui.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.example.harmony.R


// Puedes poner esto en el mismo archivo ChatScreen.kt o en un archivo de componentes separado.
@Composable
fun ConfirmationDialog(
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    val context = LocalContext.current

    val eliminar_conversacion = context.getString(R.string.eliminar_conversacion)
    val confirmacion_eliminar_conversacion = context.getString(R.string.confirmacion_eliminar_conversacion)
    val eliminar = context.getString(R.string.eliminar)
    val cancelar = context.getString(R.string.cancelar)

    AlertDialog(
        onDismissRequest = onDismiss, // Acción cuando se toca fuera del diálogo o se presiona "atrás"
        title = {
            Text(text = eliminar_conversacion)
        },
        text = {
            Text(text = confirmacion_eliminar_conversacion)
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
        // Opcional: puedes personalizar los colores y la forma del diálogo
        containerColor = MaterialTheme.colorScheme.surface, // O el color que prefieras para el fondo del diálogo
        titleContentColor = MaterialTheme.colorScheme.onSurface,
        textContentColor = MaterialTheme.colorScheme.onSurfaceVariant
    )
}