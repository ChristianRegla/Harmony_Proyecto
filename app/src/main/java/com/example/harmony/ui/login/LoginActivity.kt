package com.example.harmony.ui.login

import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.animation.AnimatedContentTransitionScope.SlideDirection
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.core.content.ContextCompat
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.harmony.ui.chat.ChatScreen
import com.example.harmony.ui.chat.ChatViewModel
import com.example.harmony.ui.contacto.ContactanosModel
import com.example.harmony.ui.helpline.HelplineModel
import com.example.harmony.ui.helpline.HelplineScreen
import com.example.harmony.ui.helpline.HelplineViewModel
import com.example.harmony.ui.helpline.HelplineViewModelFactory
import com.example.harmony.ui.home.HomeModel
import com.example.harmony.ui.home.HomeScreen
import com.example.harmony.ui.contacto.ContactanosScreen
import com.example.harmony.ui.contacto.ContactanosViewModel
import com.example.harmony.ui.contacto.ContactanosViewModelFactory
import com.example.harmony.ui.donation.DonationModel
import com.example.harmony.ui.donation.DonationScreen
import com.example.harmony.ui.donation.DonationViewModel
import com.example.harmony.ui.donation.DonationViewModelFactory
import com.example.harmony.ui.ejercicios.EjerciciosModel
import com.example.harmony.ui.ejercicios.EjerciciosScreen
import com.example.harmony.ui.ejercicios.EjerciciosViewModel
import com.example.harmony.ui.ejercicios.EjerciciosViewModelFactory
import com.example.harmony.ui.home.HomeViewModel
import com.example.harmony.ui.home.HomeViewModelFactory
import com.example.harmony.ui.privacynotice.PrivacyNoticeModel
import com.example.harmony.ui.privacynotice.PrivacyNoticeScreen
import com.example.harmony.ui.privacynotice.PrivacyNoticeViewModel
import com.example.harmony.ui.privacynotice.PrivacyNoticeViewModelFactory
import com.example.harmony.ui.profile.Editar_PerfilViewModel
import com.example.harmony.ui.profile.Editar_PerfilScreen
import com.example.harmony.ui.profile.ProfileModel
import com.example.harmony.ui.profile.ProfileScreen
import com.example.harmony.ui.profile.ProfileViewModel
import com.example.harmony.ui.profile.ProfileViewModelFactory
import com.example.harmony.ui.registeremotions.RegisterEmotionsModel
import com.example.harmony.ui.registeremotions.RegisterEmotionsViewModel
import com.example.harmony.ui.registeremotions.RegisterEmotionsViewModelFactory
import com.example.harmony.ui.relax.RelaxModel
import com.example.harmony.ui.relax.RelaxScreen
import com.example.harmony.ui.relax.RelaxViewModel
import com.example.harmony.ui.relax.RelaxViewModelFactory
import com.example.harmony.ui.signup.SignUpScreen
import com.google.firebase.auth.FirebaseAuth
import android.Manifest
import android.content.Intent
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import com.example.harmony.notifications.ReminderReceiver
import com.example.harmony.ui.notifications.NotificationsScreen
import com.example.harmony.ui.registeremotions.RegisterEmotionsScreen

@OptIn(ExperimentalAnimationApi::class)
class LoginActivity : ComponentActivity() {

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
        super.onCreate(savedInstanceState)

        handleIntentExtras(intent)

        setContent {
            val navController = rememberNavController()
            // Verificar si el usuario ya está autenticado para pasarlo al main
            val currentUser = FirebaseAuth.getInstance().currentUser
            val startDestination = if (currentUser != null) "main" else "login"

            if (startDestination == "main") {
                val context = LocalContext.current
                val requestPermissionLauncher = rememberLauncherForActivityResult(
                    ActivityResultContracts.RequestPermission()
                ) { isGranted: Boolean ->
                    if (isGranted) {
                        // Permiso concedido
                    } else {
                        // Permiso denegado
                    }
                }

                LaunchedEffect(Unit) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
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
            }

            NavHost(navController = navController, startDestination = startDestination) {
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

            // Navegar al destino de la notificación si existe y el usuario está autenticado
            LaunchedEffect(intent, currentUser) { // Reaccionar a cambios en el intent
                val routeFromNotification = intent.getStringExtra(ReminderReceiver.DESTINATION_ROUTE)
                if (currentUser != null && routeFromNotification != null) {
                    navController.navigate(routeFromNotification) {
                        launchSingleTop = true
                    }
                    // Limpiar el extra del intent para que no se vuelva a procesar
                    intent.removeExtra(ReminderReceiver.DESTINATION_ROUTE)
                }
            }
        }
    }

    // Manejar nuevos intents si la actividad ya está en primer plano
    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        // Actualizar el intent de la actividad para que el LaunchedEffect en setContent lo recoja
        // y procesar los extras del nuevo intent.
        handleIntentExtras(intent)
        setIntent(intent) // Muy importante para que el LaunchedEffect reaccione
    }

    // Función auxiliar para procesar los extras del intent
    private fun handleIntentExtras(intent: Intent?) {
        intent?.getStringExtra(ReminderReceiver.DESTINATION_ROUTE)?.let { route ->
            println("Notification wants to navigate to: $route")
        }
    }
}