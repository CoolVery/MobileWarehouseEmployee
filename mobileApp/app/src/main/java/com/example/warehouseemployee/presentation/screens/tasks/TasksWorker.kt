package com.example.warehouseemployee.presentation.screens.tasks

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Indication
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.warehouseemployee.R
import com.example.warehouseemployee.data.classes.Task
import com.example.warehouseemployee.data.classes.Worker
import com.example.warehouseemployee.presentation.navigathion.InfoTaskLoadingDestination
import com.example.warehouseemployee.presentation.navigathion.MessagesDestination
import com.example.warehouseemployee.presentation.screens.infotask.InfoTaskLoading
import com.example.warehouseemployee.ui.theme.ThemeMode
import com.example.warehouseemployee.ui.theme.WarehouseEmployeeTheme
import kotlinx.coroutines.delay
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.net.URLEncoder
import java.nio.charset.StandardCharsets
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.util.Date
import kotlin.time.Duration.Companion.seconds

@Composable
fun TasksWorker(
    worker: Worker,
    navController: NavController,
    themeModeCurrent: ThemeMode?,
    viewModel: TasksWorkerViewModel = hiltViewModel()
) {
    //Хранит лист задач
    val taskList by viewModel.taskList.collectAsState(initial = emptyList())
    //Хранит лист задач, которые уже выполнены
    val startTaskList = remember { mutableStateListOf<Int>() }
    var themeMode by remember { mutableStateOf<ThemeMode?>(null) }
    //Флаг для отображения рабочих, которым можно написать
    var visibleChatMessages by remember { mutableStateOf(false) }
    //Список работников, которым можно написать
    val workerListToMessage by viewModel.workerListToMessage.collectAsState(initial = emptyList())



    viewModel.getTaskList(worker)
    //Обработка все возможных вариантов передачи themeMode с экрана
    if (themeModeCurrent == null && themeMode != ThemeMode.Dark) {
        themeMode = ThemeMode.Light
    } else if (themeModeCurrent == null && themeMode == ThemeMode.Dark) {
        themeMode = ThemeMode.Dark
    } else if (themeModeCurrent == ThemeMode.Dark && themeMode == ThemeMode.Light) {
        themeMode = ThemeMode.Light
    } else if (themeModeCurrent == ThemeMode.Dark && themeMode == ThemeMode.Dark) {
        themeMode = ThemeMode.Dark
    } else if (themeModeCurrent == ThemeMode.Light && themeMode == ThemeMode.Dark) {
        themeMode = ThemeMode.Dark
    } else if (themeModeCurrent == ThemeMode.Light && themeMode == ThemeMode.Light) {
        themeMode = ThemeMode.Light
    } else {
        themeMode = themeModeCurrent
    }
    //Часы, минуты, Секунды до следующей выполняемой задачи
    val (hours, minutes, seconds) = TimerStartTask(taskList, startTaskList)
    WarehouseEmployeeTheme(themeMode = themeMode!!) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(WarehouseEmployeeTheme.colors.background)
                .padding(30.dp, 0.dp)
                .clickable(
                    interactionSource = remember {
                        MutableInteractionSource()
                    },
                    indication = null
                ) {
                    visibleChatMessages = false
                }
        ) {
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()

            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 40.dp, bottom = 20.dp)
                        .clip(RoundedCornerShape(20.dp))
                        .background(WarehouseEmployeeTheme.colors.background_important_element)
                ) {
                    Column(
                        modifier = Modifier
                            .padding(0.dp, 30.dp)
                    ) {
                        Text(
                            text = "${worker.firstName} ${worker.lastName}",
                            style = WarehouseEmployeeTheme.typography.primaryTitle.copy(
                                fontSize = 24.sp
                            ),
                            color = WarehouseEmployeeTheme.colors.text_color_important_element

                        )
                        //Печатает часы, если все равно -1 - значит все задачи были выполнены
                        if (hours == -1 && minutes == -1 && seconds == -1) {
                            Text(
                                modifier = Modifier
                                    .padding(top = 10.dp),
                                text = "Сегодня задач больше нет",
                                style = WarehouseEmployeeTheme.typography.secondText.copy(
                                    fontSize = 13.sp
                                ),
                                color = WarehouseEmployeeTheme.colors.text_color_important_element

                            )
                        } else {
                            Text(
                                modifier = Modifier
                                    .padding(top = 10.dp),
                                text = "Задача через: ${hours}:${minutes}:${seconds}",
                                style = WarehouseEmployeeTheme.typography.secondText.copy(
                                    fontSize = 13.sp
                                ),
                                color = WarehouseEmployeeTheme.colors.text_color_important_element

                            )
                        }
                    }

                    IconButton(
                        onClick = {
                            if (themeMode == ThemeMode.Light) {
                                themeMode = ThemeMode.Dark
                            } else {
                                themeMode = ThemeMode.Light

                            }
                        }
                    ) {
                        if (themeMode == ThemeMode.Light) {
                            Icon(
                                painter = painterResource(id = R.drawable.dark_mode),
                                contentDescription = "",
                                tint = WarehouseEmployeeTheme.colors.color_icon
                            )
                        } else {
                            Icon(
                                painter = painterResource(id = R.drawable.light_mode),
                                contentDescription = "",
                                tint = WarehouseEmployeeTheme.colors.color_icon
                            )
                        }
                    }
                }
            }
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
            ) {
                items(
                    taskList,
                    key = { taskList -> taskList.id }
                ) { taskDate ->
                    //Если задача есть в списке выполненных, то его рамка зеленая
                    if (startTaskList.contains(taskDate.id)) {
                        TaskItem(
                            taskStartColor = Color.Green,
                            taskDate = taskDate,
                            navController = navController,
                            worker = worker,
                            themeMode = themeMode!!
                        )
                    } else {
                        TaskItem(
                            taskStartColor = Color.Transparent,
                            taskDate = taskDate,
                            navController = navController,
                            worker = worker,
                            themeMode = themeMode!!
                        )
                    }
                }
            }
            Button(

                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp, start = 40.dp, bottom = 50.dp),
                onClick = {

                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = WarehouseEmployeeTheme.colors.background_important_element,
                    contentColor = WarehouseEmployeeTheme.colors.text_color_important_element
                ),
                contentPadding = PaddingValues(vertical = 10.dp),
                shape = RoundedCornerShape(20.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            visibleChatMessages = true
                        },
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Сообщения",
                        style = WarehouseEmployeeTheme.typography.primaryTitle,
                        color = WarehouseEmployeeTheme.colors.text_color_important_element
                    )
                    Icon(
                        painter = painterResource(id = R.drawable.message),
                        contentDescription = "",
                        tint = WarehouseEmployeeTheme.colors.color_icon
                    )
                }
            }
        }
        //Мини колонка рабочих, которым можно написать, появляется снизу вверх
        AnimatedVisibility(
            modifier = Modifier
                .padding(top = 0.dp),
            visible = visibleChatMessages,
            enter = slideInVertically(
                initialOffsetY = { fullHeight -> fullHeight },
                animationSpec = tween(durationMillis = 500)
            ),
            exit = slideOutVertically(
                targetOffsetY = { fullHeight -> fullHeight },
                animationSpec = tween(durationMillis = 500)
            )
        ) {
            Column(
                modifier = Modifier
                    .padding(top = 200.dp)
                    .background(WarehouseEmployeeTheme.colors.background)

            ) {
                Spacer(modifier = Modifier.padding(vertical = 20.dp))
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 30.dp)
                        .clip(RoundedCornerShape(20.dp))
                        .background(WarehouseEmployeeTheme.colors.background_important_element)
                ) {
                    if (worker.idRole == 1) {
                        Text(
                            modifier = Modifier
                                .padding(vertical = 30.dp),
                            text = "Главные по смене",
                            color = WarehouseEmployeeTheme.colors.text_color_important_element,
                            style = WarehouseEmployeeTheme.typography.primaryTitle
                        )
                    } else {
                        Text(
                            modifier = Modifier
                                .padding(vertical = 30.dp),
                            text = "Работники на\nсмене",
                            color = WarehouseEmployeeTheme.colors.text_color_important_element,
                            style = WarehouseEmployeeTheme.typography.primaryTitle,
                            textAlign = TextAlign.Center
                        )
                    }
                }
                Spacer(modifier = Modifier.padding(vertical = 10.dp))

                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(WarehouseEmployeeTheme.colors.background)
                ) {
                    items(
                        workerListToMessage
                    ) { workerToMessage ->
                        Row(
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 30.dp, vertical = 20.dp)
                                .clip(RoundedCornerShape(20.dp))
                                .background(WarehouseEmployeeTheme.colors.background_for_light_mode)
                                .clickable {
                                    navController.navigate(
                                        "${MessagesDestination.route}/${Json.encodeToString(worker)}/${Json.encodeToString(workerToMessage)}/null/${themeMode!!.title}"
                                    )
                                }
                        ) {
                            Text(
                                workerToMessage.firstName + " " + workerToMessage.lastName + " " + workerToMessage.patronymic,
                                color = WarehouseEmployeeTheme.colors.text_color_second_element,
                                style = WarehouseEmployeeTheme.typography.secondText,
                                modifier = Modifier
                                    .padding(vertical = 30.dp)
                            )
                        }

                    }
                }
            }
        }
    }
}


