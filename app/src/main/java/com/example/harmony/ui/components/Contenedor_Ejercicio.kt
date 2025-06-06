package com.example.harmony.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.harmony.R

@Composable
fun Container_Ejercicio(
    modifier: Modifier = Modifier,
    Titulo : String,
    Subtitulo : String,
    Duracion : String,
    Imagen : Int = 0,
    OnClick : () -> Unit
) {
    ConstraintLayout(
        modifier = Modifier
            .clip(RoundedCornerShape(10.dp))
            .background(Color(0xFF8E97FD))
            .height(200.dp)
            .width(150.dp)
    ) {
        val (titulo, subtitulo, duracion, imagen, boton) = createRefs()

        //Se acomoda la imagen al final del container
        Image(
            painter = painterResource(id = Imagen),
            contentDescription = null,
            modifier = Modifier
                .size(100.dp)
                .constrainAs(imagen) {
                    top.linkTo(parent.top)
                    end.linkTo(parent.end)
                }
        )

        // Este es el título, se acomoda justo debajo de la imagen
        Text(
            text = Titulo,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            maxLines = 1,
            color = Color.White,
            modifier = Modifier
                .constrainAs(titulo) {
                    top.linkTo(imagen.bottom, margin = (-5).dp)
                    start.linkTo(parent.start)
                }
                .padding(start = 10.dp, )
        )

        // Este es el subtítulo
        Text(
            text = Subtitulo,
            fontSize = 12.sp,
            color = Color.White,
            modifier = Modifier
                .constrainAs(subtitulo) {
                    top.linkTo(titulo.bottom)
                    start.linkTo(parent.start)
                }
                .padding(start = 10.dp, top = 2.dp)
        )

        Text(
            text = Duracion,
            fontSize = 10.sp,
            color = Color.White,
            modifier = Modifier
                .constrainAs(duracion) {
                    top.linkTo(boton.top)
                    bottom.linkTo(boton.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(boton.start)
                }
                .padding(bottom = 5.dp)
        )

        Button(
            onClick = OnClick,
            shape = RoundedCornerShape(50.dp),
            colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                containerColor = Color.White
            ),
            modifier = Modifier
                .constrainAs(boton) {
                    bottom.linkTo(parent.bottom)
                    end.linkTo(parent.end)
                }
                .wrapContentWidth()
                .height(40.dp)
                .padding(bottom = 5.dp, end = 5.dp)
        ) {
            Text(
                text = stringResource(R.string.iniciar),
                fontSize = 10.sp,
                color = Color.Black
            )
        }
    }
}