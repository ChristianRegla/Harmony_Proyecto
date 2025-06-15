package com.example.harmony.ui.components

import androidx.compose.foundation.layout.height
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.getValue
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.harmony.ui.navigation.BottomNavItem
import com.example.harmony.ui.theme.BlueDark
import com.example.harmony.ui.theme.DarkerPurpleColor

@Composable
fun AppBottomNavigationBar(navController: NavHostController) {
    val items = listOf(
        BottomNavItem.Home,
        BottomNavItem.Relax
    )

    val screensWithoutNavBar = listOf(
        "login",
        "signup",
        "editar_perfil",
        "registerEmotions",
        "ejercicios",
        "chatbot"
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val navigationBarVisible = currentRoute !in screensWithoutNavBar

    if (navigationBarVisible) {
        NavigationBar(
            modifier = Modifier.height(80.dp),
            containerColor = if (currentRoute == "relax") DarkerPurpleColor else BlueDark,
            contentColor = Color.White
        ) {
           items.forEach { item ->
               val isSelected = currentRoute == item.route
               NavigationBarItem(
                   selected = isSelected,
                   onClick = {
                       navController.navigate(item.route) {
                           popUpTo(navController.graph.findStartDestination().id) {
                               saveState = true
                           }
                           launchSingleTop = true
                           restoreState = true
                       }
                   },
                   icon = {
                       val iconRes = if (isSelected) item.selectedIcon else item.unselectedIcon
                       Icon(
                           painter = painterResource(id = iconRes),
                           contentDescription = stringResource(id = item.titleResId)
                       )
                   },
                   label = {
                       Text(
                           text = stringResource(id = item.titleResId),
                           modifier = if (!isSelected) Modifier.alpha(0.6f) else Modifier
                       )
                   },
                   colors = NavigationBarItemDefaults.colors(
                       selectedIconColor = Color.White,
                       unselectedIconColor = Color.White,
                       selectedTextColor = Color.White,
                       unselectedTextColor = Color.White,
                       indicatorColor = Color.Transparent
                   )
               )
           }
        }
    }
}