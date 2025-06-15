package com.example.harmony

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp


@Composable
fun ApodoTextField(
    apodo: String,
    onApodoChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    TextField(
        value = apodo,
        onValueChange = onApodoChange,
        label = { Text(text = stringResource(id = R.string.user_name)) },
        shape = RoundedCornerShape(15.dp),
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = Color(0xFFEEEEEE),
            focusedContainerColor = Color(0xFFEEEEEE),

            unfocusedIndicatorColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,

            unfocusedTextColor = Color.Black,
            focusedTextColor = Color.Black,
            unfocusedLabelColor = Color.Gray,
            focusedLabelColor = Color.Black,

            cursorColor = Color.Black
        ),
        modifier = modifier.fillMaxWidth().padding(start = 32.dp, end = 32.dp),
        singleLine = true
    )
}

@Composable
fun EmailTextField(
    email: String,
    onEmailChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    TextField(
        value = email,
        onValueChange = onEmailChange,
        label = { Text(text = stringResource(id = R.string.email)) },
        shape = RoundedCornerShape(15.dp),
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = Color(0xFFEEEEEE),
            focusedContainerColor = Color(0xFFEEEEEE),

            unfocusedIndicatorColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,

            unfocusedTextColor = Color.Black,
            focusedTextColor = Color.Black,
            unfocusedLabelColor = Color.Gray,
            focusedLabelColor = Color.Black,

            cursorColor = Color.Black
        ),
        modifier = modifier.fillMaxWidth().padding(start = 32.dp, end = 32.dp),
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Email,
            imeAction = ImeAction.Next
        )
    )
}

@Composable
fun PasswordTextField(
    password: String,
    onPasswordChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val passwordVisible = remember { mutableStateOf(false) }

    TextField(
        value = password,
        onValueChange = onPasswordChange,
        label = { Text(text = stringResource(id = R.string.password)) },
        shape = RoundedCornerShape(15.dp),
        visualTransformation = if (passwordVisible.value) VisualTransformation.None else PasswordVisualTransformation(),
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = Color(0xFFEEEEEE),
            focusedContainerColor = Color(0xFFEEEEEE),

            unfocusedIndicatorColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,

            unfocusedTextColor = Color.Black,
            focusedTextColor = Color.Black,
            unfocusedLabelColor = Color.Gray,
            focusedLabelColor = Color.Black,

            cursorColor = Color.Black
        ),
        modifier = modifier.fillMaxWidth().padding(start = 32.dp, end = 32.dp),
        singleLine = true,
        trailingIcon = {
            IconButton(onClick = { passwordVisible.value = !passwordVisible.value }) {
                Icon(
                    imageVector = if (passwordVisible.value) Icons.Filled.VisibilityOff else Icons.Filled.Visibility,
                    contentDescription = "Toggle password visibility"
                )
            }
        }
    )
}
@Composable
fun NombreTextField(
    nombre: String,
    onNombreChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    TextField(
        value = nombre,
        onValueChange = onNombreChange,
        label = { Text(text = stringResource(id = R.string.nombre)) },
        shape = RoundedCornerShape(15.dp),
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = Color(0xFFEEEEEE),
            focusedContainerColor = Color(0xFFEEEEEE),

            unfocusedIndicatorColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,

            unfocusedTextColor = Color.Black,
            focusedTextColor = Color.Black,
            unfocusedLabelColor = Color.Gray,
            focusedLabelColor = Color.Black,

            cursorColor = Color.Black
        ),
        modifier = modifier.fillMaxWidth().padding(start = 32.dp, end = 32.dp),
        singleLine = true,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
    )
}
@Composable
fun Apodo2TextField(
    apodo2: String,
    onApodo2Change: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    TextField(
        value = apodo2,
        onValueChange = onApodo2Change,
        label = { Text(text = stringResource(id = R.string.apodo)) },
        shape = RoundedCornerShape(15.dp),
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = Color(0xFFEEEEEE),
            focusedContainerColor = Color(0xFFEEEEEE),

            unfocusedIndicatorColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,

            unfocusedTextColor = Color.Black,
            focusedTextColor = Color.Black,
            unfocusedLabelColor = Color.Gray,
            focusedLabelColor = Color.Black,

            cursorColor = Color.Black
        ),
        modifier = modifier.fillMaxWidth().padding(start = 32.dp, end = 32.dp),
        singleLine = true,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
    )
}
@Composable
fun NumeroTextField(
    numero: String,
    onNumeroChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    TextField(
        value = numero,
        onValueChange = { newValue ->
            // Solo permite números enteros
            if (newValue.all { it.isDigit() }) {
                onNumeroChange(newValue)
            }
        },
        label = { Text(text = stringResource(id = R.string.Numero_Telefonico)) },
        shape = RoundedCornerShape(15.dp),
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = Color(0xFFEEEEEE),
            focusedContainerColor = Color(0xFFEEEEEE),

            unfocusedIndicatorColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,

            unfocusedTextColor = Color.Black,
            focusedTextColor = Color.Black,
            unfocusedLabelColor = Color.Gray,
            focusedLabelColor = Color.Black,

            cursorColor = Color.Black
        ),
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 32.dp, end = 32.dp),
        singleLine = true,
        leadingIcon = {
            Icon(
                painter = painterResource(id = R.drawable.mexico_flag),
                contentDescription = "Icono de bandera",
                tint = Color.Unspecified,
                modifier = Modifier.size(24.dp)
            )
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Next
        )
    )
}
@Composable
fun CiudadDropdown(
    ciudadSeleccionada: String,
    onCiudadSeleccionada: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val ciudades = listOf("Ciudad de México", "Guadalajara", "Monterrey", "Puebla", "Mérida")
    var expanded by remember { mutableStateOf(false) }

    Box(modifier = modifier.padding(horizontal = 25.dp)) { // Reducir padding horizontal
        TextField(
            value = ciudadSeleccionada,
            onValueChange = {},
            label = { Text(text = stringResource(id = R.string.ciudad)) },
            shape = RoundedCornerShape(15.dp),
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Color(0xFFEEEEEE),
                focusedContainerColor = Color(0xFFEEEEEE),

                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,

                unfocusedTextColor = Color.Black,
                focusedTextColor = Color.Black,
                unfocusedLabelColor = Color.Gray,
                focusedLabelColor = Color.Black,

                cursorColor = Color.Black
            ),
            modifier = Modifier
                .fillMaxWidth(1f) // Ancho ajustado para ser más compacto
                .clickable { expanded = true },
            readOnly = true,
            trailingIcon = {
                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = "Desplegar",
                    modifier = Modifier.clickable { expanded = true }
                )
            }
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .fillMaxWidth(0.75f) // Menú ajustado
        ) {
            ciudades.forEach { ciudad ->
                DropdownMenuItem(
                    text = { Text(text = ciudad) },
                    onClick = {
                        onCiudadSeleccionada(ciudad)
                        expanded = false
                    }
                )
            }
        }
    }
}

