package com.example.harmony.ui.main

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.exclude
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScaffoldDefaults
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.harmony.notifications.ReminderReceiver
import com.example.harmony.ui.screens.ChatScreen
import com.example.harmony.ui.viewModel.ChatViewModel
import com.example.harmony.ui.components.AppBottomNavigationBar
import com.example.harmony.ui.model.ContactanosModel
import com.example.harmony.ui.screens.ContactanosScreen
import com.example.harmony.ui.viewModel.ContactanosViewModel
import com.example.harmony.ui.factory.ContactanosViewModelFactory
import com.example.harmony.ui.model.DonationModel
import com.example.harmony.ui.screens.DonationScreen
import com.example.harmony.ui.viewModel.DonationViewModel
import com.example.harmony.ui.factory.DonationViewModelFactory
import com.example.harmony.ui.model.EjerciciosModel
import com.example.harmony.ui.screens.EjerciciosScreen
import com.example.harmony.ui.viewModel.EjerciciosViewModel
import com.example.harmony.ui.factory.EjerciciosViewModelFactory
import com.example.harmony.ui.model.HelplineModel
import com.example.harmony.ui.screens.HelplineScreen
import com.example.harmony.ui.viewModel.HelplineViewModel
import com.example.harmony.ui.factory.HelplineViewModelFactory
import com.example.harmony.ui.model.HomeModel
import com.example.harmony.ui.screens.HomeScreen
import com.example.harmony.ui.viewModel.HomeViewModel
import com.example.harmony.ui.factory.HomeViewModelFactory
import com.example.harmony.ui.screens.LoginScreen
import com.example.harmony.ui.viewModel.LoginViewModel
import com.example.harmony.ui.factory.LoginViewModelFactory
import com.example.harmony.ui.screens.NotificationsScreen
import com.example.harmony.ui.model.PrivacyNoticeModel
import com.example.harmony.ui.screens.PrivacyNoticeScreen
import com.example.harmony.ui.viewModel.PrivacyNoticeViewModel
import com.example.harmony.ui.factory.PrivacyNoticeViewModelFactory
import com.example.harmony.ui.screens.Editar_PerfilScreen
import com.example.harmony.ui.viewModel.Editar_PerfilViewModel
import com.example.harmony.ui.model.ProfileModel
import com.example.harmony.ui.screens.ProfileScreen
import com.example.harmony.ui.viewModel.ProfileViewModel
import com.example.harmony.ui.factory.ProfileViewModelFactory
import com.example.harmony.ui.model.RegisterEmotionsModel
import com.example.harmony.ui.screens.RegisterEmotionsScreen
import com.example.harmony.ui.viewModel.RegisterEmotionsViewModel
import com.example.harmony.ui.factory.RegisterEmotionsViewModelFactory
import com.example.harmony.ui.model.RelaxModel
import com.example.harmony.ui.screens.RelaxScreen
import com.example.harmony.ui.viewModel.RelaxViewModel
import com.example.harmony.ui.factory.RelaxViewModelFactory
import com.example.harmony.ui.screens.SignUpScreen
import com.example.harmony.ui.theme.HarmonyTheme
import com.google.firebase.auth.FirebaseAuth

@OptIn(ExperimentalAnimationApi::class)
class MainActivity : ComponentActivity() {

    private val loginViewModel: LoginViewModel by viewModels {
        LoginViewModelFactory(this)
    }
    private val homeViewModel: HomeViewModel by viewModels {
        HomeViewModelFactory(HomeModel(this), this)
    }
    private val relaxViewModel: RelaxViewModel by viewModels {
        RelaxViewModelFactory(RelaxModel(this), this)
    }
    private val chatViewModel: ChatViewModel by viewModels()
    private val profileViewModel: ProfileViewModel by viewModels {
        ProfileViewModelFactory(ProfileModel(this), this)
    }
    private val editarPerfilViewModel: Editar_PerfilViewModel by viewModels()
    private val contactanosViewModel: ContactanosViewModel by viewModels {
        ContactanosViewModelFactory(ContactanosModel(this), this)
    }
    private val helplineViewModel: HelplineViewModel by viewModels {
        HelplineViewModelFactory(HelplineModel(this), this)
    }
    private val donationViewModel: DonationViewModel by viewModels {
        DonationViewModelFactory(DonationModel(this), this)
    }
    private val privacyNoticeViewModel: PrivacyNoticeViewModel by viewModels {
        PrivacyNoticeViewModelFactory(PrivacyNoticeModel(this), this)
    }
    private val registerEmotionsViewModel: RegisterEmotionsViewModel by viewModels {
        RegisterEmotionsViewModelFactory(RegisterEmotionsModel(this), this)
    }
    private val ejerciciosViewModel: EjerciciosViewModel by viewModels {
        EjerciciosViewModelFactory(EjerciciosModel(this), this)
    }

