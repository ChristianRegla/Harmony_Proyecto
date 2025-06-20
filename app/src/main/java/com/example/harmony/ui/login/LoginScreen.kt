package com.example.harmony.ui.login

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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.harmony.EmailTextField
import com.example.harmony.PasswordTextField
import com.example.harmony.R
import com.example.harmony.ui.components.SystemBarStyle
import com.example.harmony.utils.ResultState
import androidx.compose.ui.text.withStyle
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import androidx.credentials.exceptions.GetCredentialException
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.android.libraries.identity.googleid.GetSignInWithGoogleOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.launch
import java.util.UUID

@Composable
fun LoginScreen(
    viewModel: LoginViewModel = viewModel(
        factory = LoginViewModelFactory(LocalContext.current)
    ),
    onNavigateToSignUp: () -> Unit,
    onNavigateToMain: () -> Unit
) {
    // Intento de snackbar
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    val loginState by viewModel.loginState.collectAsState()
    val infoMessage by viewModel.infoMessage.collectAsState()

    val credentialManager = CredentialManager.create(context)

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    SystemBarStyle(
        statusBarColor = Color.Transparent,
        navigationBarColor = Color.Transparent,
    )

    // Observa el estado
    LaunchedEffect(loginState, infoMessage) {
        if (loginState is ResultState.Error) {
            val message = (loginState as ResultState.Error).message
            scope.launch {
                snackbarHostState.showSnackbar(message = message, withDismissAction = true)
                viewModel.resetLoginState()
            }
        }

        infoMessage?.let { message ->
            scope.launch {
                snackbarHostState.showSnackbar(message = message)
                viewModel.onInfoMessageShown()
            }
        }

        if (loginState is ResultState.Success) {
            onNavigateToMain()
            viewModel.resetLoginState()
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        containerColor = Color.Transparent
    ) { contentPadding ->

        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding)
        ) {
            val (logo, emailField, passwordField, loginButton, googleButton, divider, signUpText, backgroundBox, forgotPassword) = createRefs()

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
                        bottom.linkTo(emailField.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    },
                textAlign = TextAlign.Center
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

            Text(
                text = stringResource(id = R.string.forgot_password),
                color = Color.White,
                modifier = Modifier.constrainAs(forgotPassword) {
                    bottom.linkTo(loginButton.top, margin = 10.dp)
                    start.linkTo(parent.start, margin = 32.dp)
                }
            )

            // Botón de inicio de sesión
            Button(
                onClick = { viewModel.iniciarSesion(email, password) },
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(id = R.color.azul_oscuro)
                ),
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
                    modifier = Modifier.weight(1f),
                    thickness = 1.dp,
                    color = Color.White
                )
                Text(
                    text = stringResource(id = R.string.o),
                    color = Color.White,
                    modifier = Modifier.padding(horizontal = 8.dp)
                )
                HorizontalDivider(
                    modifier = Modifier.weight(1f),
                    thickness = 1.dp,
                    color = Color.White
                )
            }

            // Botón de continuar con Google
            Button(
                onClick = {
                    scope.launch {
                        try {
                            val nonce = UUID.randomUUID().toString()
                            // Primero pues se construye la petición
                            val signInWithGoogleOption: GetSignInWithGoogleOption = GetSignInWithGoogleOption.Builder(context.getString(R.string.web_client))
                                .setNonce(nonce)
                                .build()

                            val request: GetCredentialRequest = GetCredentialRequest.Builder()
                                .addCredentialOption(signInWithGoogleOption)
                                .build()

                            // Obtenemos la credencial del sistema
                            val result = credentialManager.getCredential(
                                request = request,
                                context = context
                            )

                            // Luego ya con el resultado lo pasamos al viewmodel
                            val credential = GoogleIdTokenCredential.createFrom(result.credential.data)
                            val firebaseCredential = GoogleAuthProvider.getCredential(credential.idToken, null)
                            viewModel.signInWithGoogleCredential(firebaseCredential)
                        } catch (e: GetCredentialException) {
                            scope.launch {
                                snackbarHostState.showSnackbar(
                                    message = context.getString(R.string.error_google_login_cancelado),
                                    withDismissAction = true
                                )
                            }
                        }
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(id = R.color.google)
                ),
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

            // Texto de crear una cuenta
            Text(
                text = buildAnnotatedString {
                    append(stringResource(id = R.string.No_tienes_cuenta))
                    append(" ")

                    withStyle(
                        style = SpanStyle(
                            textDecoration = TextDecoration.Underline,
                            fontWeight = FontWeight.Bold
                        )
                    ) {
                        append(stringResource(id = R.string.crea_una))
                    }
                },
                color = Color.White,
                modifier = Modifier.constrainAs(signUpText) {
                    bottom.linkTo(parent.bottom, margin = 15.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                    .clickable { onNavigateToSignUp() }
            )
        }
    }
}