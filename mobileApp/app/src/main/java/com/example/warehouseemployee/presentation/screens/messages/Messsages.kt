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
import com.example.warehouseemployee.presentation.navigathion.InfoTaskLoadingDestination
import com.example.warehouseemployee.presentation.navigathion.TasksWorkerDestination
import com.example.warehouseemployee.presentation.screens.infotask.InfoTaskLoading
import com.example.warehouseemployee.ui.theme.ThemeMode
import com.example.warehouseemployee.ui.theme.WarehouseEmployeeTheme
import kotlinx.coroutines.delay
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlin.time.Duration.Companion.seconds

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

    LaunchedEffect(Unit) {
        while (true) {
            delay(5.seconds)
            viewModel.getMessages(sendWorker, recipientWorker)

        }
    }
    WarehouseEmployeeTheme(
        themeMode = themeUI
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(WarehouseEmployeeTheme.colors.background)
        ) {
            Spacer(Modifier.padding(vertical = 30.dp))

            Column(
                modifier = Modifier
                    .padding(horizontal = 30.dp)
                    .weight(3f)
                    .clip(RoundedCornerShape(20.dp))
                    .background(WarehouseEmployeeTheme.colors.background_for_light_mode)

            ) {

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
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
                                if (task == null) {
                                    navController.navigate(
                                        "${TasksWorkerDestination.route}/${
                                            Json.encodeToString(
                                                sendWorker
                                            )
                                        }/${themeUI.title}"
                                    )
                                } else {
                                    navController.navigate(
                                        "${InfoTaskLoadingDestination.route}/${
                                            Json.encodeToString(
                                                sendWorker
                                            )
                                        }/${Json.encodeToString(task)}/${themeUI.title}"
                                    )
                                }
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
                            .weight(4f)
                            .padding(start = 10.dp)
                    ) {
                        Spacer(Modifier.padding(vertical = 15.dp))

                        Text(
                            text = "${recipientWorker.firstName} ${recipientWorker.patronymic}\n${recipientWorker.lastName}",
                            textAlign = TextAlign.Center,
                            style = WarehouseEmployeeTheme.typography.primaryTitle.copy(
                                fontSize = 18.sp
                            ),
                            color = WarehouseEmployeeTheme.colors.text_color_important_element
                        )

                    }
                }
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    items(
                        messageList
                    ) { message ->
                        if (message == messageList[0]) {
                            Spacer(Modifier.padding(vertical = 15.dp))
                        }
                        if (message.idWorkerSender == sendWorker.idWorker) {
                            Row(
                                horizontalArrangement = Arrangement.End,
                                modifier = Modifier
                                    .fillMaxWidth()
                            ) {
                                Spacer(modifier = Modifier.weight(1f))
                                Box(
                                    contentAlignment = Alignment.Center,

                                    modifier = Modifier
                                        .weight(2f)
                                        .clip(
                                            RoundedCornerShape(
                                                topStart = 20.dp,
                                                bottomStart = 20.dp
                                            )
                                        )
                                        .background(WarehouseEmployeeTheme.colors.background_message)
                                        .padding(vertical = 20.dp)

                                ) {
                                    Text(
                                        text = message.contentMessage,
                                        style = WarehouseEmployeeTheme.typography.smallText,
                                        color = WarehouseEmployeeTheme.colors.text_color_message,
                                        textAlign = TextAlign.Center
                                    )
                                }

                            }
                            Spacer(modifier = Modifier.padding(vertical = 10.dp))

                        } else {
                            Row(
                                horizontalArrangement = Arrangement.Start,
                                modifier = Modifier
                                    .fillMaxWidth()
                            ) {
                                Box(
                                    contentAlignment = Alignment.Center,
                                    modifier = Modifier
                                        .weight(2f)
                                        .clip(RoundedCornerShape(topEnd = 20.dp, bottomEnd = 20.dp))
                                        .background(WarehouseEmployeeTheme.colors.background_message)
                                        .padding(vertical = 20.dp)

                                ) {
                                    Text(
                                        modifier = Modifier
                                            .padding(horizontal = 10.dp),
                                        text = message.contentMessage,
                                        style = WarehouseEmployeeTheme.typography.smallText,
                                        color = WarehouseEmployeeTheme.colors.text_color_message,
                                        textAlign = TextAlign.Center
                                    )
                                }
                                Spacer(modifier = Modifier.weight(1f))

                            }
                            Spacer(modifier = Modifier.padding(vertical = 10.dp))

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
                    onValueChange = { newText ->
                        textValue = newText
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
                    onClick = {
                        viewModel.sendMessage(textValue, sendWorker)
                        textValue = ""
                    },
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
}

