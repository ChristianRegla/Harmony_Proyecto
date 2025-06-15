package com.example.harmony.ui.notifications

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.harmony.R
import com.example.harmony.ui.components.SystemBarStyle
import com.example.harmony.ui.components.TopBarEditar
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.ui.res.stringResource

@Composable
fun NotificationsScreen(navController: NavHostController) {

    val headerTitle = stringResource(R.string.notificaciones)

    SystemBarStyle()

    var localNotificationsEnabled by remember { mutableStateOf(true) }

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.background_inicio),
            contentDescription = "Background Image",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        Scaffold(
            topBar = {
                TopBarEditar(
                    onBackClick = { navController.popBackStack() },
                    title = headerTitle
                )
            },
            containerColor = Color.Transparent,
            contentColor = Color.White
        ) { innerPadding ->
            NotificationsContent(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                notificationsEnabled = localNotificationsEnabled,
                onNotificationSettingChanged = { newState ->
                    localNotificationsEnabled = newState
                }
            )
        }
    }

}

@Composable
fun NotificationsContent(
    modifier: Modifier = Modifier,
    notificationsEnabled: Boolean,
    onNotificationSettingChanged: (Boolean) -> Unit
) {
    Column(
        modifier = modifier
            .padding(16.dp)
    ) {
        Text(
            text = stringResource(R.string.configuracion_de_notificaciones),
            style = MaterialTheme.typography.headlineSmall,
            color = Color.White,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = Color.White.copy(alpha = 0.15f)
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column {
                NotificationSettingItem(
                    title = stringResource(R.string.recordatorios_diarios),
                    description = stringResource(R.string.recibir_recordatorios_para_registrar_emocion),
                    isChecked = notificationsEnabled,
                    onCheckedChanged = onNotificationSettingChanged
                )
            }
        }
        Spacer(modifier = Modifier.height(24.dp))

    }
}

@Composable
fun NotificationSettingItem(
    title: String,
    description: String,
    isChecked: Boolean,
    onCheckedChanged: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 20.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                color = Color.White
            )
            Text(
                text = description,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.White.copy(alpha = 0.7f),
                modifier = Modifier.padding(top = 4.dp)
            )
        }
        Spacer(modifier = Modifier.width(16.dp))
        Switch(
            checked = isChecked,
            onCheckedChange = onCheckedChanged,
            colors = SwitchDefaults.colors(
                checkedThumbColor = Color.White,
                uncheckedThumbColor = Color.White.copy(alpha = 0.7f),
                uncheckedTrackColor = Color.Gray.copy(alpha = 0.5f),
                uncheckedBorderColor = Color.Gray.copy(alpha = 0.75f)
            )
        )
    }
}