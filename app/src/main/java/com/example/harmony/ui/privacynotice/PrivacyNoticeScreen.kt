package com.example.harmony.ui.privacynotice

import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.harmony.R
import com.example.harmony.ui.components.Background_inicio
import com.example.harmony.ui.theme.HarmonyTheme

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
            //Como su nombre lo dice, es un ejemplo xd
            CenterAlignedTopAppBar(
            title = {
                Text(
                    "Navigation example",
                )
            }
            )

        },
        bottomBar = {
            BottomAppBar(
                containerColor = MaterialTheme.colorScheme.primary,
                contentPadding = PaddingValues(16.dp)
            ) {
                Text("Eslaputo gogogogo", color = MaterialTheme.colorScheme.onPrimary)
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
        PrivacyNoticeScreen ()
    }
}
