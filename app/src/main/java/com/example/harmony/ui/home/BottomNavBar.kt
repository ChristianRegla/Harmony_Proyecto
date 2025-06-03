package com.example.harmony.ui.home

import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController

@Composable
fun BottomNavBar(
    navController: NavController,
    onTitleChange: (String) -> Unit
) {

    val currentDestination = navController.currentDestination?.route

    NavigationBar(containerColor = Color.Transparent) {
        NavigationBarItem(
            selected = currentDestination == "home",
            onClick = {
                navController.navigate("home")
                onTitleChange("Inicio")
            },
            icon = {
                Icon(
                    Icons.Default.Home,
                    contentDescription = "Inicio",
                    tint = if(currentDestination == "home") Color.Magenta else Color.Gray)
                   },
            label = {
                Text(
                    "Inicio",
                    color = if(currentDestination == "home") Color.Magenta else Color.Gray
                )
            },
            colors = NavigationBarItemDefaults.colors(
                indicatorColor = Color.Transparent, // Cambia el color de fondo a transparente
                selectedIconColor = Color.Transparent,
                unselectedIconColor = Color.Transparent
            )
        )
        NavigationBarItem(
            selected = currentDestination == "perfil",
            onClick = {
                navController.navigate("perfil")
                onTitleChange("Perfil")
            },
            icon = {
                Icon(
                    Icons.Default.Person,
                    contentDescription = "Perfil",
                )
                   },
            label = {
                Text(
                    "Perfil",
                )
            },
            colors = NavigationBarItemDefaults.colors(
                indicatorColor = Color.Transparent, // Cambia el color de fondo a transparente
                selectedIconColor = Color.Transparent,
                unselectedIconColor = Color.Transparent
            )
        )
    }
}
