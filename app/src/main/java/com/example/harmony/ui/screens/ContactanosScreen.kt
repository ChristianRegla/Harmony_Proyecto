package com.example.harmony.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.harmony.R
import com.example.harmony.ui.components.DrawerContentComponent
import com.example.harmony.ui.components.SystemBarStyle
import com.example.harmony.ui.theme.BlueDark
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.compose.rememberNavController
import com.example.harmony.ui.components.TopBarEditar
import com.example.harmony.ui.components.EmailContainer
import com.example.harmony.ui.components.SocialNetworkContainer
import com.example.harmony.ui.model.ContactanosModel
import com.example.harmony.ui.viewModel.ContactanosViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactanosScreen(navController: NavHostController, contactanosViewModel: ContactanosViewModel) {
    val relajacion = stringResource(R.string.relajacion)
    val home = stringResource(R.string.inicio)
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

    SystemBarStyle()
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet (
                drawerContainerColor = BlueDark,
                modifier = Modifier
                    .width(250.dp)
            ){
                DrawerContentComponent(navController = navController, drawerActions = contactanosViewModel, isDrawerOpen = drawerState.isOpen)
            }
        },
        gesturesEnabled = drawerState.isOpen
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Image(
                painter = painterResource(id = R.drawable.background_inicio),
                contentDescription = "Background Image",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
            Scaffold(
                // Barra de arriba
                topBar = {
                    TopBarEditar(
                        onBackClick = { navController.popBackStack() },
                        title = "",
                        modifier = Modifier.wrapContentHeight()
                    )
                },
                containerColor = Color.Transparent,
                contentColor = Color.White
            ) { innerpadding ->
                ContactContent(modifier = Modifier
                    .fillMaxSize()
                    .padding(innerpadding),
                )
            }
        }
    }
}

@Composable
fun ContactContent(modifier: Modifier = Modifier) {
    val titulo = stringResource(R.string.contactanos)
    val seguidores1 = stringResource(R.string.Seguidores1)
    val seguidores2 = stringResource(R.string.Seguidores2)
    val seguidores3 = stringResource(R.string.Seguidores3)

    ConstraintLayout(
        modifier = modifier
            .padding(16.dp)
    ) {
        val (titleText, emailContainer, instagramContainer, telegramContainer, facebookContainer) = createRefs()

        Text(
            text = titulo,
            color = Color.White,
            fontSize = 42.sp,
            fontWeight = FontWeight.Normal,
            modifier = Modifier
                .constrainAs(titleText) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        )

        EmailContainer(
            imageResId = R.drawable.mail,
            title = "Email",
            description = "Nuestro equipo está en línea",
            schedule = "Lun-Vie • 9-17",
            modifier = Modifier
                .constrainAs(emailContainer) {
                    top.linkTo(titleText.bottom, margin = 16.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
            }
        )
        SocialNetworkContainer(
            imageResId = R.drawable.instagram,
            name = "Instagram",
            followers = seguidores1,
            link = "https://www.instagram.com/christian_slappy?igsh=MXhtMm02ejRxYjUxbg==",
            modifier = Modifier.constrainAs(instagramContainer) {
                top.linkTo(emailContainer.bottom, margin = 16.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
        )
        SocialNetworkContainer(
            imageResId = R.drawable.telegram,
            name = "Telegram",
            followers = seguidores2,
            link = "https://info.jalisco.gob.mx/instituciones/hospital-psiquiatrico-de-jalisco-estancia-prolongada",
            modifier = Modifier.constrainAs(telegramContainer) {
                top.linkTo(instagramContainer.bottom, margin = 16.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
        )
        SocialNetworkContainer(
            imageResId = R.drawable.facebook,
            name = "Facebook",
            followers = seguidores3,
            link = "https://www.facebook.com/share/1Mk4xNzHmx/",
            modifier = Modifier.constrainAs(facebookContainer) {
                top.linkTo(telegramContainer.bottom, margin = 16.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ContactanosPreview() {
    val navController = rememberNavController()
    val context = LocalContext.current
    ContactanosScreen(navController = navController, contactanosViewModel = ContactanosViewModel(
        ContactanosModel(context),
        context
    )
    )
}