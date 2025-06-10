package com.example.harmony.ui.privacynotice

import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.harmony.R
import com.example.harmony.ui.components.Background_inicio
import com.example.harmony.ui.components.DrawerContentComponent
import com.example.harmony.ui.components.SystemBarStyle
import com.example.harmony.ui.home.TopBar
import com.example.harmony.ui.theme.BlueDark
import com.example.harmony.ui.theme.HarmonyTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PrivacyNoticeScreen(navController: NavHostController, privacyNoticeViewModel: PrivacyNoticeViewModel) {

    SystemBarStyle(
        statusBarColor = Color.Transparent,
        navigationBarColor = Color.Transparent,
    )

    val headerTitle = ""
    val relajacion = stringResource(R.string.relajacion)
    val inicio = stringResource(R.string.inicio)


    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val screenHeight = configuration.screenHeightDp.dp

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet (
                drawerContainerColor = BlueDark,
                modifier = Modifier
                    .width(250.dp)
            ){
                DrawerContentComponent(navController = navController, drawerActions = privacyNoticeViewModel,isDrawerOpen = drawerState.isOpen)
            }
        },
        gesturesEnabled = drawerState.isOpen
    ) {
        Scaffold(
            // Barra de arriba
            topBar = {
                TopBar(
                    onOpenDrawer = {
                        scope.launch {
                            if(drawerState.isClosed) drawerState.open()
                        }
                    },
                    // Este es el título que va en medio de la barra superior
                    title = headerTitle,
                    navController = navController,
                    modifier = Modifier.wrapContentHeight()
                )
            },
            // Barra de abajo
            bottomBar = {
                NavigationBar(
                    modifier = Modifier.height(80.dp),
                    containerColor = BlueDark
                ) {
                    NavigationBarItem(
                        icon = { Icon(painterResource(id = R.drawable.home_unselected),
                            contentDescription = "Home",
                            tint = Color.White
                        ) },
                        label = { Text(
                            inicio,
                            color = Color.White,
                            modifier = Modifier.alpha(0.5f)
                        ) },
                        selected = true,
                        onClick = { navController.navigate("main") },
                        colors = NavigationBarItemDefaults.colors(
                            indicatorColor = Color.Transparent,
                            selectedIconColor = Color.Transparent,
                            unselectedIconColor = Color.Transparent
                        )
                    )
                    NavigationBarItem(
                        icon = { Icon(
                            painter = painterResource(id = R.drawable.relax_unselected),
                            contentDescription = "Relaxing",
                            tint = Color.White
                        ) },
                        label = { Text(relajacion, color = Color.White, modifier = Modifier.alpha(0.5f)) },
                        selected = false,
                        onClick = { navController.navigate("relax") },
                        colors = NavigationBarItemDefaults.colors(
                            indicatorColor = Color.Transparent,
                            selectedIconColor = Color.Transparent,
                            unselectedIconColor = Color.Transparent
                        )
                    )
                }
            },
            containerColor = Color.Transparent,
            contentColor = Color.White

        ) { scaffoldPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(scaffoldPadding)
            ) {
                Background_inicio(modifier = Modifier.matchParentSize())

                Surface(
                    modifier = Modifier
                        .fillMaxWidth(0.91f)  // 91% del ancho
                        .fillMaxHeight(0.93f) // 93% del alto
                        .align(Alignment.Center),
                    shape = RoundedCornerShape(16.dp),
                    color = Color(0xFFFAFAFA)
                ) {
                    Column(
                        modifier = Modifier
                            .padding(
                                horizontal = screenWidth * 0.04f,
                                vertical = screenHeight * 0.03f
                            )
                            .verticalScroll(rememberScrollState())
                    ) {
                        Text(
                            text = stringResource(R.string.header_privacidad),
                            style = MaterialTheme.typography.headlineLarge,
                            textAlign = TextAlign.Justify,
                            color = Color.Black,
                            modifier = Modifier.padding(
                                horizontal = screenWidth *0.02f,
                                vertical = screenHeight * 0.01f
                            )
                        )

                        Text(
                            text = stringResource(R.string.texto_privacidad),
                            style = MaterialTheme.typography.bodySmall.copy(
                                lineHeight = 26.sp
                            ),
                            textAlign = TextAlign.Justify,
                            color = Color.Black,
                            modifier = Modifier.padding(
                                horizontal = screenWidth * 0.02f,
                                vertical = screenHeight * 0.02f
                            )
                        )
                    }
                }
            }
        }

    }
}

@Preview (showBackground = true)
@Composable
fun PrivacyNoticeScreenPreview () {
    HarmonyTheme {
        val navController = rememberNavController()
        val context = LocalContext.current
        PrivacyNoticeScreen (
            navController = navController,
            privacyNoticeViewModel = PrivacyNoticeViewModel(
                PrivacyNoticeModel(context),
                context
            )
        )
    }
}
