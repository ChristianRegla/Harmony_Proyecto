package com.example.harmony.ui.chat

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.harmony.R
import com.example.harmony.ui.components.ConfirmationDialog
import com.example.harmony.ui.components.SystemBarStyle
import com.example.harmony.ui.theme.ColorModelMessage
import com.example.harmony.ui.theme.ColorUserMessage
import com.example.harmony.ui.theme.Sendbutton

@RequiresApi(Build.VERSION_CODES.VANILLA_ICE_CREAM)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(navController: NavHostController, chatViewModel: ChatViewModel) {

    var showConfirmationDialog by remember { mutableStateOf(false) }

    SystemBarStyle(
        statusBarColor = Color.Transparent,
        navigationBarColor = Color.Transparent,
    )

    // Esto es para el diálogo de confirmar o cancelar el eliminar una conversación
    if (showConfirmationDialog) {
        ConfirmationDialog(
            onConfirm = {
                chatViewModel.deleteChatHistory()
                showConfirmationDialog = false
            },
            onDismiss = {
                showConfirmationDialog = false
            }
        )
    }

    Box(modifier = Modifier.fillMaxSize()){
        Image(
            painter = painterResource(id = R.drawable.background_chatbot),
            contentDescription = "Background Image",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Scaffold(
            topBar = {
                ChatTopBar(
                    onBackClick = { navController.popBackStack() },
                    title = stringResource(R.string.chatbot),
                    onDeleteChatClick = { showConfirmationDialog = true },
                    modifier = Modifier.size(56.dp)
                )
            },
            containerColor = Color.Transparent,
            contentColor = Color.White
        ) { padding ->
            Column(
                modifier = Modifier.padding(padding)
            ) {
                MessageList(
                    modifier = Modifier.weight(1f),
                    messageList = chatViewModel.messageList
                )
                MessageInput(
                    onMessageSend = {
                        chatViewModel.sendMessage(it)
                    }
                )
            }
        }
    }
}



@Composable
fun MessageList(modifier: Modifier = Modifier, messageList: List<MessageModel>){
    val mensajePredeterminado = stringResource(R.string.hola_soy_harmony)

    if(messageList.isEmpty()){
        Column(
            modifier = modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(painter = painterResource(id = R.drawable.baseline_question_answer_24),
                contentDescription = "Icon",
                tint = Color.White,
                modifier = Modifier.size(60.dp)
            )
            Text(text = mensajePredeterminado,
                fontSize = 22.sp,
                textAlign = TextAlign.Center
            )
        }
    } else {
        LazyColumn(
            modifier = modifier,
            reverseLayout = true
        ){
            items(messageList.reversed()) {
                MessageRow(messageModel = it)
            }
        }
    }
}

//Esto es para los mensajes de usuario y modelo
@Composable
fun MessageRow(messageModel: MessageModel){
    val isModel = messageModel.role == "model"

    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            Box(
                //Si el mensaje es del modelo, lo ponemos a la derecha, sino a la izquierda
                modifier = Modifier.align(if(isModel) Alignment.BottomStart else Alignment.BottomEnd)
                    .padding(
                        start = if(isModel) 8.dp else 70.dp,
                        end = if(isModel) 70.dp else 8.dp,
                        top = 8.dp,
                        bottom = 8.dp
                    )
                    .clip(RoundedCornerShape(48f))
                    .background(if(isModel) ColorModelMessage else ColorUserMessage.copy(alpha = 0.8f))
                    .padding(16.dp)

            ) {
                //Si el mensaje es del modelo, lo envolvemos en un SelectionContainer para que podamos copiar el mensaje
                SelectionContainer {
                    Text(
                        text = messageModel.message,
                        fontWeight = FontWeight.W500,
                        color = Color.Black
                    )
                }
            }
        }

    }
}

@Composable
fun MessageInput(onMessageSend: (String) -> Unit){

    var message by remember {
        mutableStateOf("")
    }

    Row(
        modifier = Modifier.padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedTextField(
            modifier = Modifier
                .weight(1f)
                .padding(start = 8.dp, end = 8.dp),
            value = message,
            onValueChange = {
                message = it
            },
            placeholder = {
                Text(text = "Escribe un mensaje")
            },
            shape = RoundedCornerShape(16.dp),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
            )
        )
        IconButton(
            onClick = {
            if(message.isNotEmpty()){
                onMessageSend(message)
                message = ""
            }
        },
            colors = IconButtonDefaults.iconButtonColors(containerColor = Sendbutton),
            modifier = Modifier
                .size(50.dp)
                .clip(CircleShape)
        ){
            Icon(
                imageVector = Icons.AutoMirrored.Filled.Send,
                contentDescription = "Send",
                tint = Color.Black
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.VANILLA_ICE_CREAM)
@Preview(showBackground = true)
@Composable
fun ChatbotPreview() {
    val navController = rememberNavController()
    ChatScreen(navController = navController, chatViewModel = ChatViewModel())
}