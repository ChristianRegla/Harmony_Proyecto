package com.example.harmony.ui.home

import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.navigation.NavController

@Composable
fun BottomNavBar(
    navController: NavController,
    onTitleChange: (String) -> Unit
) {
    NavigationBar {
        NavigationBarItem(
            selected = false,
            onClick = {
                navController.navigate("home")
                onTitleChange("Inicio")
            },
            icon = { Icon(Icons.Default.Home, contentDescription = "Inicio") },
            label = { Text("Inicio") }
        )
        NavigationBarItem(
            selected = false,
            onClick = {
                navController.navigate("perfil")
                onTitleChange("Perfil")
            },
            icon = { Icon(Icons.Default.Person, contentDescription = "Perfil") },
            label = { Text("Perfil") }
        )
        NavigationBarItem(
            selected = false,
            onClick = {
                navController.navigate("settings")
                onTitleChange("Configuración")
            },
            icon = { Icon(Icons.Default.Settings, contentDescription = "Configuración") },
            label = { Text("Config") }
        )
    }
}
