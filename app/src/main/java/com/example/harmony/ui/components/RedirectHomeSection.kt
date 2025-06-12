package com.example.harmony.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavController
import com.example.harmony.R

@Composable
fun RedirectHomeSection(
    modifier: Modifier = Modifier,
    titulo: String,
    subtitulo: String,
    imageResId: Int = 0,
    navController: NavController,
    onClick: () -> Unit = {}
) {
    ConstraintLayout(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(color = Color.White)
            .height(80.dp)
            .fillMaxWidth()
            .clickable(onClick = onClick)
    ) {
        val (title, subtitle, image, arrow) = createRefs()

        // Create a vertical chain for the title and subtitle
        createVerticalChain(title, subtitle) // This links them together vertically

        if(imageResId != 0) {
            Image(
                painter = painterResource(id = imageResId),
                contentDescription = null,
                modifier = Modifier
                    .size(40.dp)
                    .constrainAs(image) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start, margin = 16.dp)
                    }
            )
        }

        Text(
            text = titulo,
            fontSize = 16.sp,
            color = Color.Black,
            modifier = Modifier
                .constrainAs(title) {
                    // Start constraint: depends on image presence
                    if (imageResId != 0) {
                        start.linkTo(image.end, margin = 16.dp)
                    } else {
                        start.linkTo(parent.start, margin = 16.dp)
                    }
                    end.linkTo(arrow.start, margin = 8.dp) // Constrain title to the right of the arrow
                    width = Dimension.fillToConstraints // Allow title to fill available space horizontally

                    // Vertically center the entire chain (title and subtitle)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                }
        )

        Text(
            text = subtitulo,
            fontSize = 14.sp,
            color = Color(0xFF1D1B20),
            modifier = Modifier
                .constrainAs(subtitle) {
                    // Start constraint: depends on image presence
                    if (imageResId != 0) {
                        start.linkTo(image.end, margin = 16.dp)
                    } else {
                        start.linkTo(parent.start, margin = 16.dp)
                    }
                    end.linkTo(arrow.start, margin = 8.dp) // Constrain subtitle to the right of the arrow
                    width = Dimension.fillToConstraints // Allow subtitle to fill available space horizontally

                    // No explicit top/bottom links needed here as it's part of the vertical chain
                    // top.linkTo(title.bottom) // This was correct for chaining
                }
        )

        Image(
            painter = painterResource(id = R.drawable.image_arrow_right),
            contentDescription = null,
            modifier = Modifier
                .size(24.dp)
                .constrainAs(arrow) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    end.linkTo(parent.end, margin = 16.dp)
                }
        )
    }
}