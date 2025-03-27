package com.example.harmony.ui.login

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.AnimatedContentTransitionScope.SlideDirection
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.currentRecomposeScope
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.harmony.ui.home.HomeScreen
import com.example.harmony.ui.home.HomeViewModel
import com.example.harmony.ui.perfil.PerfilScreen
import com.example.harmony.ui.perfil.PerfilViewModel
import com.example.harmony.ui.signup.SignUpScreen
import com.google.accompanist.navigation.animation.AnimatedNavHost

@OptIn(ExperimentalAnimationApi::class)
class LoginActivity : ComponentActivity() {

    private val loginViewModel: LoginViewModel by viewModels()
    private val homeViewModel: HomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val navController = rememberNavController()
            AnimatedNavHost(navController = navController, startDestination = "login") {
                composable(
                    "login",
                    enterTransition = { slideIntoContainer(SlideDirection.Right) },
                    exitTransition = { slideOutOfContainer(SlideDirection.Left) },
                    popEnterTransition = { slideIntoContainer(SlideDirection.Left) },
                    popExitTransition = { slideOutOfContainer(SlideDirection.Right) }
                ) {
                    LoginScreen(
                        viewModel = loginViewModel,
                        onNavigateToSignUp = { navController.navigate("signup") },
                        onNavigateToMain = {
                            navController.navigate("main") {
                                popUpTo("login"){ inclusive = true }
                            }
                        }
                    )
                }
                composable(
                    "signup",
                    enterTransition = { slideIntoContainer(SlideDirection.Left) },
                    exitTransition = { slideOutOfContainer(SlideDirection.Right) },
                    popEnterTransition = { slideIntoContainer(SlideDirection.Right) },
                    popExitTransition = { slideOutOfContainer(SlideDirection.Left) }
                ) {
                    SignUpScreen(
                        onNavigateToLogin = { navController.popBackStack() }, // Regresar
                        onNavigateToMain = { navController.navigate("main") }
                    )
                }
                composable("main"){
                    HomeScreen(navController = navController, homeViewModel = homeViewModel)
                }

                composable("perfil"){
                    PerfilScreen(navController = navController, perfilViewModel = PerfilViewModel())
                }
            }
        }
    }
}