package com.example.harmony.ui.PrivacyNotice

import TopBarComponent
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.NotificationsNone
import androidx.compose.material.icons.filled.PersonOutline
import androidx.compose.material.icons.outlined.HelpOutline
import androidx.compose.material.icons.outlined.Logout
import androidx.compose.material.icons.outlined.MonetizationOn
import androidx.compose.material3.*
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.geometry.toRect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.withSaveLayer
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.harmony.R
import com.example.harmony.ui.components.Background_inicio
import com.example.harmony.ui.home.TopBar
import com.example.harmony.ui.theme.BlueDark
import com.example.harmony.ui.theme.HarmonyTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PrivacyNoticeScreen() {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val screenHeight = configuration.screenHeightDp.dp

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = Color.Transparent,
        topBar = {
            TopBarComponent()
        },
        bottomBar = {
            BottomAppBar(
                containerColor = MaterialTheme.colorScheme.primary,
                contentPadding = PaddingValues(16.dp)
            ) {
                Text("© 2024 Mi Empresa", color = MaterialTheme.colorScheme.onPrimary)
            }
        },
        content = { scaffoldPadding ->
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
                            .padding (
                                horizontal = screenWidth * 0.04f,
                                vertical = screenHeight * 0.03f
                            )
                            .verticalScroll(rememberScrollState())
                    ) {
                        Text(
                            text = stringResource(R.string.header_privacidad),
                            style = MaterialTheme.typography.headlineLarge,
                            textAlign = TextAlign.Justify,
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
                            modifier = Modifier.padding(
                                horizontal = screenWidth * 0.02f,
                                vertical = screenHeight * 0.02f
                            )
                        )
                    }
                }
            }
        }
    )
}

@Preview (showBackground = true)
@Composable
fun PrivacyNoticeScreenPreview () {
    HarmonyTheme {
        PrivacyNoticeScreen()
    }
}
