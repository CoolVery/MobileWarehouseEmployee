package com.example.warehouseemployee.presentation.screens.tasks

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.warehouseemployee.R
import com.example.warehouseemployee.data.classes.Task
import com.example.warehouseemployee.data.classes.Worker
import com.example.warehouseemployee.ui.theme.ThemeMode
import com.example.warehouseemployee.ui.theme.WarehouseEmployeeTheme
import kotlinx.coroutines.delay
import kotlinx.datetime.LocalDateTime
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.util.Date
import kotlin.time.Duration.Companion.seconds

@Composable
fun TasksWorker(
    worker: Worker,
    navController: NavController,
    viewModel: TasksWorkerViewModel = hiltViewModel()
) {
    var themeMode by remember { mutableStateOf<ThemeMode>(ThemeMode.Dark) }
    WarehouseEmployeeTheme(themeMode = themeMode) {
        val taskList by viewModel.taskList.collectAsState(initial = emptyList())
        var taskStartColor by remember { mutableStateOf(Color.Transparent) }

        viewModel.getTaskList(worker)

        val (hours, minutes, seconds) = TimerStartTask(taskList)

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(WarehouseEmployeeTheme.colors.background)
                .padding(30.dp, 0.dp)
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

                    IconButton(
                        onClick = {
                            themeMode = if (themeMode == ThemeMode.Dark) ThemeMode.Light else ThemeMode.Dark                        }

                    ) {
                        if (themeMode == ThemeMode.Light) {
                            Icon(
                                painter = painterResource(id = R.drawable.dark_mode),
                                contentDescription = ""
                            )
                        }
                        else {
                            Icon(
                                painter = painterResource(id = R.drawable.light_mode),
                                contentDescription = ""
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
                    taskList
                ) { taskDate ->
//                    LaunchedEffect(taskStartColor, taskDate) {
//                        taskStartColor = Color.Green
//                    }
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
                                        onClick = {}
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
                        .fillMaxWidth(),
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
                        contentDescription = ""
                    )
                }
            }
        }
    }
}

@Composable
fun TimerStartTask(tasksList: List<Task>): Triple<Int, Int, Int> {
    var hours by remember { mutableStateOf(0) }
    var minutes by remember { mutableStateOf(0) }
    var seconds by remember { mutableStateOf(0) }
    var indexInList = 0;
    if (tasksList.isNotEmpty()) {
        for (taskIndex in tasksList.indices) {
            val targetDateTimeStrin = tasksList[taskIndex].dateExecutionTask
            val targetTimeString = targetDateTimeStrin.substring(11, 19)

            val sdf = SimpleDateFormat("HH:mm:ss")
            val targetDate = sdf.parse(targetTimeString)
            val currentDateAndTime = sdf.parse(sdf.format(Date()))
            if (targetDate > currentDateAndTime) {
                val differenceInMillis = targetDate.time - currentDateAndTime.time
                hours = (differenceInMillis / (1000 * 60 * 60)).toInt()
                minutes = ((differenceInMillis / (1000 * 60)) % 60).toInt()
                seconds = ((differenceInMillis / 1000) % 60).toInt()
                indexInList = taskIndex
                break;
            }
            else if (targetDate == currentDateAndTime) {
                indexInList = taskIndex + 1
            }
        }
    }
    LaunchedEffect(tasksList, indexInList)  {
        while (true) {
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
                if (indexInList < tasksList.size) {
                    val task = tasksList[indexInList]
                    val targetDateTimeStrin = task.dateExecutionTask
                    val targetTimeString = targetDateTimeStrin.substring(11, 19)

                    val sdf = SimpleDateFormat("HH:mm:ss")
                    val targetDate = sdf.parse(targetTimeString)
                    val currentDateAndTime = sdf.parse(sdf.format(Date()))

                    val differenceInMillis = targetDate.time - currentDateAndTime.time
                    hours = (differenceInMillis / (1000 * 60 * 60)).toInt() // Часы
                    minutes = ((differenceInMillis / (1000 * 60)) % 60).toInt() // Минуты
                    seconds = ((differenceInMillis / 1000) % 60).toInt() // Секунды
                } else {
                    // Все задачи завершены
                    break
                }
                break
            }
        }
    }

    return Triple(hours, minutes, seconds)
}