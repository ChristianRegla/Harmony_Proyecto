package com.example.harmony.ui.profile

import androidx.compose.foundation.Image
import com.example.harmony.R
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import android.annotation.SuppressLint
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.navigation.compose.rememberNavController
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.harmony.Apodo2TextField
import com.example.harmony.EmailTextField
import com.example.harmony.NombreTextField
import com.example.harmony.NumeroTextField
import com.example.harmony.CiudadDropdown
import com.example.harmony.GeneroDropdown
import com.example.harmony.DomicilioTextField
import com.example.harmony.ui.components.SystemBarStyle
import com.example.harmony.ui.components.TopBarEditar

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Editar_PerfilScreen(
    navController: NavHostController,
    editarPerfilViewModel: Editar_PerfilViewModel
) {
    val header = stringResource(R.string.header_Editarperfil)
    val scrollState = rememberScrollState()
    var nombre by remember { mutableStateOf("") }
    var apodo2 by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var numero by remember { mutableStateOf("") }
    var ciudadSeleccionada by remember { mutableStateOf("") }
    var generoSeleccionado by remember { mutableStateOf("") }
    var domicilio by remember { mutableStateOf("") }

    // Actualizados
    val nombreActual by editarPerfilViewModel.nombre.collectAsState()
    val apodoActual by editarPerfilViewModel.apodo.collectAsState()
    val emailActual by editarPerfilViewModel.email.collectAsState()
    val numeroActual by editarPerfilViewModel.numero.collectAsState()
    val ciudadActual by editarPerfilViewModel.ciudad.collectAsState()
    val generoActual by editarPerfilViewModel.genero.collectAsState()
    val domicilioActual by editarPerfilViewModel.domicilio.collectAsState()

    LaunchedEffect(
        nombreActual,
        apodoActual,
        emailActual,
        numeroActual,
        ciudadActual,
        generoActual,
        domicilioActual
    ) {
        nombre = nombreActual
        apodo2 = apodoActual
        email = emailActual
        numero = numeroActual
        ciudadSeleccionada = ciudadActual
        generoSeleccionado = generoActual
        domicilio = domicilioActual
    }
    SystemBarStyle(
        statusBarColor = Color.Transparent,
        navigationBarColor = Color.Transparent,
    )

    ConstraintLayout(
        modifier = Modifier.fillMaxSize()
    ) {
        val (scaffold, fondoImage) = createRefs()
        Box(
            modifier = Modifier
                .fillMaxSize()
                .constrainAs(fondoImage) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        ) {
            Image(
                painter = painterResource(id = R.drawable.background_editar_perfil_info), // Cambiarlo al original
                contentDescription = "Fondo de pantalla",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }
        Scaffold(
            topBar = {
                TopBarEditar(
                    onBackClick = { navController.popBackStack() },
                    // Este es el título que va en medio de la barra superior
                    title = header,
                    modifier = Modifier.size(20.dp)
                )
            },
            containerColor = Color.Transparent,
            contentColor = Color.White,
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
                    .padding(bottom = 10.dp)
                    .padding(top = 20.dp)
            ) {
                Column(
                    modifier = Modifier
                        .background(Color.Transparent)
                        .align(Alignment.CenterHorizontally)

                ) {
                    Spacer(modifier = Modifier.height(10.dp))
                    NombreTextField(
                        nombre = nombre,
                        onNombreChange = { nombre = it },
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                    )
                    Spacer(modifier = Modifier.height(20.dp))

                    Apodo2TextField(
                        apodo2 = apodo2,
                        onApodo2Change = { apodo2 = it },
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                    )
                    Spacer(modifier = Modifier.height(20.dp))

                    EmailTextField(
                        email = email,
                        onEmailChange = { },
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                    )
                    Spacer(modifier = Modifier.height(20.dp))

                    NumeroTextField(
                        numero = numero,
                        onNumeroChange = { numero = it },
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                    ) {
                        CiudadDropdown(
                            ciudadSeleccionada = ciudadSeleccionada,
                            onCiudadSeleccionada = { ciudadSeleccionada = it },
                            modifier = Modifier.width(200.dp) // Ancho ajustado
                        )

                        GeneroDropdown(
                            generoSeleccionado = generoSeleccionado,
                            onGeneroSeleccionado = { generoSeleccionado = it },
                            modifier = Modifier.width(250.dp) // Ancho ajustado
                        )
                    }
                    Spacer(modifier = Modifier.height(20.dp))
                    DomicilioTextField(
                        domicilio = domicilio,
                        onDomicilioChange = { domicilio = it },
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    // Botón de Actualizar
                    Button(
                        onClick = {
                            editarPerfilViewModel.actualizarDatos(
                                nombre = nombre,
                                apodo = apodo2,
                                numero = numero,
                                ciudad = ciudadSeleccionada,
                                genero = generoSeleccionado,
                                domicilio = domicilio
                            )
                            editarPerfilViewModel.cargarDatosUsuario()

                        },
                        colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.azul_oscuro)),
                        shape = RoundedCornerShape(50.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                            .padding(start = 32.dp, end = 32.dp)
                    ) {
                        androidx.compose.material.Text(
                            text = stringResource(id = R.string.actualizar), color = Color.White,
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun Editar_PerfilPreview() {
    val navController = rememberNavController()
    Editar_PerfilScreen(
        navController = navController,
        editarPerfilViewModel = Editar_PerfilViewModel()
    )
}