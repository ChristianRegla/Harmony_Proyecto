package com.example.harmony.ui.signup

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.harmony.ApodoTextField
import com.example.harmony.EmailTextField
import com.example.harmony.PasswordTextField
import com.example.harmony.R
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

@Composable
fun SignUpScreen(
    viewModel: SignUpViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
    onNavigateToLogin: () -> Unit,
    onNavigateToMain: () -> Unit
) {
    val context = LocalContext.current
    var apodo by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val auth = Firebase.auth

    ConstraintLayout(
        modifier = Modifier.fillMaxSize()
    ) {
        val (logo, apodoField, emailField, passwordField, loginButton, googleButton, divider, signUpText, backgroundBox, contentColumn) = createRefs()

        // Box para el fondo
        Box(
            modifier = Modifier
                .fillMaxSize()
                .constrainAs(backgroundBox) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        ) {
            // Imagen de fondo
            Image(
                painter = painterResource(id = R.drawable.background_login_signup),
                contentDescription = stringResource(id = R.string.background_image),
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
            // Imagen de fondo con opacidad
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xBF295E84))
            )
        }

        // Nombre de la app centrado
        Text(
            text = stringResource(id = R.string.app_name),
            fontSize = 64.sp,
            fontFamily = FontFamily(Font(R.font.lobster)),
            color = Color.White,
            modifier = Modifier
                .constrainAs(logo) {
                    top.linkTo(parent.top)
                    bottom.linkTo(apodoField.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
            textAlign = TextAlign.Center
        )

        ApodoTextField(
            apodo = apodo,
            onApodoChange = { apodo = it },
            modifier = Modifier.constrainAs(apodoField) {
                bottom.linkTo(emailField.top, margin = 16.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
        )

        // Campo de entrada para el correo
        EmailTextField(
            email = email,
            onEmailChange = { email = it },
            modifier = Modifier.constrainAs(emailField) {
                bottom.linkTo(passwordField.top, margin = 16.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
        )

        // Campo de entrada para la contraseña
        PasswordTextField(
            password = password,
            onPasswordChange = { password = it },
            modifier = Modifier.constrainAs(passwordField) {
                bottom.linkTo(loginButton.top, margin = 32.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
        )

        // Botón de inicio de sesión
        Button(
            onClick = { viewModel.crearCuenta(apodo, email, password, context) },
            colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(id = R.color.azul_oscuro)),
            shape = RoundedCornerShape(50.dp),
            modifier = Modifier
                .constrainAs(loginButton) {
                    bottom.linkTo(divider.top, margin = 10.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .fillMaxWidth()
                .height(50.dp)
                .padding(start = 32.dp, end = 32.dp)
        ) {
            Text(
                text = stringResource(id = R.string.login), color = Color.White,
            )
        }

        // Linea con O
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .constrainAs(divider) {
                    bottom.linkTo(googleButton.top, margin = 10.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .padding(horizontal = 32.dp)
        ) {
            Divider(
                color = Color.White,
                thickness = 1.dp,
                modifier = Modifier.weight(1f)
            )
            Text(
                text = stringResource(id = R.string.o),
                color = Color.White,
                modifier = Modifier.padding(horizontal = 8.dp)
            )
            Divider(
                color = Color.White,
                thickness = 1.dp,
                modifier = Modifier.weight(1f)
            )
        }

        // Botón de continuar con Google
        Button(
            onClick = { /* TODO: Google Sign-In */ },
            colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(id = R.color.google)),
            shape = RoundedCornerShape(50.dp),
            modifier = Modifier
                .constrainAs(googleButton) {
                    bottom.linkTo(signUpText.top, margin = 15.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .fillMaxWidth()
                .height(50.dp)
                .padding(start = 32.dp, end = 32.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.image_continuar_google_group),
                contentDescription = stringResource(id = R.string.continuar_google),
                tint = Color.Unspecified,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(text = stringResource(id = R.string.continuar_google), color = Color.Black,)
        }

        // Texto de crear una cuenta
        Text(
            text = stringResource(id = R.string.No_tienes_cuenta),
            color = Color.White,
            modifier = Modifier.constrainAs(signUpText) {
                bottom.linkTo(parent.bottom, margin = 15.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
                .clickable { onNavigateToLogin() }
        )
    }
}