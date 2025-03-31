package com.example.harmony.ui.helpline

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.harmony.R
import com.example.harmony.ui.home.HomeScreen
import com.example.harmony.ui.home.HomeViewModel
import com.example.harmony.ui.theme.BlueDark
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HelplineScreen (navController: NavHostController, helplineViewModel: HelplineViewModel){
    val context = LocalContext.current
    // Controlador del Drawer
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            DrawerContent(navController)
        }
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Image(
                painter = painterResource(id = R.drawable.background_inicio),
                contentDescription = "Background Image",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )

            Scaffold(
                topBar = {
                    TopAppBar(
                        title = {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    "Home",
                                    color = Color.White,
                                    modifier = Modifier.align(Alignment.CenterVertically)
                                )
                            }
                        },
                        colors = TopAppBarDefaults.topAppBarColors(containerColor = BlueDark),
                        navigationIcon = {
                            IconButton(onClick = { scope.launch { drawerState.open() } }) {
                                Icon(imageVector = Icons.Filled.Home, contentDescription = "Menu", tint = Color.White)
                            }
                        },
                        actions = {
                            IconButton(onClick = { navController.navigate("chat") }) {
                                Icon(imageVector = Icons.Filled.Chat, contentDescription = "Chat", tint = Color.White)
                            }
                        }
                    )
                },
                bottomBar = {
                    NavigationBar(containerColor = BlueDark) {
                        NavigationBarItem(
                            icon = { Icon(imageVector = Icons.Filled.Home, contentDescription = "Home", tint = Color.White) },
                            label = { Text("Home", color = Color.White) },
                            selected = true,
                            onClick = { navController.navigate("main")},
                        )
                        NavigationBarItem(
                            icon = { Icon(imageVector = Icons.Filled.AccountCircle, contentDescription = "Profile", tint = Color.White, modifier = Modifier.alpha(0.5f)) },
                            label = { Text("Profile", color = Color.White, modifier = Modifier.alpha(0.5f)) },
                            selected = false,
                            onClick = { navController.navigate("perfil") }
                        )
                    }
                },
                containerColor = Color.Transparent, // haz el scaffold transparente
                contentColor = Color.White // Ajusta el color del contenido si es necesario
            ) { paddingValues ->
                // Aquí iría el contenido de la pantalla principal
                Text(
                    text = "Contenido principal",
                    modifier = Modifier.padding(paddingValues)
                )
            }
        }
    }

}

@Composable
fun DrawerContent(navController: NavHostController) {
    // Opciones del menú lateral
    Column(modifier = Modifier.padding(16.dp)) {
        TextButton(
            onClick = { navController.navigate("profile") },
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, Color.Gray, RoundedCornerShape(8.dp))
                .padding(16.dp) // Este es el espacio interno
        ) {
            Icon(imageVector = Icons.Filled.AccountCircle, contentDescription = "Profile", modifier = Modifier.padding(end = 8.dp))
            Text(
                text = "Perfil",
                color = Color(0xFF295E84),
                style = MaterialTheme.typography.bodyLarge
            )
        }
        // Espacio entre las opciones
        Spacer(modifier = Modifier.height(16.dp))

        TextButton(
            onClick = { navController.navigate("settings") },
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, Color.Gray, RoundedCornerShape(8.dp))
                .padding(16.dp) // Este es el espacio interno

        ) {
            Text(
                text = "Configuración",
                color = Color(0xFF295E84),
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }

}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    val navController = rememberNavController()
    HelplineScreen(navController = navController, helplineViewModel = HelplineViewModel())
}