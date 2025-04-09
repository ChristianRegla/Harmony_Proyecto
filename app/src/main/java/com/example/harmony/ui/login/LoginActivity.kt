package com.example.harmony.ui.login

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.AnimatedContentTransitionScope.SlideDirection
import androidx.compose.animation.ExperimentalAnimationApi
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
import com.example.harmony.ui.home.HomeViewModel
import com.example.harmony.ui.home.HomeViewModelFactory
import com.example.harmony.ui.profile.Editar_PerfilViewModel
import com.example.harmony.ui.profile.Editar_PerfilScreen
import com.example.harmony.ui.profile.ProfileModel
import com.example.harmony.ui.profile.ProfileScreen
import com.example.harmony.ui.profile.ProfileViewModel
import com.example.harmony.ui.profile.ProfileViewModelFactory
import com.example.harmony.ui.relax.RelaxModel
import com.example.harmony.ui.relax.RelaxScreen
import com.example.harmony.ui.relax.RelaxViewModel
import com.example.harmony.ui.relax.RelaxViewModelFactory
import com.example.harmony.ui.signup.SignUpScreen
import com.google.accompanist.navigation.animation.AnimatedNavHost

@OptIn(ExperimentalAnimationApi::class)
class LoginActivity : ComponentActivity() {

    private val loginViewModel: LoginViewModel by viewModels()
    private val homeViewModel: HomeViewModel by viewModels {
        HomeViewModelFactory(HomeModel(this), this)
    }
    private val relaxViewModel: RelaxViewModel by viewModels() {
        RelaxViewModelFactory(RelaxModel(this), this)
    }
    private val ChatViewModel: ChatViewModel by viewModels()
    private val profileViewModel: ProfileViewModel by viewModels() {
        ProfileViewModelFactory(ProfileModel(this), this)
    }
    private val Editar_PerfilViewModel: Editar_PerfilViewModel by viewModels()
    private val ContactanosViewModel: ContactanosViewModel by viewModels() {
        ContactanosViewModelFactory(ContactanosModel(this), this)
    }
    private val HelplineViewModel: HelplineViewModel by viewModels() {
        HelplineViewModelFactory(HelplineModel(this), this)
    }


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
                                popUpTo("login") { inclusive = true }
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
                composable("main") {
                    HomeScreen(navController = navController, homeViewModel = homeViewModel)
                }

                composable("relax") {
                    RelaxScreen(navController = navController, relaxViewModel = relaxViewModel)
                }

                composable("chatbot") {
                    ChatScreen(navController = navController, ChatViewModel = ChatViewModel)
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
                        Editar_PerfilViewModel = Editar_PerfilViewModel
                    )
                }
                composable("contactanos") {
                    ContactanosScreen(
                        navController = navController,
                        contactanosViewModel = ContactanosViewModel
                    )
                }
                composable("helpline") {
                        HelplineScreen(
                            navController = navController,
                            helplineViewModel = HelplineViewModel
                        )
                    }
                }
            }
        }
    }
