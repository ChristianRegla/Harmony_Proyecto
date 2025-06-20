package com.example.harmony.ui.profile

import androidx.compose.foundation.Image
import com.example.harmony.R
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.unit.Dp
import androidx. compose. ui. draw. drawBehind
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import com.example.harmony.ui.theme.BlueDark
import kotlinx.coroutines.launch
import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import com.example.harmony.ui.components.DrawerContentComponent
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.compose.rememberNavController
import com.example.harmony.ui.home.TopBar
import android.net.Uri
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.foundation.shape.CircleShape
import com.example.harmony.ui.components.SystemBarStyle
import androidx. compose. material3.Surface
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.window.Dialog


data class MenuItem(
    val iconId: Int,
    val text: String,
    val trailingText: String? = null,
    val onClick: () -> Unit = {}
)

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(navController: NavHostController, profileViewModel: ProfileViewModel) {

    SystemBarStyle(
        statusBarColor = Color.Transparent,
        navigationBarColor = Color.Transparent,
    )

    val context = LocalContext.current
    val viewModel = profileViewModel
    val perfilState = viewModel.perfil.collectAsState()

    // Para los textos y que estén traducidos:
    val header = stringResource(R.string.header_perfil)
    val inicio = stringResource(R.string.inicio)
    val relajacion = stringResource(R.string.relajacion)

    // Para el menu
    val editarPerfil = stringResource(R.string.editar_informacion_de_perfil)
    val notificaciones = stringResource(R.string.notificaciones)
    val seguridad = stringResource(R.string.seguridad)
    val idioma = stringResource(R.string.idioma)
    val tema = stringResource(R.string.tema)
    val ayudaSoporte = stringResource(R.string.ayuda_soporte)
    val contactanos = stringResource(R.string.contactanos)
    val politicaPrivacidad = stringResource(R.string.politica_privacidad)

    var apodo by remember { mutableStateOf("") }

    LaunchedEffect(perfilState.value?.apodo) {
        apodo = perfilState.value?.apodo ?: ""
    }


    // Estado para la URI de la imagen seleccionada localmente
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }

    LaunchedEffect(selectedImageUri) {
        perfilState.value?.let {
            apodo = it.apodo
            selectedImageUri = null
        }
    }

    val scrollState = rememberScrollState()
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
                DrawerContentComponent(navController = navController, drawerActions = profileViewModel, isDrawerOpen = drawerState.isOpen)
            }
        },
        gesturesEnabled = drawerState.isOpen
    ) {
        ConstraintLayout(
            modifier = Modifier.fillMaxSize()
        ) {
            val (scaffold, fondoImg, fondoAzul) = createRefs()

            // Fondo de pantalla
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .constrainAs(fondoImg){
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
            ) {
                Image(
                    painter = painterResource(id = R.drawable.fondo_perfil),
                    contentDescription = "Fondo de pantalla",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }

            Image(
                painter = painterResource(id = R.drawable.fondo1),
                contentDescription = null,
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .size(412.dp, 272.dp)
                    .padding(top = 0.dp)
                    .constrainAs(fondoAzul) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
            )

            Scaffold(
                topBar = {
                    TopBar(
                        onOpenDrawer = {
                            scope.launch {
                                if(drawerState.isClosed) drawerState.open()
                            }
                        },
                        // Este es el título que va en medio de la barra superior
                        title = header,
                        navController = navController,
                        modifier = Modifier.size(20.dp)
                    )
                },
                bottomBar = {
                    NavigationBar(
                        modifier = Modifier.height(80.dp),
                        containerColor = BlueDark
                    ) {
                        NavigationBar(containerColor = BlueDark) {
                            NavigationBarItem(
                                icon = {
                                    Icon(
                                        painterResource(id = R.drawable.home_unselected),
                                        contentDescription = "Home",
                                        tint = Color.White
                                    )
                                },
                                label = {
                                    Text(
                                    inicio,
                                    color = Color.White,
                                    modifier = Modifier.alpha(0.5f)
                                ) },
                                selected = false,
                                onClick = { navController.navigate("main") },
                            )
                            NavigationBarItem(
                                icon = {
                                    Icon(
                                        painterResource(id = R.drawable.relax_unselected),
                                        contentDescription = "Profile",
                                        tint = Color.White
                                    )
                                },
                                label = {
                                    Text(
                                        relajacion,
                                        color = Color.White,
                                        modifier = Modifier.alpha(0.5f)
                                    )
                                },
                                selected = false,
                                onClick = { navController.navigate("relax") }
                            )
                        }
                    }

                },
                containerColor = Color.Transparent, // haz el scaffold transparente
                contentColor = Color.White, // Ajusta el color del contenido si es necesario
                modifier = Modifier
                    .fillMaxSize()
                    .constrainAs(scaffold) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                }
            ) { innerPadding ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .verticalScroll(scrollState)
                        .padding(bottom = 20.dp)
                ) {
                        Spacer(modifier = Modifier.height(100.dp))

                    // Foto de perfil
                        val imagenUrl by profileViewModel.imagenUrl.collectAsState()
                        val defaultImageResId = R.drawable.foto_avatar
                        var showDialog by remember { mutableStateOf(false) }
                        val resId = remember(imagenUrl) {
                        imagenUrl?.let { imageName ->
                            context.resources.getIdentifier(imageName, "drawable", context.packageName)
                            } ?: 0
                        }

                    Image(
                        painter = painterResource(id = if (resId != 0) resId else defaultImageResId),
                        contentDescription = "Foto de perfil",
                        modifier = Modifier
                            .size(120.dp)
                            .clip(CircleShape)
                            .align(Alignment.CenterHorizontally),
                        contentScale = ContentScale.Crop
                    )
                    if (imagenUrl == null) {
                        Text(
                            text = "Elige una foto de perfil",
                            modifier = Modifier.align(Alignment.CenterHorizontally),
                            color = Color(0xffffffff)
                        )
                    }
                            Icon(
                                imageVector = Icons.Filled.Edit,
                                contentDescription = "Editar foto de perfil",
                                tint = Color.White,
                                modifier = Modifier
                                    .size(24.dp)
                                    .align(Alignment.CenterHorizontally)
                                    .clickable { showDialog = true }
                            )
                        // Elegir foto de perfil
                        if (showDialog) {
                            Dialog(onDismissRequest = { showDialog = false }) {
                                Surface(
                                    shape = RoundedCornerShape(8.dp),
                                    color = Color.White,
                                    modifier = Modifier
                                        .padding(16.dp)
                                        .fillMaxWidth()
                                ) {
                                    Row(
                                        modifier = Modifier.padding(16.dp),
                                        horizontalArrangement = Arrangement.SpaceEvenly
                                    ) {
                                        val imageNames = listOf("perfil1", "perfil2", "perfil3", "perfil4")
                                        imageNames.forEach { imageName ->
                                            Image(
                                                painter = painterResource(id = getResourceIdFromName(imageName)),
                                                contentDescription = imageName,
                                                modifier = Modifier
                                                    .size(60.dp)
                                                    .clip(CircleShape)
                                                    .clickable {
                                                        profileViewModel.guardarImagenEnFirestore(
                                                            profileViewModel.auth.currentUser?.uid ?: "",
                                                            imageName
                                                        )
                                                        showDialog = false
                                                    },
                                                contentScale = ContentScale.Crop
                                            )
                                        }
                                    }
                                }
                            }
                        }
                        Spacer(modifier = Modifier.height(14.dp))

                        // Nombre del usuario
                        Text(
                            text = apodo,
                            color = Color(0xffffffff),
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Normal,
                            textAlign = TextAlign.Center,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier
                                .wrapContentHeight()
                                .wrapContentWidth()
                                .align(Alignment.CenterHorizontally)
                        )

                    // Email del usuario
                        val userEmail = profileViewModel.auth.currentUser?.email ?: "Correo no disponible"
                        Text(
                            text = userEmail,
                            color = Color(0xfffafafa),
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Normal,
                            textAlign = TextAlign.Center,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier
                                .wrapContentHeight()
                                .wrapContentWidth()
                                .align(Alignment.CenterHorizontally)
                        )
                        Spacer(modifier = Modifier.height(40.dp))

                    Column(
                        modifier = Modifier
                            .background(Color.Transparent)
                            .fillMaxWidth()
                            .fillMaxHeight()
                            .align(Alignment.CenterHorizontally)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Box(
                                modifier = Modifier
                                    .wrapContentWidth()
                                    .clip(RoundedCornerShape(8.dp)) // Bordes redondeados
                                    .border(1.dp, Color.Transparent, RoundedCornerShape(8.dp))
                            ) {
                                Column(
                                    modifier = Modifier
                                        .wrapContentWidth(),
                                    horizontalAlignment = Alignment.CenterHorizontally

                                ) {
                                    ProfileMenuItem(
                                        item = MenuItem(
                                            R.drawable.ico_editar_perfil,
                                            editarPerfil,
                                        ),
                                        onClick = { navController.navigate("editar_perfil") }
                                    )
                                    ProfileMenuItem(
                                        item = MenuItem(
                                            R.drawable.icononotificaciones,
                                            notificaciones,
                                            "Si"
                                        )
                                    )
                                    ProfileMenuItem(
                                        item = MenuItem(
                                            R.drawable.icono_idioma,
                                            idioma,
                                            "Español"
                                        )
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.height(14.dp))

                            // Grupo de 2
                            Box(
                                modifier = Modifier
                                    .wrapContentWidth()
                                    .clip(RoundedCornerShape(8.dp)) // Bordes redondeados
                                    .border(1.dp, Color.Transparent, RoundedCornerShape(8.dp))
                            ) {
                                Column(
                                    modifier = Modifier
                                        .wrapContentWidth(),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    ProfileMenuItem(
                                        item = MenuItem(
                                            R.drawable.ico_seguridad,
                                            seguridad
                                        )
                                    )
                                    ProfileMenuItem(
                                        item = MenuItem(
                                            R.drawable.ico_tema,
                                            tema,
                                            "Modo Claro"
                                        )
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.height(14.dp))
                            // Grupo de 3
                            Box(
                                modifier = Modifier
                                    .wrapContentWidth()
                                    .clip(RoundedCornerShape(8.dp)) // Bordes redondeados
                                    .border(1.dp, Color.Transparent, RoundedCornerShape(8.dp))
                            ) {
                                Column(
                                    modifier = Modifier
                                        .wrapContentWidth(),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    ProfileMenuItem(
                                        item = MenuItem(
                                            R.drawable.ico_asistecia,
                                            ayudaSoporte
                                        )
                                    )
                                    ProfileMenuItem(
                                        item = MenuItem(
                                            R.drawable.ico_contactanos,
                                            contactanos
                                        ),
                                        onClick = { navController.navigate("contactanos") }
                                    )
                                    ProfileMenuItem(
                                        item = MenuItem(
                                            R.drawable.ico_politicas,
                                            politicaPrivacidad
                                        ),
                                        onClick = { navController.navigate("privacyNotice") }
                                    )
                                }
                                Spacer(modifier = Modifier.height(14.dp))
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ProfileMenuItem(modifier: Modifier = Modifier, item: MenuItem, onClick: () -> Unit = {}) {
    Box(
        contentAlignment = Alignment.TopStart,
        modifier = Modifier
            //.fillMaxWidth()
            //.wrapContentHeight()
            .size(342.dp, 40.dp)
            .clickable(onClick = onClick),
    ) {
        //(Reutilizado para todos los items)
        Box(
            modifier = Modifier
                .advancedShadow(
                    color = Color(0xFF000000),
                    alpha = 0.25f,
                    cornersRadius = 8.dp,
                    shadowBlurRadius = 4.dp,
                    offsetX = 0.dp,
                    offsetY = 1.dp
                )
                .background(Color(0xff295e84))
                .fillMaxWidth()
                .height(40.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = item.iconId),
                        contentDescription = null,
                        contentScale = ContentScale.Fit,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(13.dp))
                    Text(
                        text = item.text,
                        color = Color(0xffffffff),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Normal,
                        textAlign = TextAlign.Left,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.wrapContentWidth()
                    )
                }
                item.trailingText?.let {
                    Text(
                        text = it,
                        color = Color(0xffffffff),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Normal,
                        textAlign = TextAlign.Right,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.wrapContentWidth()
                    )
                }
            }
        }
    }
}
fun Modifier.advancedShadow(
    color: Color,
    alpha: Float,
    cornersRadius: Dp,
    shadowBlurRadius: Dp,
    offsetX: Dp,
    offsetY: Dp
): Modifier = this.then(
    Modifier.drawBehind {
        drawRoundRect(
            color = color.copy(alpha = alpha),
            size = size
        )
    }
)
@Composable
fun getResourceIdFromName(imageName: String): Int {
    val context = LocalContext.current
    return remember(imageName) {
        context.resources.getIdentifier(imageName.substringBeforeLast("."), "drawable", context.packageName)
    }
}

@Preview(showBackground = true)
@Composable
fun ProfilePreview() {
    val navController = rememberNavController()
    val context = LocalContext.current
    ProfileScreen(navController = navController, profileViewModel = ProfileViewModel(ProfileModel(context), context))
}