package com.example.harmony.ui.perfil

import androidx.compose.foundation.Image
import com.example.harmony.R
import androidx. compose. ui. draw. clipToBounds
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.unit.Dp
import androidx. compose. ui. draw. drawBehind
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import com.example.harmony.ui.theme.BlueDark
import kotlinx.coroutines.launch
import android.annotation.SuppressLint
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.filled.NotificationsNone
import androidx.compose.material.icons.filled.PersonOutline
import androidx.compose.material.icons.outlined.HelpOutline
import androidx.compose.material.icons.outlined.Logout
import androidx.compose.material.icons.outlined.MonetizationOn
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.TextButton
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import com.example.harmony.ui.home.DrawerContent
import androidx. compose. ui. text. font. Font
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.compose.rememberNavController
import com.example.harmony.ui.home.HomeViewModel
import com.example.harmony.ui.home.TopBar
import androidx. compose. foundation. layout. wrapContentSize
/**
 * Created by codia-figma
 */
@Composable
fun Editar_PerfilScreen(navController: NavHostController, editarperfilViewModel: Editar_PerfilViewModel) {
    // Box-47:1273-Profile-1 / Edit - Bruce
    Box(
        contentAlignment = Alignment.TopStart,
        modifier = Modifier
            .size(412.dp, 917.dp)
            .clip(RoundedCornerShape(32.dp)),
    ) {
        // Box-47:1274-Nombre
        Box(
            contentAlignment = Alignment.TopStart,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .offset(x = 0.dp, y = 131.dp)
                .background(Color(0xffffffff))
                .size(368.dp, 57.dp),
        ) {
            // Empty-I47:1274;30:726-Rectangle 7
            Box(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .background(Color(0xfff3f8ff), RoundedCornerShape(8.dp))
                    .size(368.dp, 57.dp)
                    .border(1.dp, Color(0xff9e9e9e), RoundedCornerShape(8.dp)),
            )
            // Row-I47:1274;30:723-Value
            Row(
                horizontalArrangement = Arrangement.spacedBy(space = 8.dp, alignment = Alignment.CenterHorizontally),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .wrapContentHeight()
                    .offset(x = 0.dp, y = 24.dp)
                    .width(368.dp)
                    .padding(horizontal = 16.dp, vertical = 0.dp),
            ) {
                // Text-I47:1274;30:724-Enter here...
                Text(
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .fillMaxWidth()
                        .padding(start = 0.dp, end = 31.dp),
                    text = "Christian Josue Regla Andrade",
                    color = Color(0xff212121),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal,
                    textAlign = TextAlign.Left,
                    overflow = TextOverflow.Ellipsis,
                )
            }
            // Column-I47:1274;30:727-Label
            Column(
                verticalArrangement = Arrangement.spacedBy(space = 10.dp, alignment = Alignment.Top),
                horizontalAlignment = Alignment.Start,
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .wrapContentSize()
                    .offset(x = 12.dp, y = 2.dp),
            ) {
                // Row-I47:1274;30:729-Frame 89
                Row(
                    horizontalArrangement = Arrangement.spacedBy(space = 10.dp, alignment = Alignment.Start),
                    verticalAlignment = Alignment.Top,
                    modifier = Modifier
                        .align(Alignment.Start)
                        .wrapContentSize()
                        .padding(start = 4.dp, top = 6.dp, end = 4.dp, bottom = 1.dp),
                ) {
                    // Text-I47:1274;30:730-Label
                    Text(
                        modifier = Modifier.wrapContentSize(),
                        text = "Nombre Completo",
                        color = Color(0xff757575),
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Normal,
                        textAlign = TextAlign.Left,
                        overflow = TextOverflow.Ellipsis,
                    )
                }
            }
        }
        // Box-47:1275-Apodo
        Box(
            contentAlignment = Alignment.TopStart,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .offset(x = 0.dp, y = 222.dp)
                .size(368.dp, 57.dp),
        ) {
            // Empty-I47:1275;30:726-Rectangle 7
            Box(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .background(Color(0xfff3f8ff), RoundedCornerShape(8.dp))
                    .size(368.dp, 57.dp)
                    .border(1.dp, Color(0xff9e9e9e), RoundedCornerShape(8.dp)),
            )
            // Row-I47:1275;30:723-Value
            Row(
                horizontalArrangement = Arrangement.spacedBy(space = 8.dp, alignment = Alignment.CenterHorizontally),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .wrapContentHeight()
                    .offset(x = 0.dp, y = 24.dp)
                    .width(368.dp)
                    .padding(horizontal = 16.dp, vertical = 0.dp),
            ) {
                // Text-I47:1275;30:724-Enter here...
                Text(
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .fillMaxWidth()
                        .padding(start = 0.dp, end = 31.dp),
                    text = "Slappy",
                    color = Color(0xff212121),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal,
                    textAlign = TextAlign.Left,
                    overflow = TextOverflow.Ellipsis,
                )
            }
            // Column-I47:1275;30:727-Label
            Column(
                verticalArrangement = Arrangement.spacedBy(space = 10.dp, alignment = Alignment.Top),
                horizontalAlignment = Alignment.Start,
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .wrapContentSize()
                    .offset(x = 12.dp, y = 2.dp),
            ) {
                // Row-I47:1275;30:729-Frame 89
                Row(
                    horizontalArrangement = Arrangement.spacedBy(space = 10.dp, alignment = Alignment.Start),
                    verticalAlignment = Alignment.Top,
                    modifier = Modifier
                        .align(Alignment.Start)
                        .wrapContentSize()
                        .padding(start = 4.dp, top = 6.dp, end = 4.dp, bottom = 1.dp),
                ) {
                    // Text-I47:1275;30:730-Label
                    Text(
                        modifier = Modifier.wrapContentSize(),
                        text = "Apodo",
                        color = Color(0xff757575),
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Normal,
                        textAlign = TextAlign.Left,
                        overflow = TextOverflow.Ellipsis,
                    )
                }
            }
        }
        // Box-47:1276-Correo
        Box(
            contentAlignment = Alignment.TopStart,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .offset(x = 0.dp, y = 316.dp)
                .size(368.dp, 57.dp),
        ) {
            // Empty-I47:1276;30:726-Rectangle 7
            Box(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .background(Color(0xfff3f8ff), RoundedCornerShape(8.dp))
                    .size(368.dp, 57.dp)
                    .border(1.dp, Color(0xff9e9e9e), RoundedCornerShape(8.dp)),
            )
            // Row-I47:1276;30:723-Value
            Row(
                horizontalArrangement = Arrangement.spacedBy(space = 8.dp, alignment = Alignment.CenterHorizontally),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .wrapContentHeight()
                    .offset(x = 0.dp, y = 24.dp)
                    .width(368.dp)
                    .padding(horizontal = 16.dp, vertical = 0.dp),
            ) {
                // Text-I47:1276;30:724-Enter here...
                Text(
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .fillMaxWidth()
                        .padding(start = 0.dp, end = 31.dp),
                    text = "debajodelmar@domain.com",
                    color = Color(0xff212121),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal,
                    textAlign = TextAlign.Left,
                    overflow = TextOverflow.Ellipsis,
                )
            }
            // Column-I47:1276;30:727-Label
            Column(
                verticalArrangement = Arrangement.spacedBy(space = 10.dp, alignment = Alignment.Top),
                horizontalAlignment = Alignment.Start,
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .wrapContentSize()
                    .offset(x = 12.dp, y = 2.dp),
            ) {
                // Row-I47:1276;30:729-Frame 89
                Row(
                    horizontalArrangement = Arrangement.spacedBy(space = 10.dp, alignment = Alignment.Start),
                    verticalAlignment = Alignment.Top,
                    modifier = Modifier
                        .align(Alignment.Start)
                        .wrapContentSize()
                        .padding(start = 4.dp, top = 6.dp, end = 4.dp, bottom = 1.dp),
                ) {
                    // Text-I47:1276;30:730-Correo
                    Text(
                        modifier = Modifier.wrapContentSize(),
                        text = "Correo",
                        color = Color(0xff757575),
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Normal,
                        textAlign = TextAlign.Left,
                        overflow = TextOverflow.Ellipsis,
                    )
                }
            }
        }
        // Box-47:1277-Ciudad
        Box(
            contentAlignment = Alignment.TopStart,
            modifier = Modifier
                .align(Alignment.TopStart)
                .offset(x = 22.dp, y = 526.dp)
                .size(163.dp, 50.dp),
        ) {
            // Empty-I47:1277;89:14777-Rectangle 7
            Box(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .background(Color(0xfff3f8ff), RoundedCornerShape(8.dp))
                    .size(163.dp, 50.dp)
                    .border(1.dp, Color(0xff9e9e9e), RoundedCornerShape(8.dp)),
            )
            // Row-I47:1277;89:14778-Value
            Row(
                horizontalArrangement = Arrangement.spacedBy(space = 8.dp, alignment = Alignment.CenterHorizontally),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .wrapContentHeight()
                    .offset(x = 0.dp, y = 24.dp)
                    .width(163.dp)
                    .padding(horizontal = 16.dp, vertical = 0.dp),
            ) {
                // Text-I47:1277;89:14779-Enter here...
                Text(
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .fillMaxWidth()
                        .padding(start = 0.dp, end = 31.dp),
                    text = "México",
                    color = Color(0xff212121),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal,
                    textAlign = TextAlign.Left,
                    overflow = TextOverflow.Ellipsis,
                )
            }
            // Column-I47:1277;89:14781-Label
            Column(
                verticalArrangement = Arrangement.spacedBy(space = 10.dp, alignment = Alignment.Top),
                horizontalAlignment = Alignment.Start,
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .wrapContentSize()
                    .offset(x = 12.dp, y = 2.dp),
            ) {
                // Row-I47:1277;89:14783-Frame 89
                Row(
                    horizontalArrangement = Arrangement.spacedBy(space = 10.dp, alignment = Alignment.Start),
                    verticalAlignment = Alignment.Top,
                    modifier = Modifier
                        .align(Alignment.Start)
                        .wrapContentSize()
                        .padding(start = 4.dp, top = 6.dp, end = 4.dp, bottom = 1.dp),
                ) {
                    // Text-I47:1277;89:14784-Label
                    Text(
                        modifier = Modifier.wrapContentSize(),
                        text = "Ciudad",
                        color = Color(0xff757575),
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Normal,
                        textAlign = TextAlign.Left,
                        overflow = TextOverflow.Ellipsis,
                    )
                }
            }
            // Image-I47:1277;89:14819-arrow_drop_down
            Image(
                painter = painterResource(id = R.drawable.flecha_abajo),
                contentDescription = null,
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .offset(x = -8.dp, y = 0.dp)
                    .size(24.dp, 24.dp)
                    .clipToBounds(),
            )
        }
        // Box-47:1278-Genero
        Box(
            contentAlignment = Alignment.TopStart,
            modifier = Modifier
                .align(Alignment.TopStart)
                .offset(x = 229.dp, y = 526.dp)
                .size(163.dp, 50.dp),
        ) {
            // Empty-I47:1278;30:726-Rectangle 7
            Box(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .background(Color(0xfff3f8ff), RoundedCornerShape(8.dp))
                    .size(163.dp, 50.dp)
                    .border(1.dp, Color(0xff9e9e9e), RoundedCornerShape(8.dp)),
            )
            // Row-I47:1278;30:723-Value
            Row(
                horizontalArrangement = Arrangement.spacedBy(space = 8.dp, alignment = Alignment.CenterHorizontally),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .wrapContentHeight()
                    .offset(x = 0.dp, y = 24.dp)
                    .width(163.dp)
                    .padding(horizontal = 16.dp, vertical = 0.dp),
            ) {
                // Text-I47:1278;30:724-Enter here...
                Text(
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .fillMaxWidth()
                        .padding(start = 0.dp, end = 31.dp),
                    text = "Femenino",
                    color = Color(0xff212121),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal,
                    textAlign = TextAlign.Left,
                    overflow = TextOverflow.Ellipsis,
                )
            }
            // Column-I47:1278;30:727-Label
            Column(
                verticalArrangement = Arrangement.spacedBy(space = 10.dp, alignment = Alignment.Top),
                horizontalAlignment = Alignment.Start,
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .wrapContentSize()
                    .offset(x = 12.dp, y = 2.dp),
            ) {
                // Row-I47:1278;30:729-Frame 89
                Row(
                    horizontalArrangement = Arrangement.spacedBy(space = 10.dp, alignment = Alignment.Start),
                    verticalAlignment = Alignment.Top,
                    modifier = Modifier
                        .align(Alignment.Start)
                        .wrapContentSize()
                        .padding(start = 4.dp, top = 6.dp, end = 4.dp, bottom = 1.dp),
                ) {
                    // Text-I47:1278;30:730-Label
                    Text(
                        modifier = Modifier.wrapContentSize(),
                        text = "Genero",
                        color = Color(0xff757575),
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Normal,
                        textAlign = TextAlign.Left,
                        overflow = TextOverflow.Ellipsis,
                    )
                }
            }
        }
        // Box-47:1279-Domicilio
        Box(
            contentAlignment = Alignment.TopStart,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .offset(x = 0.dp, y = 619.dp)
                .size(368.dp, 57.dp),
        ) {
            // Empty-I47:1279;30:726-Rectangle 7
            Box(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .background(Color(0xfff3f8ff), RoundedCornerShape(8.dp))
                    .size(368.dp, 57.dp)
                    .border(1.dp, Color(0xff9e9e9e), RoundedCornerShape(8.dp)),
            )
            // Row-I47:1279;30:723-Value
            Row(
                horizontalArrangement = Arrangement.spacedBy(space = 8.dp, alignment = Alignment.CenterHorizontally),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .wrapContentHeight()
                    .offset(x = 0.dp, y = 24.dp)
                    .width(368.dp)
                    .padding(horizontal = 16.dp, vertical = 0.dp),
            ) {
                // Text-I47:1279;30:724-Enter here...
                Text(
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .fillMaxWidth()
                        .padding(start = 0.dp, end = 31.dp),
                    text = "45 New Avenue, New York",
                    color = Color(0xff212121),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal,
                    textAlign = TextAlign.Left,
                    overflow = TextOverflow.Ellipsis,
                )
            }
            // Column-I47:1279;30:727-Label
            Column(
                verticalArrangement = Arrangement.spacedBy(space = 10.dp, alignment = Alignment.Top),
                horizontalAlignment = Alignment.Start,
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .wrapContentSize()
                    .offset(x = 12.dp, y = 2.dp),
            ) {
                // Row-I47:1279;30:729-Frame 89
                Row(
                    horizontalArrangement = Arrangement.spacedBy(space = 10.dp, alignment = Alignment.Start),
                    verticalAlignment = Alignment.Top,
                    modifier = Modifier
                        .align(Alignment.Start)
                        .wrapContentSize()
                        .padding(start = 4.dp, top = 6.dp, end = 4.dp, bottom = 1.dp),
                ) {
                    // Text-I47:1279;30:730-Label
                    Text(
                        modifier = Modifier.wrapContentSize(),
                        text = "Domicilio",
                        color = Color(0xff757575),
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Normal,
                        textAlign = TextAlign.Left,
                        overflow = TextOverflow.Ellipsis,
                    )
                }
            }
        }
        // Row-47:1280-Boton_Actualizar
        Row(
            horizontalArrangement = Arrangement.spacedBy(space = 10.dp, alignment = Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .offset(x = 0.dp, y = 719.dp)
                .background(Color(0xff214f70), RoundedCornerShape(8.dp))
                .size(266.dp, 51.dp)
                .padding(horizontal = 16.dp, vertical = 12.5.dp),
        ) {
            // Text-I47:1280;33:1174-BUTTON
            Text(
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .wrapContentSize(),
                text = "Actualizar",
                color = Color(0xffffffff),
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal,
                textAlign = TextAlign.Center,
                overflow = TextOverflow.Ellipsis,
            )
        }
        // Box-47:1281-Numero_Telefonico
        Box(
            contentAlignment = Alignment.TopStart,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .offset(x = 0.dp, y = 420.dp)
                .size(368.dp, 57.dp),
        ) {
            // Box-47:1282-Numero
            Box(
                contentAlignment = Alignment.TopStart,
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .size(368.dp, 57.dp),
            ) {
                // Empty-I47:1282;30:726-Rectangle 7
                Box(
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .background(Color(0xfff3f8ff), RoundedCornerShape(8.dp))
                        .size(368.dp, 57.dp)
                        .border(1.dp, Color(0xff9e9e9e), RoundedCornerShape(8.dp)),
                )
                // Row-I47:1282;30:723-Value
                Row(
                    horizontalArrangement = Arrangement.spacedBy(space = 8.dp, alignment = Alignment.CenterHorizontally),
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .wrapContentHeight()
                        .offset(x = 0.dp, y = 24.dp)
                        .width(368.dp)
                        .padding(horizontal = 16.dp, vertical = 0.dp),
                ) {
                    // Text-I47:1282;30:724-Enter here...
                    Text(
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                            .fillMaxWidth()
                            .padding(start = 0.dp, end = 31.dp),
                        text = "              123-456-7890",
                        color = Color(0xff212121),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Normal,
                        textAlign = TextAlign.Left,
                        overflow = TextOverflow.Ellipsis,
                    )
                }
                // Column-I47:1282;30:727-Label
                Column(
                    verticalArrangement = Arrangement.spacedBy(space = 10.dp, alignment = Alignment.Top),
                    horizontalAlignment = Alignment.Start,
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .wrapContentSize()
                        .offset(x = 12.dp, y = 2.dp),
                ) {
                    // Row-I47:1282;30:729-Frame 89
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(space = 10.dp, alignment = Alignment.Start),
                        verticalAlignment = Alignment.Top,
                        modifier = Modifier
                            .align(Alignment.Start)
                            .wrapContentSize()
                            .padding(start = 4.dp, top = 6.dp, end = 4.dp, bottom = 1.dp),
                    ) {
                        // Text-I47:1282;30:730-Label
                        Text(
                            modifier = Modifier.wrapContentSize(),
                            text = "                    Numero Telefonico",
                            color = Color(0xff757575),
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Normal,
                            textAlign = TextAlign.Left,
                            overflow = TextOverflow.Ellipsis,
                        )
                    }
                }
            }
            // Image-47:1283-Bandera
            Image(
                painter = painterResource(id = R.drawable.bandera),
                contentDescription = null,
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .offset(x = 10.dp, y = 13.dp)
                    .size(41.dp, 34.dp)
                    .clipToBounds(),
            )
        }
        // Box-47:1284-Menú
        Box(
            contentAlignment = Alignment.TopStart,
            modifier = Modifier
                .align(Alignment.TopStart)
                .background(Color(0xff295e84))
                .size(412.dp, 65.dp)
                .border(1.dp, Color(0x00ffffff))
                .clipToBounds(),
        ) {
            // Text-47:1285-Informacion de Perfil
            Text(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .offset(x = 0.dp, y = 16.dp)
                    .size(318.dp, 37.dp),
                text = "Informacion de Perfil",
                color = Color(0xffffffff),
                fontSize = 32.sp,
                fontWeight = FontWeight.Normal,
                textAlign = TextAlign.Center,
                overflow = TextOverflow.Ellipsis,
            )
            // Image-47:1286-Header
            Image(
                painter = painterResource(id = R.drawable.atras),
                contentDescription = null,
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .offset(x = 22.dp, y = 19.dp)
                    .size(33.dp, 30.dp),
            )
        }
    }
}
@Composable
fun DrawerContent(navController: NavHostController, editarperfilViewModel: Editar_PerfilViewModel) {
    val context = LocalContext.current

    Spacer(modifier = Modifier.height(32.dp))

    Box(
        modifier = Modifier.fillMaxWidth(), // Asegura que el Box ocupe todo el ancho disponible
        contentAlignment = Alignment.Center // Centra el contenido dentro del Box
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo_harmony),
            contentDescription = null,
            modifier = Modifier.size(100.dp)
        )
    }

    Text(
        text = "Slappy",
        fontSize = 24.sp,
        textAlign = TextAlign.Center,
        color = Color.White,
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
    )

    HorizontalDivider()

    Spacer(modifier = Modifier.height(4.dp))

    // Ícono de Editar Perfil
    NavigationDrawerItem(
        icon = {
            Icon(
                imageVector = Icons.Filled.PersonOutline,
                contentDescription = "Account",
                tint = Color.White,
                modifier = Modifier
                    .size(27.dp)
            ) },
        label = {
            Text(
                text = context.getString(R.string.editar_perfil),
                fontSize = 16.sp,
                color = Color.White,
                modifier = Modifier.padding(16.dp)
            )
        },
        selected = false,
        onClick = {}
    )

    Spacer(modifier = Modifier.height(4.dp))

    // Ícono de Notificaciones
    NavigationDrawerItem(
        icon = {
            Icon(
                imageVector = Icons.Filled.NotificationsNone,
                contentDescription = "Account",
                tint = Color.White,
                modifier = Modifier
                    .size(27.dp)
            ) },
        label = {
            Text(
                text = context.getString(R.string.notificaciones),
                fontSize = 16.sp,
                color = Color.White,
                modifier = Modifier.padding(16.dp)
            )
        },
        selected = false,
        onClick = {}
    )

    Spacer(modifier = Modifier.height(4.dp))

    // Ícono de Donaciones
    NavigationDrawerItem(
        icon = {
            Icon(
                imageVector = Icons.Outlined.MonetizationOn,
                contentDescription = "Account",
                tint = Color.White,
                modifier = Modifier
                    .size(27.dp)
            ) },
        label = {
            Text(
                text = context.getString(R.string.donaciones),
                fontSize = 16.sp,
                color = Color.White,
                modifier = Modifier.padding(16.dp)
            )
        },
        selected = false,
        onClick = {}
    )

    Spacer(modifier = Modifier.height(4.dp))

    // Ícono de Centro de Ayuda
    NavigationDrawerItem(
        icon = {
            Icon(
                imageVector = Icons.Outlined.HelpOutline,
                contentDescription = "Account",
                tint = Color.White,
                modifier = Modifier
                    .size(27.dp)
            ) },
        label = {
            Text(
                text = context.getString(R.string.notificaciones),
                fontSize = 16.sp,
                color = Color.White,
                modifier = Modifier.padding(16.dp)
            )
        },
        selected = false,
        onClick = {}
    )

    Spacer(modifier = Modifier.height(4.dp))

    // Ícono de Cerrar Sesión
    NavigationDrawerItem(
        icon = {
            Icon(
                imageVector = Icons.Outlined.Logout,
                contentDescription = "Account",
                tint = Color.White,
                modifier = Modifier
                    .size(27.dp)
            ) },
        label = {
            Text(
                text = context.getString(R.string.cerrar_sesi_n),
                fontSize = 16.sp,
                color = Color.White,
                modifier = Modifier.padding(16.dp)
            )
        },
        selected = false,
        onClick = { editarperfilViewModel.cerrarSesion(navController) }
    )
}

@Preview(showBackground = true)
@Composable
fun Editar_PerfilPreview() {
    val navController = rememberNavController()
    Editar_PerfilScreen(navController = navController, editarperfilViewModel = Editar_PerfilViewModel())
}