@Composable
fun GeneroDropdown(
    generoSeleccionado: String,
    onGeneroSeleccionado: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val masculino = stringResource(id = R.string.masculino)
    val femenino = stringResource(id = R.string.femenino)
    val prefieroNoDecirlo = stringResource(id = R.string.prefiero_no_decirlo)
    val generos = listOf(masculino, femenino, prefieroNoDecirlo)
    var expanded by remember { mutableStateOf(false) }

    Box(modifier = modifier.padding(horizontal = 25.dp)) { // Reducir padding horizontal
        TextField(
            value = generoSeleccionado,
            onValueChange = {},
            label = { Text(text = stringResource(id = R.string.genero)) },
            shape = RoundedCornerShape(15.dp),
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Color(0xFFEEEEEE),
                focusedContainerColor = Color(0xFFEEEEEE),

                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,

                unfocusedTextColor = Color.Black,
                focusedTextColor = Color.Black,
                unfocusedLabelColor = Color.Gray,
                focusedLabelColor = Color.Black,

                cursorColor = Color.Black
            ),
            modifier = Modifier
                .fillMaxWidth(1f) // Ancho ajustado
                .clickable { expanded = true },
            readOnly = true,
            trailingIcon = {
                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = "Desplegar",
                    modifier = Modifier.clickable { expanded = true }
                )
            }
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .fillMaxWidth(0.75f)
        ) {
            generos.forEach { genero ->
                DropdownMenuItem(
                    text = { Text(text = genero) },
                    onClick = {
                        onGeneroSeleccionado(genero)
                        expanded = false
                    }
                )
            }
        }
    }
}

@Composable
fun DomicilioTextField(
    domicilio: String,
    onDomicilioChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    TextField(
        value = domicilio,
        onValueChange = onDomicilioChange,
        label = { Text(text = stringResource(id =R.string.domicilio)) },
        shape = RoundedCornerShape(15.dp),
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = Color(0xFFEEEEEE),
            focusedContainerColor = Color(0xFFEEEEEE),

            unfocusedIndicatorColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,

            unfocusedTextColor = Color.Black,
            focusedTextColor = Color.Black,
            unfocusedLabelColor = Color.Gray,
            focusedLabelColor = Color.Black,

            cursorColor = Color.Black
        ),
        modifier = modifier.fillMaxWidth().padding(start = 32.dp, end = 32.dp),
        singleLine = true,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done)
    )
}