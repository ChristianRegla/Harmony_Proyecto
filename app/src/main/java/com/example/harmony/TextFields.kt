package com.example.harmony

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp

@Composable
fun ApodoTextField(
    apodo: String,
    onApodoChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = apodo,
        onValueChange = onApodoChange,
        label = { Text(text = stringResource(id = R.string.user_name)) },
        shape = RoundedCornerShape(15.dp),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            backgroundColor = Color(0xFFEEEEEE),
            focusedBorderColor = Color.Black,
            unfocusedBorderColor = Color.Gray,
            textColor = Color.Black,
            focusedLabelColor = Color.Black,
            unfocusedLabelColor = Color.Gray
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
    OutlinedTextField(
        value = email,
        onValueChange = onEmailChange,
        label = { Text(text = stringResource(id = R.string.email)) },
        shape = RoundedCornerShape(15.dp),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            backgroundColor = Color(0xFFEEEEEE),
            focusedBorderColor = Color.Black,
            unfocusedBorderColor = Color.Gray,
            textColor = Color.Black,
            focusedLabelColor = Color.Black,
            unfocusedLabelColor = Color.Gray
        ),
        modifier = modifier.fillMaxWidth().padding(start = 32.dp, end = 32.dp),
        singleLine = true
    )
}

@Composable
fun PasswordTextField(
    password: String,
    onPasswordChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val passwordVisible = remember { mutableStateOf(false) }

    OutlinedTextField(
        value = password,
        onValueChange = onPasswordChange,
        label = { Text(text = stringResource(id = R.string.password)) },
        shape = RoundedCornerShape(15.dp),
        visualTransformation = if (passwordVisible.value) VisualTransformation.None else PasswordVisualTransformation(),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            backgroundColor = Color(0xFFEEEEEE),
            focusedBorderColor = Color.Black,
            unfocusedBorderColor = Color.Gray,
            textColor = Color.Black,
            focusedLabelColor = Color.Black,
            unfocusedLabelColor = Color.Gray
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