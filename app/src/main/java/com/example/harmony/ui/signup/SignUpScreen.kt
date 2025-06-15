package com.example.harmony.ui.signup

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.harmony.ApodoTextField
import com.example.harmony.EmailTextField
import com.example.harmony.PasswordTextField
import com.example.harmony.R
import com.example.harmony.ui.components.SystemBarStyle
import com.example.harmony.utils.ResultState
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SignUpScreen(
    viewModel: SignUpViewModel = viewModel(
        factory = SignUpViewModelFactory(LocalContext.current)
    ),
    onNavigateToLogin: () -> Unit,
    onNavigateToMain: () -> Unit
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    var apodo by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val signUpState by viewModel.signUpState.collectAsState()

    LaunchedEffect(signUpState) {
        when (val state = signUpState) {
            is ResultState.Error -> {
                val message = state.message.asString(context)
                scope.launch {
                    snackbarHostState.showSnackbar(message = message, withDismissAction = true)
                    viewModel.resetSignUpState()
                }
            }
            is ResultState.Success -> {
                onNavigateToMain()
                viewModel.resetSignUpState()
            }
            is ResultState.Loading -> {}
            is ResultState.Idle -> {}
        }
    }

    SystemBarStyle()

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        containerColor = Color.Transparent
    ) { _ ->
        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
        ) {
            val (logo, apodoField, emailField, passwordField, loginButton, googleButton, divider, signUpText, backgroundBox) = createRefs()

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
                Image(
                    painter = painterResource(id = R.drawable.background_login_signup),
                    contentDescription = stringResource(id = R.string.background_image),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(0xBF295E84))
                )
            }

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

            EmailTextField(
                email = email,
                onEmailChange = { email = it },
                modifier = Modifier.constrainAs(emailField) {
                    bottom.linkTo(passwordField.top, margin = 16.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
            )

            PasswordTextField(
                password = password,
                onPasswordChange = { password = it },
                modifier = Modifier.constrainAs(passwordField) {
                    bottom.linkTo(loginButton.top, margin = 32.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
            )

            Button(
                onClick = { viewModel.crearCuenta(apodo, email, password) },
                colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.azul_oscuro)),
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
                    text = stringResource(id = R.string.sign_up), color = Color.White,
                )
            }

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
                HorizontalDivider(
                    color = Color.White,
                    thickness = 1.dp,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = stringResource(id = R.string.o),
                    color = Color.White,
                    modifier = Modifier.padding(horizontal = 8.dp)
                )
                HorizontalDivider(
                    color = Color.White,
                    thickness = 1.dp,
                    modifier = Modifier.weight(1f)
                )
            }

            Button(
                onClick = { /* TODO: Google Sign-In */ },
                colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.google)),
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
                Text(text = stringResource(id = R.string.continuar_google), color = Color.Black)
            }

            Text(
                text = buildAnnotatedString {
                    append(stringResource(id = R.string.Ya_tienes_cuenta))
                    append(" ")

                    withStyle(
                        style = SpanStyle(
                            textDecoration = TextDecoration.Underline,
                            fontWeight = FontWeight.Bold
                        )
                    ) {
                        append(stringResource(id = R.string.inicia_sesion))
                    }
                },
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
}