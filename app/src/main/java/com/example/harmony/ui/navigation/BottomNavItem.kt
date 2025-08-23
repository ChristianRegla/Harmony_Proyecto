package com.example.harmony.ui.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.example.harmony.R

sealed class BottomNavItem(
    val route: String,
    @StringRes val titleResId: Int,
    @DrawableRes val unselectedIcon: Int,
    @DrawableRes val selectedIcon: Int
) {
    object Home : BottomNavItem(
        route = "main",
        titleResId = R.string.header_menu_principal,
        unselectedIcon = R.drawable.home_unselected,
        selectedIcon = R.drawable.home_selected
    )
    object Relax : BottomNavItem(
        route = "relax",
        titleResId = R.string.relajacion,
        unselectedIcon = R.drawable.relax_unselected,
        selectedIcon = R.drawable.relax_selected
    )
}