    @RequiresApi(Build.VERSION_CODES.VANILLA_ICE_CREAM)
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        handleIntentExtras(intent)

        setContent {
            HarmonyTheme {
                val navController = rememberNavController()
                val currentUser = FirebaseAuth.getInstance().currentUser
                val startDestination = if (currentUser != null) "main" else "login"

                if (startDestination == "main") {
                    val context = LocalContext.current
                    val requestPermissionLauncher = rememberLauncherForActivityResult(
                        ActivityResultContracts.RequestPermission()
                    ) { isGranted: Boolean ->
                        if (isGranted) {
                            Log.d("NotificationPermission", "Permiso concedido")
                        } else {
                            Log.d("NotificationPermission", "Permiso denegado")
                        }
                    }

                    LaunchedEffect(Unit) {
                        when {
                            ContextCompat.checkSelfPermission(
                                context,
                                Manifest.permission.POST_NOTIFICATIONS
                            ) == PackageManager.PERMISSION_GRANTED -> {
                            }

                            else -> {
                                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                            }
                        }
                    }
                }

                Scaffold(
                    bottomBar = {
                        AppBottomNavigationBar(navController = navController)
                    },
                    contentWindowInsets = ScaffoldDefaults.contentWindowInsets.exclude(WindowInsets.systemBars)
                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = startDestination,
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable(
                            "login",
                            enterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Right) },
                            exitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Left) },
                            popEnterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Right) },
                            popExitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Left) }
                        ) {
                            LoginScreen(
                                viewModel = loginViewModel,
                                onNavigateToSignUp = { navController.navigate("signup") },
                                onNavigateToMain = {
                                    navController.navigate("main") {
                                        popUpTo("login") { inclusive = true }
                                    }
                                }
                            )
                        }
                        composable(
                            "signup",
                            enterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Left) },
                            exitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Right) },
                            popEnterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Right) },
                            popExitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Left) }
                        ) {
                            SignUpScreen(
                                onNavigateToLogin = { navController.popBackStack() },
                                onNavigateToMain = { navController.navigate("main") }
                            )
                        }
                        composable("main") {
                            HomeScreen(navController = navController, homeViewModel = homeViewModel)
                        }

                        composable("relax") {
                            RelaxScreen(navController = navController, relaxViewModel = relaxViewModel)
                        }

                        composable("chatbot") {
                            ChatScreen(navController = navController, chatViewModel = chatViewModel)
                        }

                        composable("perfil") {
                            ProfileScreen(
                                navController = navController,
                                profileViewModel = profileViewModel
                            )
                        }

                        composable("editar_perfil") {
                            Editar_PerfilScreen(
                                navController = navController,
                                editarPerfilViewModel = editarPerfilViewModel
                            )
                        }
                        composable("contactanos") {
                            ContactanosScreen(
                                navController = navController,
                                contactanosViewModel = contactanosViewModel
                            )
                        }
                        composable("helpline") {
                            HelplineScreen(
                                navController = navController,
                                helplineViewModel = helplineViewModel
                            )
                        }
                        composable("donation") {
                            DonationScreen(
                                navController = navController,
                                donationViewModel = donationViewModel
                            )
                        }
                        composable("privacyNotice") {
                            PrivacyNoticeScreen(
                                navController = navController,
                                privacyNoticeViewModel = privacyNoticeViewModel
                            )
                        }
                        composable("registerEmotions") {
                            RegisterEmotionsScreen(
                                navController = navController,
                                registerEmotionsViewModel = registerEmotionsViewModel
                            )
                        }
                        composable("ejercicios") {
                            EjerciciosScreen(
                                navController = navController,
                                ejerciciosViewModel = ejerciciosViewModel
                            )
                        }
                        composable("notifications") {
                            NotificationsScreen(
                                navController = navController
                            )
                        }
                    }
                }

                // Navegar al destino de la notificación si existe y el usuario está autenticado
                LaunchedEffect(intent, currentUser) {
                    val routeFromNotification =
                        intent.getStringExtra(ReminderReceiver.Companion.DESTINATION_ROUTE)
                    if (currentUser != null && routeFromNotification != null) {
                        navController.navigate(routeFromNotification) {
                            launchSingleTop = true
                        }
                        intent.removeExtra(ReminderReceiver.Companion.DESTINATION_ROUTE)
                    }
                }
            }
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        handleIntentExtras(intent)
        setIntent(intent)
    }

    // Función auxiliar para procesar los extras del intent
    private fun handleIntentExtras(intent: Intent?) {
        intent?.getStringExtra(ReminderReceiver.Companion.DESTINATION_ROUTE)?.let { route ->
            println("Notification wants to navigate to: $route")
        }
    }
}