package com.example.harmony.ui.donation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.EmojiPeople
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.NotificationsNone
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PersonOutline
import androidx.compose.material.icons.outlined.HelpOutline
import androidx.compose.material.icons.outlined.Logout
import androidx.compose.material.icons.outlined.MonetizationOn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.harmony.R
import com.example.harmony.ui.components.DrawerContentComponent
import com.example.harmony.ui.components.HelpCard
import com.example.harmony.ui.helpline.HelplineModel
import com.example.harmony.ui.helpline.HelplineViewModel
import com.example.harmony.ui.home.HomeViewModel
import com.example.harmony.ui.home.TopBar
import com.example.harmony.ui.theme.BlueDark
import com.example.harmony.ui.theme.Bluephone
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DonationScreen(navController: NavHostController, donationViewModel: DonationViewModel) {
    val context = LocalContext.current
    val currentRoute = navController.currentBackStackEntry?.destination?.route

    // Para los textos y que estén traducidos:
    val headerTitle = context.getString(R.string.donacion)
    val relajacion = context.getString(R.string.relajacion)

    // Controlador del Drawer (o sea el menu lateral pues)
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    val inicio = ("Inicio")
    val backgroundColor = Color(0xFF1D1B20)

    // Contenedor del Drawer (menú lateral, lo vuelvo a especificar por si acaso)
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                drawerContainerColor = BlueDark,
                modifier = Modifier
                    .width(250.dp)
            ) {
                DrawerContentComponent(navController = navController, drawerActions = donationViewModel, isDrawerOpen = drawerState.isOpen)
            }
        },
        gesturesEnabled = drawerState.isOpen
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(backgroundColor)
        ) {

            Scaffold(
                // Barra de arriba
                topBar = {
                    TopBar(
                        onOpenDrawer = {
                            scope.launch {
                                if (drawerState.isClosed) drawerState.open()
                            }
                        },
                        // Este es el título que va en medio de la barra superior
                        title = headerTitle,
                        navController = navController,
                        modifier = Modifier.size(56.dp)
                    )
                },
                // Barra de abajo
                bottomBar = {
                    NavigationBar(
                        modifier = Modifier.height(80.dp),
                        containerColor = BlueDark
                    ) {
                        NavigationBarItem(
                            icon = {
                                Icon(
                                    painter = painterResource(id = R.drawable.home_unselected),
                                    contentDescription = "Home",
                                    tint = Color.White
                                )
                            },
                            label = { Text(inicio, color = Color.White, modifier = Modifier.alpha(0.5f)) },
                            selected = false,
                            onClick = { navController.navigate("main") },
                            colors = NavigationBarItemDefaults.colors(
                                indicatorColor = Color.Transparent,
                                selectedIconColor = Color.Transparent,
                                unselectedIconColor = Color.Transparent
                            )
                        )
                        NavigationBarItem(
                            icon = {
                                Icon(
                                    painter = painterResource(id = R.drawable.relax_unselected),
                                    contentDescription = "Relaxing",
                                    tint = Color.White
                                )
                            },
                            label = { Text(relajacion, color = Color.White, modifier = Modifier.alpha(0.5f)) },
                            selected = false,
                            onClick = { navController.navigate("relax") },
                            colors = NavigationBarItemDefaults.colors(
                                indicatorColor = Color.Transparent,
                                selectedIconColor = Color.Transparent,
                                unselectedIconColor = Color.Transparent
                            )
                        )
                    }
                },
                containerColor = Color.Transparent,
                contentColor = Color.White
            ) { padding ->
                ScreenContent(modifier = Modifier.padding(padding))
            }
        }
    }
}

@Composable
fun ScreenContent(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Transparent),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(32.dp))
        // Imagen de perfil (simulada con un icono) y nombre
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(
                imageVector = Icons.Filled.Person, // Reemplaza con tu imagen real
                contentDescription = "Perfil",
                modifier = Modifier.size(64.dp),
                tint = Color.White
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Slappy", fontWeight = FontWeight.Bold, fontSize = 20.sp, color = Color.White)
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Contenedor principal blanco
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .background(Color.White, RoundedCornerShape(16.dp))
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = stringResource(R.string.donaciones), fontWeight = FontWeight.Bold, fontSize = 24.sp, color = Color.Black)
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = stringResource(R.string.gracias_apoyo),
                fontSize = 14.sp,
                color = Color.Gray,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = stringResource(R.string.seleccione_cantidad),
                fontWeight = FontWeight.Medium,
                fontSize = 16.sp,
                color = Color.Black,
                modifier = Modifier.fillMaxWidth().padding(start = 8.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                horizontalArrangement = Arrangement.SpaceAround,
                modifier = Modifier.fillMaxWidth()
            ) {
                DonationButton(amount = "10.00$")
                DonationButton(amount = "50.00$")
                DonationButton(amount = "100.00$")
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = stringResource(R.string.seleccione_metodo_pago),
                fontWeight = FontWeight.Medium,
                fontSize = 16.sp,
                color = Color.Black,
                modifier = Modifier.fillMaxWidth().padding(start = 8.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                horizontalArrangement = Arrangement.SpaceAround,
                modifier = Modifier.fillMaxWidth()
            ) {
                PaymentButton(text = stringResource(R.string.tarjeta_credito))
                PaymentButton(text = stringResource(R.string.paypal))
            }
            Spacer(modifier = Modifier.height(24.dp))
            Button(
                onClick = { /* TODO: Implementar acción de donar */ },
                modifier = Modifier.fillMaxWidth(0.7f),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF123B55),
                    contentColor = Color.White
                )
            ) {
                Text(text = stringResource(R.string.donar), fontSize = 18.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
fun DonationButton(amount: String) {
    Button(
        onClick = { /* TODO: Implementar selección de cantidad */ },
        shape = RoundedCornerShape(15.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFF388BAC),
            contentColor = Color.White
        ),
        modifier = Modifier.size(100.dp, 80.dp).padding(end = 5.dp)
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "$", fontSize = 30.sp, fontWeight = FontWeight.Bold, color = Color.Black)
            Text(text = amount, fontSize = 12.sp)
        }
    }
}

@Composable
fun PaymentButton(text: String) {
    Button(
        onClick = { /* TODO: Implementar selección de método de pago */ },
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFF388BAC),
            contentColor = Color.White),
        modifier = Modifier.padding(8.dp).width(120.dp).height(35.dp)
    ) {
        Text(text = text)
    }
}


@Preview(showBackground = true)
@Composable
fun DonationPreview() {
    val navController = rememberNavController()
    val context = LocalContext.current
    DonationScreen(
        navController = navController,
        donationViewModel = DonationViewModel(
            DonationModel(context), context
        )
    )
}