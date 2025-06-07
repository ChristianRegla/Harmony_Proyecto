package com.example.harmony.ui.components

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.core.net.toUri
import com.example.harmony.R

@Composable
fun EmailContainer(
    modifier: Modifier = Modifier,
    imageResId: Int,
    title: String,
    description: String,
    schedule: String
) {
    val context = LocalContext.current
    ConstraintLayout(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .clip(RoundedCornerShape(25.dp))
            .background(color = Color.White)
            .padding(vertical = 24.dp, horizontal = 16.dp)
    ) {
        val (image, titleText, descriptionText, scheduleText) = createRefs()
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(12.dp))
                .size(60.dp)
                .background(color = Color.Black)
                .clickable {
                    val intent = Intent(Intent.ACTION_SENDTO).apply {
                        data = "mailto:a22310380@ceti.mx".toUri() // Correo Electronico
                    }
                    context.startActivity(intent)
                }
                .constrainAs(image) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
            contentAlignment = androidx.compose.ui.Alignment.Center
        ) {
            Image(
                painter = painterResource(id = imageResId),
                contentDescription = null,
                contentScale = ContentScale.Fit,
                modifier = Modifier.size(30.dp)
            )
        }

        Text(
            text = title,
            color = Color.Black,
            fontSize = 18.sp,
            fontWeight = FontWeight.Normal,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .constrainAs(titleText) {
                    top.linkTo(image.bottom, margin = 8.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        )

        Text(
            text = description,
            color = Color.Black,
            fontSize = 12.sp,
            fontWeight = FontWeight.Normal,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .constrainAs(descriptionText) {
                    top.linkTo(titleText.bottom, margin = 8.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        )

        Text(
            text = schedule,
            color = Color.Black,
            fontSize = 12.sp,
            fontWeight = FontWeight.Normal,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .constrainAs(scheduleText) {
                    top.linkTo(descriptionText.bottom, margin = 8.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        )
    }
}