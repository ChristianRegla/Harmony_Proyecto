package ai.codia.x.composeui.demo

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.harmony.R
import com.example.harmony.ui.theme.HarmonyTheme

/**
 * Si hay comentarios muy boludos me recuerdan de quitarlos
 * Son apuntes solo para mí y no son muy relevantes
 */
@Composable
fun CodiaMainView() {
    // Box de la Pantalla
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.TopStart
    ) {
        // Image-140:639-image 1
        Image(
            painter = painterResource(id = R.drawable.background_inicio),
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .size(446.dp, 917.dp),
        )
        // Header
        Row (){
            Text(text="Java", color.White)
        }
        // Box del letrero principal
        Box(
            contentAlignment = Alignment.TopStart,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .offset(x = 0.5.dp, y = 97.dp)
                .background(Color(0xffffffff), RoundedCornerShape(15.dp))
                .fillMaxWidth(0.9f),
        ) {
            // Box-140:664-donacionesframe
            Box(
                contentAlignment = Alignment.TopStart,
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .fillMaxWidth(1f)
                    .fillMaxHeight(0.1f)
                    .clipToBounds(),
            ) {
                // Text-140:665-En Harmony, tu privacidad y seguridad son nuestra prioridad.
                Text(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .wrapContentSize()
                        .offset(x = 0.dp, y = 0.5.dp),
                    text = "En Harmony, tu privacidad y seguridad \nson nuestra prioridad.",
                    color = Color(0xff000000),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal,
                    textAlign = TextAlign.Center,
                    overflow = TextOverflow.Ellipsis,
                )
            }
            // Text-140:666-Entendemos lo importante que es sentirte seguro al compartir tus pensamientos y emociones. Por eso, todas tus conversaciones con nuestro chatbot están protegidas con tecnología de encriptación avanzada, garantizando que tus datos personales y tu información de salud mental sean confidenciales y seguros. En Harmony, no almacenamos tus conversaciones de manera identificable, y tus datos nunca serán compartidos con terceros sin tu consentimiento explícito. Nuestro objetivo es brindarte un espacio seguro donde puedas expresarte libremente y recibir el apoyo que necesitas. Tu bienestar emocional es importante para nosotros, y eso incluye proteger tu privacidad en todo momento. 🌟 Si tienes alguna pregunta sobre cómo manejamos tus datos, no dudes en contactarnos. Estamos aquí para ayudarte. — El equipo de Harmony 💙
            Text(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .offset(x = 0.dp, y = 84.dp)
                    .size(323.dp, 615.dp),
                text = "Entendemos lo importante que es sentirte seguro al compartir tus pensamientos y emociones. Por eso, todas tus conversaciones con nuestro chatbot están protegidas con tecnología de encriptación avanzada, garantizando que tus datos personales y tu información de salud mental sean confidenciales y seguros.\nEn Harmony, no almacenamos tus conversaciones de manera identificable, y tus datos nunca serán compartidos con terceros sin tu consentimiento explícito. Nuestro objetivo es brindarte un espacio seguro donde puedas expresarte libremente y recibir el apoyo que necesitas.\nTu bienestar emocional es importante para nosotros, y eso incluye proteger tu privacidad en todo momento. 🌟\nSi tienes alguna pregunta sobre cómo manejamos tus datos, no dudes en contactarnos. Estamos aquí para ayudarte.\n— El equipo de Harmony 💙",
                color = Color(0xff000000),
                fontSize = 13.sp,
                fontWeight = FontWeight.Normal,
                textAlign = TextAlign.Justify,
                overflow = TextOverflow.Ellipsis,
            )
        }
        // Box-156:593-Frame 36
        Box(
            contentAlignment = Alignment.TopStart,
            modifier = Modifier
                .align(Alignment.TopStart)
                .offset(x = 0.dp, y = 850.dp)
                .background(Color(0xff295e84))
                .size(412.dp, 67.dp)
                .clipToBounds(),
        ) {
            // Text-156:594-Inicio
            Text(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .offset(x = 108.dp, y = 36.dp)
                    .size(39.dp, 16.dp),
                text = "Inicio",
                color = Color(0xffcac4d0),
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal,
                textAlign = TextAlign.Left,
                overflow = TextOverflow.Ellipsis,
            )
            // Image-156:595-Vector
            Image(
                painter = painterResource(id = R.drawable.background_inicio),
                contentDescription = null,
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .offset(x = 115.dp, y = 10.dp)
                    .size(24.228.dp, 26.112.dp),
            )
            // Text-156:596-Relajación
            Text(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .offset(x = 248.dp, y = 36.dp)
                    .size(73.738.dp, 15.667.dp),
                text = "Relajación",
                color = Color(0xffffffff),
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal,
                textAlign = TextAlign.Center,
                overflow = TextOverflow.Ellipsis,
            )
            // Image-156:597-yoga 2
            Image(
                painter = painterResource(id = R.drawable.background_inicio),
                contentDescription = null,
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .offset(x = 271.dp, y = 6.dp)
                    .size(28.dp, 28.dp),
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CodiaMainViewPreview() {
    HarmonyTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            val scrollState = rememberScrollState()
            Column(modifier = Modifier.verticalScroll(scrollState)) {
                CodiaMainView()
            }
        }
    }
}
