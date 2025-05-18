package com.example.harmony.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.EmojiPeople
import androidx.compose.material.icons.filled.NotificationsNone
import androidx.compose.material.icons.filled.PersonOutline
import androidx.compose.material.icons.outlined.HelpOutline
import androidx.compose.material.icons.outlined.Logout
import androidx.compose.material.icons.outlined.MonetizationOn
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.harmony.R
import com.example.harmony.ui.common.DrawerActions
import com.example.harmony.ui.home.HomeViewModel
import com. example. harmony. ui. profile. ProfileViewModel
import coil3.compose. rememberAsyncImagePainter
import com.example.harmony.ui.common.DataBaseActions

@Composable
fun DrawerContentComponent(navController: NavHostController, drawerActions: DrawerActions, dataBaseActions: DataBaseActions) {
    val context = LocalContext.current
    val apodo = if((drawerActions is HomeViewModel)){
        drawerActions.apodo.collectAsState().value
    } else {
        context.getString(R.string.user_name)
    }
    val imagenUrl =  if (( dataBaseActions is ProfileViewModel)) {
        dataBaseActions.imagenUrl.collectAsState().value
    } else {
        null
    }
    Spacer(modifier = Modifier.height(32.dp))

    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        if (imagenUrl != null) {
            val resId = context.resources.getIdentifier(imagenUrl, "drawable", context.packageName)
            if (resId != 0) {
                Image(
                    painter = painterResource(id = resId),
                    contentDescription = null,
                    modifier = Modifier
                        .size(100.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
            } else {
                Text("Imagen no encontrada")
            }
        } else {
            Text("Cargando imagen...")
        }
        }

    Text(
        text = apodo,
        fontSize = 24.sp,
        textAlign = TextAlign.Center,
        color = Color.White,
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
    )

    HorizontalDivider()

    Column(
        modifier = Modifier
            .background(color = Color(0xFFFAFAFA))
            .fillMaxHeight()
    ) {
        Spacer(modifier = Modifier.height(32.dp))

        // Ícono de Editar Perfil
        Box(
            modifier = Modifier
                .padding(start = 10.dp, end = 10.dp)
                .clip(RoundedCornerShape(26.dp))
                .wrapContentHeight()
                .shadow(
                    elevation = 4.dp,
                    shape = RoundedCornerShape(26.dp),
                    ambientColor = Color.Black.copy(alpha = 0.3f),
                    spotColor = Color.Black.copy(alpha = 0.3f)
                )
        ) {
            NavigationDrawerItem(
                icon = {
                    Icon(
                        painterResource(id = R.drawable.icono_editar_perfil),
                        contentDescription = "Account",
                        tint = Color.Black,
                        modifier = Modifier
                            .size(27.dp)
                    ) },
                label = {
                    Text(
                        text = context.getString(R.string.editar_perfil),
                        fontSize = 16.sp,
                        color = Color.Black,
                        modifier = Modifier.padding(16.dp)
                    )
                },
                selected = false,
                onClick = { navController.navigate("perfil") },
                modifier = Modifier
                    .background(color = Color(0xFFE3E3E3))
                    .wrapContentHeight()
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Ícono de Notificaciones
        Box(
            modifier = Modifier
                .padding(start = 10.dp, end = 10.dp)
                .clip(RoundedCornerShape(26.dp))
                .wrapContentHeight()
        ) {
            NavigationDrawerItem(
                icon = {
                    Icon(
                        imageVector = Icons.Filled.NotificationsNone,
                        contentDescription = "Account",
                        tint = Color.Black,
                        modifier = Modifier
                            .size(27.dp)
                    ) },
                label = {
                    Text(
                        text = context.getString(R.string.notificaciones),
                        fontSize = 16.sp,
                        color = Color.Black,
                        modifier = Modifier.padding(16.dp)
                    )
                },
                selected = false,
                onClick = {},
                modifier = Modifier
                    .background(color = Color(0xFFE3E3E3))
                    .wrapContentHeight()
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Ícono de Donaciones
        Box(
            modifier = Modifier
                .padding(start = 10.dp, end = 10.dp)
                .clip(RoundedCornerShape(26.dp))
                .wrapContentHeight()
        ) {
            NavigationDrawerItem(
                icon = {
                    Icon(
                        painterResource(id = R.drawable.donaciones_icon),
                        contentDescription = "Account",
                        tint = Color.Black,
                        modifier = Modifier
                            .size(27.dp)
                    ) },
                label = {
                    Text(
                        text = context.getString(R.string.donaciones),
                        fontSize = 16.sp,
                        color = Color.Black,
                        modifier = Modifier.padding(16.dp)
                    )
                },
                selected = false,
                onClick = { navController.navigate("donation") },
                modifier = Modifier
                    .background(color = Color(0xFFE3E3E3))
                    .wrapContentHeight()
            )
        }


        Spacer(modifier = Modifier.height(32.dp))

        // Ícono de Centro de Ayuda
        Box(
            modifier = Modifier
                .padding(start = 10.dp, end = 10.dp)
                .clip(RoundedCornerShape(26.dp))
                .wrapContentHeight()
        ) {
            NavigationDrawerItem(
                icon = {
                    Icon(
                        painterResource(id = R.drawable.centro_de_ayuda_icon),
                        contentDescription = "Account",
                        tint = Color.Black,
                        modifier = Modifier
                            .size(27.dp)
                    ) },
                label = {
                    Text(
                        text = context.getString(R.string.centro_de_ayuda),
                        fontSize = 16.sp,
                        color = Color.Black,
                        modifier = Modifier
                            .padding(16.dp)
                    )
                },
                selected = false,
                onClick = { navController.navigate("privacyNotice") },
                modifier = Modifier
                    .background(color = Color(0xFFE3E3E3))
                    .wrapContentHeight()
            )
        }


        Spacer(modifier = Modifier.height(32.dp))

        // Ícono de Cerrar Sesión
        Box(
            modifier = Modifier
                .padding(start = 10.dp, end = 10.dp)
                .clip(RoundedCornerShape(26.dp))
                .wrapContentHeight()
        ) {
            NavigationDrawerItem(
                icon = {
                    Icon(
                        imageVector = Icons.Outlined.Logout,
                        contentDescription = "Account",
                        tint = Color.Black,
                        modifier = Modifier
                            .size(27.dp)
                    ) },
                label = {
                    Text(
                        text = context.getString(R.string.cerrar_sesi_n),
                        fontSize = 16.sp,
                        color = Color.Black,
                        modifier = Modifier.padding(16.dp)
                    )
                },
                selected = false,
                onClick = { drawerActions.cerrarSesion(navController) },
                modifier = Modifier
                    .background(color = Color(0xFFE3E3E3))
                    .wrapContentHeight()
            )
        }
    }
}