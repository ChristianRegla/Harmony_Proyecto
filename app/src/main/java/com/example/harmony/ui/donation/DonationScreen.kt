package com.example.harmony.ui.donation

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.harmony.R
import com.example.harmony.ui.components.DrawerContentComponent
import com.example.harmony.ui.home.TopBar
import com.example.harmony.ui.theme.BlueDark

import kotlinx.coroutines.launch


private const val PAYPAL_DONATION_URL = "https://www.paypal.com/paypalme/arturoyaelposadas"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DonationScreen(navController: NavHostController, donationViewModel: DonationViewModel) {
    val headerTitle = stringResource(R.string.donacion)
    val relajacion = stringResource(R.string.relajacion)
    val inicio = ("Inicio")

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    val backgroundColor = Color(0xFF1D1B20)

    var showPayPalWebView by remember { mutableStateOf(false) }

    if (showPayPalWebView) {
        BackHandler {
            showPayPalWebView = false
        }
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                drawerContainerColor = BlueDark,
                modifier = Modifier.width(250.dp)
            ) {
                DrawerContentComponent(navController = navController, drawerActions = donationViewModel, isDrawerOpen = drawerState.isOpen)
            }
        },
        gesturesEnabled = drawerState.isOpen && !showPayPalWebView
    ) {
        if (showPayPalWebView) {
            PayPalWebViewInternal(
                donationUrl = PAYPAL_DONATION_URL,
                onClose = { showPayPalWebView = false }
            )
        } else {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(backgroundColor)
            ) {
                Scaffold(
                    topBar = {
                        TopBar(
                            onOpenDrawer = {
                                scope.launch {
                                    if (drawerState.isClosed) drawerState.open()
                                }
                            },
                            title = headerTitle,
                            navController = navController,
                        )
                    },
                    containerColor = Color.Transparent,
                    contentColor = Color.White
                ) { paddingValues ->
                    ScreenContent(
                        modifier = Modifier.padding(paddingValues),
                        onPayPalClick = { showPayPalWebView = true }
                    )
                }
            }
        }
    }
}

@Composable
fun ScreenContent(modifier: Modifier = Modifier, onPayPalClick: () -> Unit) {
    Column(
        modifier = modifier
            .fillMaxSize()
        ,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(32.dp))
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(
                imageVector = Icons.Filled.Person,
                contentDescription = "Perfil",
                modifier = Modifier.size(64.dp),
                tint = Color.White
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Slappy", fontWeight = FontWeight.Bold, fontSize = 20.sp, color = Color.White)
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Contenedor principal blanco
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .background(Color.White, RoundedCornerShape(16.dp))
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = stringResource(R.string.donaciones), fontWeight = FontWeight.Bold, fontSize = 24.sp, color = Color.Black)
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = stringResource(R.string.gracias_apoyo),
                fontSize = 14.sp,
                color = Color.Gray,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = stringResource(R.string.seleccione_cantidad),
                fontWeight = FontWeight.Medium,
                fontSize = 16.sp,
                color = Color.Black,
                modifier = Modifier.fillMaxWidth().padding(start = 8.dp) // Alineado a la izquierda dentro del contenedor blanco
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp), // Espacio entre botones de donación
                modifier = Modifier.fillMaxWidth()
            ) {
                DonationButton(amount = "10.00$", modifier = Modifier.weight(1f)) { /* TODO: Lógica de selección de cantidad 10 */ }
                DonationButton(amount = "50.00$", modifier = Modifier.weight(1f)) { /* TODO: Lógica de selección de cantidad 50 */ }
                DonationButton(amount = "100.00$", modifier = Modifier.weight(1f)) { /* TODO: Lógica de selección de cantidad 100 */ }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = stringResource(R.string.seleccione_metodo_pago),
                fontWeight = FontWeight.Medium,
                fontSize = 16.sp,
                color = Color.Black,
                modifier = Modifier.fillMaxWidth().padding(start = 8.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                PaymentButton(
                    text = stringResource(R.string.tarjeta_credito),
                    onClick = { /* TODO: Implementar lógica para tarjeta de crédito */ },
                    modifier = Modifier
                        .weight(1f)
                        .height(40.dp)

                )
                PaymentButton(
                    text = stringResource(R.string.paypal),
                    onClick = { onPayPalClick() },
                    modifier = Modifier
                        .weight(1f)
                        .height(40.dp)
                )
            }
            Spacer(modifier = Modifier.height(24.dp))
            Button(
                onClick = { /* TODO: Implementar acción de donar (puede depender de la selección) */ },
                modifier = Modifier.fillMaxWidth(0.7f),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF123B55),
                    contentColor = Color.White
                )
            ) {
                Text(text = stringResource(R.string.donar), fontSize = 18.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
fun DonationButton(amount: String, modifier: Modifier = Modifier, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(15.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFF388BAC),
            contentColor = Color.White
        ),
        modifier = modifier
            .height(80.dp)
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "$", fontSize = 30.sp, fontWeight = FontWeight.Bold, color = Color.Black)
            Text(text = amount, fontSize = 12.sp, color = Color.White)
        }
    }
}

@Composable
fun PaymentButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFF388BAC),
            contentColor = Color.White
        ),
        modifier = modifier
    ) {
        Text(text = text, textAlign = TextAlign.Center)
    }
}

// Composable privado para el WebView
@SuppressLint("SetJavaScriptEnabled")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PayPalWebViewInternal(donationUrl: String, onClose: () -> Unit) {
    var isLoading by remember { mutableStateOf(true) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Donar con PayPal") },
                navigationIcon = {
                    IconButton(onClick = onClose) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Cerrar PayPal"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = BlueDark,
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White
                )
            )
        },
        containerColor = Color.White
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            AndroidView(
                factory = { context ->
                    WebView(context).apply {
                        webViewClient = object : WebViewClient() {
                            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                                super.onPageStarted(view, url, favicon)
                                isLoading = true
                            }

                            override fun onPageFinished(view: WebView?, url: String?) {
                                super.onPageFinished(view, url)
                                isLoading = false
                                // Opcional: Detectar éxito/cancelación basado en URL
                                // if (url != null && url.contains("paypal.com/...../thank_you")) {
                                //     onClose() // Cierra si la donación fue exitosa
                                // }
                            }
                        }
                        settings.javaScriptEnabled = true
                        loadUrl(donationUrl)
                    }
                },
                modifier = Modifier.fillMaxSize()
            )

            if (isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DonationPreview() {
    val navController = rememberNavController()
    val context = LocalContext.current
    DonationScreen(
        navController = navController,
        donationViewModel = DonationViewModel(DonationModel(context), context)
    )
}
