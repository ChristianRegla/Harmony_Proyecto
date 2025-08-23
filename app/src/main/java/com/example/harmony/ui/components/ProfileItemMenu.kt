package com.example.harmony.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.harmony.ui.screens.MenuItem

@Composable
fun ProfileItemMenu(
    imageId: Int,
    title: String,
    option: String,
    onClick: () -> Unit
) {
    ConstraintLayout(
        modifier = Modifier.clickable(onClick = onClick)
    ) {
        val (itemImage, titleText, optionText) = createRefs()

        Image(
            painter = painterResource(id = imageId),
            contentDescription = null,
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .size(24.dp)
                .constrainAs(itemImage) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                }
                .padding(start = 8.dp)
        )

        Text(
            text = title,
            color = Color.White,
            fontSize = 14.sp,
            fontWeight = FontWeight.Normal,
            textAlign = TextAlign.Left,
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(titleText) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(itemImage.end)
                    if (option.isEmpty()) {
                        end.linkTo(parent.end)
                    } else {
                        end.linkTo(optionText.start)
                    }
                }
                .padding(start = 32.dp)
        )

        Text(
            text = option,
            color = Color.White,
            fontSize = 14.sp,
            fontWeight = FontWeight.Normal,
            textAlign = TextAlign.Right,
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(optionText) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    end.linkTo(parent.end)
                }
                .padding(end = 8.dp)
        )
    }
}