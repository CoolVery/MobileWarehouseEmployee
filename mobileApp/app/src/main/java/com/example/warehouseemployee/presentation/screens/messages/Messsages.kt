package com.example.warehouseemployee.presentation.screens.messages

import android.os.IBinder.DeathRecipient
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.motionEventSpy
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.warehouseemployee.R
import com.example.warehouseemployee.data.classes.Task
import com.example.warehouseemployee.data.classes.Worker
import com.example.warehouseemployee.presentation.navigathion.TasksWorkerDestination
import com.example.warehouseemployee.ui.theme.ThemeMode
import com.example.warehouseemployee.ui.theme.WarehouseEmployeeTheme
import kotlinx.coroutines.delay
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Composable
fun Messages(
    navController: NavHostController,
    sendWorker: Worker,
    recipientWorker: Worker,
    task: Task?,
    themeUI: ThemeMode,
    viewModel: MessagesViewModel = hiltViewModel()
) {
    var textValue by remember { mutableStateOf("") }
    val messageList by viewModel.messageList.collectAsState()

    viewModel.getMessages(sendWorker, recipientWorker)


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(WarehouseEmployeeTheme.colors.background)
            .clickable {  }
    ) {
        Spacer(Modifier.padding(vertical = 30.dp))

        Column(
            modifier = Modifier
                .padding(horizontal = 30.dp)
                .weight(3f)
                .clip(RoundedCornerShape(20.dp))
                .background(WarehouseEmployeeTheme.colors.background_second_element)

        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(20.dp))
                    .background(WarehouseEmployeeTheme.colors.background_important_element)
            ) {

                Column(
                    verticalArrangement = Arrangement.Center,

                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 10.dp)

                ) {
                    Spacer(Modifier.padding(vertical = 15.dp))

                    IconButton(

                        onClick = {

                        }
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.arrow_left),
                            contentDescription = ""
                        )
                    }
                    Spacer(Modifier.padding(vertical = 15.dp))

                }
                Column(
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .weight(3f)
                        .padding(start = 10.dp)
                ) {
                    Spacer(Modifier.padding(vertical = 15.dp))

                    Text(
                        text = "${recipientWorker.firstName} ${recipientWorker.lastName}\n${recipientWorker.patronymic}",
                        textAlign = TextAlign.Center,
                        style = WarehouseEmployeeTheme.typography.primaryTitle.copy(
                            fontSize = 20.sp
                        ),
                        color = WarehouseEmployeeTheme.colors.text_color_important_element
                    )
                    Spacer(Modifier.padding(vertical = 15.dp))

                }
            }
            LazyColumn (
                modifier = Modifier
                    .padding(top = 30.dp)
                    .fillMaxSize()
            ) {
                items(
                    messageList
                ) { message ->
                    if (message.idWorkerSender == sendWorker.idWorker) {
                        Row (
                            horizontalArrangement = Arrangement.End,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 30.dp)
                                .background(WarehouseEmployeeTheme.colors.background_message)
                        ) {
                            Text(
                                text = message.contentMessage,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                    else {
                        Row (
                            horizontalArrangement = Arrangement.Start,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(end = 30.dp)
                                .background(WarehouseEmployeeTheme.colors.background_message)
                        ) {
                            Text(
                                text = message.contentMessage,
                                textAlign = TextAlign.Center
                            )
                        }
                    }

                }
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(start = 30.dp, end = 30.dp, bottom = 50.dp, top = 20.dp)
        ) {
            TextField(
                value = textValue,
                onValueChange = {
                    newText -> textValue = newText
                },
                textStyle = WarehouseEmployeeTheme.typography.textField.copy(
                    textDecoration = TextDecoration.None
                ),
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(3f)
                    .padding(end = 10.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .background(Color.White),
                placeholder = {
                    Text(
                        text = "Набрать сообщение..."
                    )
                },
                minLines = 1,
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = Color.Transparent,
                    focusedBorderColor = Color.Transparent,
                    unfocusedSuffixColor = Color.Transparent,
                    focusedSupportingTextColor = Color.Transparent,
                    unfocusedSupportingTextColor = Color.Transparent
                ),
            )
            IconButton(
                onClick = {},
                enabled = textValue.isNotBlank(),
                modifier = Modifier
                    .weight(1f)
                    .fillMaxSize()
                    .padding(vertical = 20.dp)
                    .clip(RoundedCornerShape(15.dp))
                    .background(WarehouseEmployeeTheme.colors.background_important_element)
            ) {
                Icon(
                    modifier = Modifier
                        .width(60.dp),
                    painter = painterResource(id = R.drawable.send_message),
                    contentDescription = ""
                )
            }
        }
    }
}