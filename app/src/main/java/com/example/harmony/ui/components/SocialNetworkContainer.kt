package com.example.harmony.ui.components

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.core.net.toUri
import com.example.harmony.R

@Composable
fun SocialNetworkContainer(
    modifier: Modifier = Modifier,
    imageResId: Int,
    name: String,
    followers: String,
    link: String
) {
    ConstraintLayout(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(color = Color.White)
            .height(80.dp)
            .fillMaxWidth()
    ) {
        val (image, nameText, followersText, followersImage) = createRefs()
        val context = LocalContext.current
        Image(
            painter = painterResource(id = imageResId),
            contentDescription = null,
            modifier = Modifier
                .size(50.dp, 50.dp)
                .clip(RoundedCornerShape(12.dp))
                .constrainAs(image) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start, margin = 16.dp)
                }
        )

        Text(
            text = name,
            color = Color.Black,
            fontSize = 16.sp,
            fontWeight = FontWeight.Normal,
            modifier = Modifier.constrainAs(nameText) {
                top.linkTo(parent.top, margin = 16.dp)
                start.linkTo(image.end, margin = 8.dp)
            }
        )

        Text(
            text = followers,
            color = Color.Black,
            fontSize = 14.sp,
            fontWeight = FontWeight.Normal,
            modifier = Modifier.constrainAs(followersText) {
                top.linkTo(nameText.bottom, margin = 4.dp)
                start.linkTo(image.end, margin = 8.dp)
            }
        )

        Box(
            modifier = Modifier
                .size(44.dp)
                .clip(RoundedCornerShape(100.dp))
                .background(Color(0xFF377BAC))
                .clickable {
                    val intent = Intent(Intent.ACTION_VIEW,
                        link.toUri())
                    context.startActivity(intent)
                }
                .constrainAs(followersImage) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    end.linkTo(parent.end, margin = 16.dp)
                },
            contentAlignment = androidx.compose.ui.Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.ingresar),
                contentDescription = null,
                contentScale = ContentScale.Fit,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}