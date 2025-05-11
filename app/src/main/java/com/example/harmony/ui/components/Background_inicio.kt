package com.example.harmony.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.example.harmony.R

@Composable
fun Background_inicio(
    modifier: Modifier = Modifier
){
    Box(
        modifier = Modifier.fillMaxSize()
    ){
        Image(
            //Fondo
            painter = painterResource(R.drawable.background_inicio),
            contentDescription = stringResource(R.string.background_image),
            //Pone de manera uniforme height y weight
            contentScale = ContentScale.Crop,
            //Se agranda al size al del padre
            modifier = Modifier.matchParentSize()
        )
    }
}