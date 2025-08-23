package com.example.harmony.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.harmony.R
import com.example.harmony.ui.components.DrawerContentComponent
import com.example.harmony.ui.components.HelpCard
import com.example.harmony.ui.components.TopBar
import com.example.harmony.ui.model.HelplineModel
import com.example.harmony.ui.viewModel.HelplineViewModel
import com.example.harmony.ui.theme.BlueDark
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HelplineScreen(navController: NavHostController, helplineViewModel: HelplineViewModel) {
    val context = LocalContext.current

    // Para los textos y que estén traducidos:
    val headerTitle = context.getString(R.string.header_linea_ayuda)

    // Controlador del Drawer (o sea el menu lateral pues)
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    val inicio = ("Inicio")
    val backgroundColor = Color(0xFF1D1B20)

    // Contenedor del Drawer (menú lateral, lo vuelvo a especificar por si acaso)
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet (
                drawerContainerColor = BlueDark,
                modifier = Modifier
                    .width(250.dp)
            ){
                DrawerContentComponent(
                    navController = navController,
                    drawerActions = helplineViewModel,
                    isDrawerOpen = drawerState.isOpen,
                    onCloseDrawer = {
                        scope.launch {
                            drawerState.close()
                        }
                    }
                )
            }
        },
        gesturesEnabled = drawerState.isOpen
    ) {
        Box(
            modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)) {

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
                containerColor = Color.Transparent,
                contentColor = Color.White
            ) { padding ->
                ScreenContent(modifier = Modifier.padding(padding))
                HelpScreen(helplineViewModel = helplineViewModel)
            }
        }
    }
}

@Composable
fun ScreenContent(modifier: Modifier = Modifier) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(30.dp)
            .background(Color.Transparent)
    )

}

@Composable
fun HelpScreen(helplineViewModel: HelplineViewModel) {
    Box(modifier = Modifier.fillMaxSize()) {
        // Contenido principal
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 120.dp, start = 16.dp, end = 16.dp, bottom = 16.dp),
        ) {
            Text(
                text = stringResource(id = R.string.estas_en_crisis),
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = colorResource(id = R.color.blue_medium),
                modifier = Modifier.padding(bottom = 10.dp)
            )
            Text(
                text = stringResource(id = R.string.comun_cate_con_amigos),
                fontSize = 16.sp,
                color = Color.White,
                modifier = Modifier.padding(bottom = 10.dp)
            )
            Text(
                text = stringResource(id = R.string.recuerda_no_estas_solo),
                fontSize = 16.sp,
                color = Color.White,
                modifier = Modifier.padding(bottom = 20.dp)
            )

            HelpCard(
                imageRes = R.drawable.drawable_image_1,
                title = stringResource(id = R.string.linea_de_la_vida),
                phone = stringResource(id = R.string._800_911_2000),
                description = stringResource(id = R.string.cobertura_nacional_disponible_24_7),
                onCallClick = { phoneNumber -> helplineViewModel.callPhoneNumber(phoneNumber) }
            )
            Spacer(modifier = Modifier.height(16.dp))
            HelpCard(
                imageRes = R.drawable.drawable_image_1,
                title = stringResource(id = R.string.linea_de_la_vida),
                phone = stringResource(id = R.string._075),
                description = stringResource(id = R.string.cobertura_en_jalisco_disponible_24_7),
                onCallClick = { phoneNumber -> helplineViewModel.callPhoneNumber(phoneNumber) }
            )
            Spacer(modifier = Modifier.height(16.dp))
            HelpCard(
                imageRes = R.drawable.drawable_image_3,
                title = stringResource(id = R.string.emergencias),
                phone = stringResource(id = R.string._911),
                description = stringResource(id = R.string.cobertura_nacional_disponible_24_7),
                onCallClick = { phoneNumber -> helplineViewModel.callPhoneNumber(phoneNumber) }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HelplinePreview() {
    val navController = rememberNavController()
    val context = LocalContext.current
    HelplineScreen(navController = navController, helplineViewModel = HelplineViewModel(
        HelplineModel(context),
        context
    )
    )
}