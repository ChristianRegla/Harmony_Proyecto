package com.example.harmony.ui.common

import androidx.navigation.NavHostController

interface DrawerActions {

    fun cargarApodoEnDrawerContent()

    fun cerrarSesion(navController: NavHostController)
}