@Composable
fun TimerStartTask(
    tasksList: List<Task>,
    startTaskList: MutableList<Int>
): Triple<Int, Int, Int> {
    var hours by remember { mutableStateOf(0) }
    var minutes by remember { mutableStateOf(0) }
    var seconds by remember { mutableStateOf(0) }
    var indexInList = 0
    //Если лист не пустой, т.к. поточные данные
    if (tasksList.isNotEmpty()) {
        //Для каждой задачи из списка, но получаем индексы
        for (taskIndex in tasksList.indices) {
            //Получаем дату выполнения, а потом именно часть со Временем
            val targetDateTimeStrin = tasksList[taskIndex].dateExecutionTask
            val targetTimeString = targetDateTimeStrin.substring(11, 19)
            //Создаем формат и приводим к ней дату на телефоне
            val sdf = SimpleDateFormat("HH:mm:ss")
            val targetDate = sdf.parse(targetTimeString)
            val currentDateAndTime = sdf.parse(sdf.format(Date()))
            //Если Времена равны, значит в лист выполненных добавляем айди задачи
            if (targetDate == currentDateAndTime) {
                startTaskList.add(tasksList[taskIndex].id)
                indexInList = taskIndex + 1
            }
            //Если Время в задаче меньше, чем на телефоне, добавляем также в список выполненных
            if (targetDate < currentDateAndTime) {
                startTaskList.add(tasksList[taskIndex].id)
                indexInList = taskIndex + 1
            }
            //Если Время в задаче больше, чем на телефоне, значит рассчитываем разницу, которая будет выводиться на экран
            if (targetDate > currentDateAndTime) {
                val differenceInMillis = targetDate.time - currentDateAndTime.time
                hours = (differenceInMillis / (1000 * 60 * 60)).toInt()
                minutes = ((differenceInMillis / (1000 * 60)) % 60).toInt()
                seconds = ((differenceInMillis / 1000) % 60).toInt()
                indexInList = taskIndex
                break;
            }

        }
    }
    //Сработает тогда, когда меняет индекс листа и сам лист
    LaunchedEffect(tasksList, indexInList) {
        while (true) {
            //В цикле уменьшаются время по секундам
            if (seconds > 0) {
                delay(1.seconds)
                seconds--
            } else if (minutes > 0) {
                seconds = 59
                minutes--
            } else if (hours > 0) {
                minutes = 59
                seconds = 59
                hours--
            } else {
                indexInList++
                //Если лист не пустой, индекс в листе меньше размеру листа и Времена не равны -1 (все выполнены)
                if (tasksList.isNotEmpty() && indexInList < tasksList.size && (hours != -1 && minutes != -1 && seconds != -1)) {
                    //Получаем задаче по индексу
                    val task = tasksList[indexInList]
                    val targetDateTimeString = task.dateExecutionTask
                    val targetTimeString = targetDateTimeString.substring(11, 19)
                    //Получаем времена и находим разницу
                    val sdf = SimpleDateFormat("HH:mm:ss")
                    val targetDate = sdf.parse(targetTimeString)
                    val currentDateAndTime = sdf.parse(sdf.format(Date()))

                    val differenceInMillis = targetDate.time - currentDateAndTime.time
                    hours = (differenceInMillis / (1000 * 60 * 60)).toInt() // Часы
                    minutes = ((differenceInMillis / (1000 * 60)) % 60).toInt() // Минуты
                    seconds = ((differenceInMillis / 1000) % 60).toInt() // Секунды
                } else {
                    hours = -1
                    minutes = -1
                    seconds = -1
                    break
                }
                break
            }
        }
    }

    return Triple(hours, minutes, seconds)
}
//Элемент Задача в списке
@Composable
fun TaskItem(
    taskStartColor: Color,
    taskDate: Task,
    navController: NavController,
    worker: Worker,
    themeMode: ThemeMode
) {
    Box(
        modifier = Modifier
            .padding(bottom = 20.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(WarehouseEmployeeTheme.colors.background_for_light_mode)
            .border(3.dp, taskStartColor, RoundedCornerShape(20.dp))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(0.dp, 20.dp)
        ) {
            Column(
                modifier = Modifier
                    .weight(4f)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    Text(
                        text = taskDate.dateExecutionTask.substring(11, 16),
                        style = WarehouseEmployeeTheme.typography.secondText.copy(
                            fontSize = 20.sp
                        ),
                        color = WarehouseEmployeeTheme.colors.text_color_second_element

                    )
                    Text(
                        text = taskDate.idCategoryTask.nameCategory,
                        style = WarehouseEmployeeTheme.typography.secondText.copy(
                            fontSize = 20.sp
                        ),
                        color = WarehouseEmployeeTheme.colors.text_color_second_element
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 5.dp),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    Text(
                        text = "${taskDate.idResponsibleWorker.firstName} ${taskDate.idResponsibleWorker.patronymic}",
                        style = WarehouseEmployeeTheme.typography.secondText.copy(
                            fontSize = 12.sp
                        ),
                        color = WarehouseEmployeeTheme.colors.text_color_second_element
                    )
                    Spacer(modifier = Modifier)
                }
            }
            Column(
                modifier = Modifier
                    .weight(1f)
            ) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(20.dp))
                        .background(Color.White)
                ) {
                    IconButton(
                        onClick = {
                            var tempDate = taskDate
                            tempDate.imgOptimalPath = "path"
                            navController.navigate(
                                "${InfoTaskLoadingDestination.route}/" +
                                        "${Json.encodeToString(worker)}/" +
                                        "${Json.encodeToString(tempDate)}/" +
                                        "${themeMode.title}"
                            )
                        }
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.arrow_right),
                            contentDescription = ""
                        )
                    }
                }
            }
        }
    }
}

