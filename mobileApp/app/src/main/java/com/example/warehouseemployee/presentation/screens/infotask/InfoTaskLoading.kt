package com.example.warehouseemployee.presentation.screens.infotask

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.currentCompositionErrors
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerIcon.Companion.Text
import androidx.compose.ui.input.pointer.motionEventSpy
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.warehouseemployee.R
import com.example.warehouseemployee.data.classes.Task
import com.example.warehouseemployee.data.classes.Worker
import com.example.warehouseemployee.presentation.navigathion.InfoCellDestination
import com.example.warehouseemployee.presentation.navigathion.MessagesDestination
import com.example.warehouseemployee.presentation.navigathion.OptimalPlanDestination
import com.example.warehouseemployee.presentation.navigathion.TasksWorkerDestination
import com.example.warehouseemployee.ui.theme.ThemeMode
import com.example.warehouseemployee.ui.theme.WarehouseEmployeeTheme
import kotlinx.datetime.Month
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Composable
fun InfoTaskLoading(
    navController: NavHostController,
    currentTask: Task,
    worker: Worker,
    themeUI: ThemeMode,
    viewModel: TasksWorkerViewModel = hiltViewModel()
) {
    val cellAndProductList by viewModel.cellAndProductList.collectAsState(listOf())
    val orderInOptimalPath by viewModel.orderInOptimalPath.collectAsState(listOf())
    val workerListToMessage by viewModel.workerListToMessage.collectAsState(listOf())
    var visibleChatMessages by remember { mutableStateOf(false) }

    viewModel.getTaskProduct(currentTask.id)
    viewModel.getWorkersToMessage(currentTask.id, worker)

    WarehouseEmployeeTheme(themeMode = themeUI) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .background(WarehouseEmployeeTheme.colors.background)
                .padding(horizontal = 30.dp)
                .clickable(
                    interactionSource = remember {
                        MutableInteractionSource()
                    },
                    indication = null
                ) {
                    visibleChatMessages = false
                }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 30.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .background(WarehouseEmployeeTheme.colors.background_important_element),

                ) {

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 20.dp, start = 20.dp, end = 20.dp)
                ) {
                    IconButton(
                        onClick = {
                            navController.navigate(
                                "${TasksWorkerDestination.route}/${
                                    Json.encodeToString(
                                        worker
                                    )
                                }/${themeUI}"
                            )
                        }
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.arrow_left),
                            contentDescription = "",
                            tint = WarehouseEmployeeTheme.colors.color_icon
                        )
                    }
                    Text(
                        text = currentTask.idCategoryTask.nameCategory,
                        style = WarehouseEmployeeTheme.typography.primaryTitle,
                        color = WarehouseEmployeeTheme.colors.text_color_important_element
                    )
                }
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp, bottom = 20.dp, start = 20.dp, end = 20.dp)
                ) {
                    Text(
                        text = "Ответственный\n${currentTask.idResponsibleWorker.firstName} ${currentTask.idResponsibleWorker.patronymic}",
                        style = WarehouseEmployeeTheme.typography.primaryTitle.copy(
                            fontSize = 12.sp
                        ),
                        color = WarehouseEmployeeTheme.colors.text_color_important_element

                    )
                    Text(
                        text = "${currentTask.dateExecutionTask.substring(11, 16)}",
                        style = WarehouseEmployeeTheme.typography.primaryTitle.copy(
                            fontSize = 24.sp
                        ),
                        color = WarehouseEmployeeTheme.colors.text_color_important_element

                    )
                }
            }

            Spacer(modifier = Modifier.padding(0.dp, 10.dp))
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(20.dp))
                    .background(WarehouseEmployeeTheme.colors.background_for_light_mode)
            ) {
                Text(
                    text = "Товар - Ячейка",
                    style = WarehouseEmployeeTheme.typography.secondText,
                    color = WarehouseEmployeeTheme.colors.text_color_second_element,
                    modifier = Modifier
                        .padding(0.dp, 10.dp)

                )
                if (currentTask.idCategoryTask.id == 1) {
                    LazyColumn(
                        modifier = Modifier
                    ) {
                        items(
                            cellAndProductList
                        ) { itemList ->

                            Row(
                                horizontalArrangement = Arrangement.SpaceAround,
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier

                                    .padding(10.dp, 10.dp)

                            ) {
                                Column(
                                    verticalArrangement = Arrangement.Center,
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(10.dp))
                                        .background(WarehouseEmployeeTheme.colors.background)
                                        .padding(10.dp, 10.dp)
                                        .weight(1.5f)
                                ) {
                                    Text(
                                        text = itemList.idProduct.article,
                                        style = WarehouseEmployeeTheme.typography.secondText.copy(
                                            fontSize = 14.sp
                                        ),
                                        color = WarehouseEmployeeTheme.colors.text_color_in_cell
                                    )
                                    Text(
                                        text = itemList.idProduct.productName,
                                        style = WarehouseEmployeeTheme.typography.secondText.copy(
                                            fontSize = 14.sp
                                        ),
                                        color = WarehouseEmployeeTheme.colors.text_color_in_cell
                                    )
                                    Text(
                                        text = "Кол-во: ${itemList.countProduct}",
                                        style = WarehouseEmployeeTheme.typography.secondText.copy(
                                            fontSize = 14.sp
                                        ),
                                        color = WarehouseEmployeeTheme.colors.text_color_in_cell
                                    )
                                    Text(
                                        text = "Вес: %.2f кг".format(itemList.idProduct.weight * itemList.countProduct),
                                        style = WarehouseEmployeeTheme.typography.secondText.copy(
                                            fontSize = 14.sp
                                        ),
                                        color = WarehouseEmployeeTheme.colors.text_color_in_cell
                                    )
                                }
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    modifier = Modifier
                                        .weight(1f)
                                ) {
                                    Image(
                                        painter = painterResource(id = R.drawable.arrow),
                                        contentDescription = ""
                                    )
                                }
                                Column(

                                    verticalArrangement = Arrangement.Center,
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    modifier = Modifier
                                        .weight(1f)
                                        .clip(RoundedCornerShape(10.dp))
                                        .background(WarehouseEmployeeTheme.colors.background)
                                        .padding(0.dp, 30.dp)
                                        .clickable {
                                            navController.navigate(
                                                "${InfoCellDestination.route}/${themeUI.title}/${Json.encodeToString(itemList.idCell)}/${Json.encodeToString(worker)}/${Json.encodeToString(currentTask)}"
                                            )
                                        }
                                ) {

                                    Text(
                                        text = itemList.idCell.abbreviatedName,
                                        style = WarehouseEmployeeTheme.typography.secondText,
                                        color = WarehouseEmployeeTheme.colors.text_color_in_cell,
                                        modifier = Modifier
                                    )
                                }
                            }
                        }
                    }
                }
                else {
                    LazyColumn(
                        modifier = Modifier
                    ) {
                        items(
                            cellAndProductList
                        ) { itemList ->

                            Row(
                                horizontalArrangement = Arrangement.SpaceAround,
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier

                                    .padding(10.dp, 10.dp)

                            ) {
                                Column(

                                    verticalArrangement = Arrangement.Center,
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    modifier = Modifier
                                        .weight(1f)
                                        .clip(RoundedCornerShape(10.dp))
                                        .background(WarehouseEmployeeTheme.colors.background)
                                        .padding(0.dp, 30.dp)
                                        .clickable {
                                            navController.navigate(
                                                "${InfoCellDestination.route}/${themeUI.title}/${Json.encodeToString(itemList.idCell)}/${Json.encodeToString(worker)}/${Json.encodeToString(currentTask)}"
                                            )
                                        }
                                ) {

                                    Text(
                                        text = itemList.idCell.abbreviatedName,
                                        style = WarehouseEmployeeTheme.typography.secondText,
                                        color = WarehouseEmployeeTheme.colors.text_color_in_cell,
                                        modifier = Modifier
                                    )
                                }
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    modifier = Modifier
                                        .weight(1f)
                                ) {
                                    Image(
                                        painter = painterResource(id = R.drawable.arrow),
                                        contentDescription = ""
                                    )
                                }
                                Column(
                                    verticalArrangement = Arrangement.Center,
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(10.dp))
                                        .background(WarehouseEmployeeTheme.colors.background)
                                        .padding(10.dp, 10.dp)
                                        .weight(1.5f)
                                ) {
                                    Text(
                                        text = itemList.idProduct.article,
                                        style = WarehouseEmployeeTheme.typography.secondText.copy(
                                            fontSize = 14.sp
                                        ),
                                        color = WarehouseEmployeeTheme.colors.text_color_in_cell
                                    )
                                    Text(
                                        text = itemList.idProduct.productName,
                                        style = WarehouseEmployeeTheme.typography.secondText.copy(
                                            fontSize = 14.sp
                                        ),
                                        color = WarehouseEmployeeTheme.colors.text_color_in_cell
                                    )
                                    Text(
                                        text = "Кол-во: ${itemList.countProduct}",
                                        style = WarehouseEmployeeTheme.typography.secondText.copy(
                                            fontSize = 14.sp
                                        ),
                                        color = WarehouseEmployeeTheme.colors.text_color_in_cell
                                    )
                                    Text(
                                        text = "Вес: %.2f кг".format(itemList.idProduct.weight * itemList.countProduct),
                                        style = WarehouseEmployeeTheme.typography.secondText.copy(
                                            fontSize = 14.sp
                                        ),
                                        color = WarehouseEmployeeTheme.colors.text_color_in_cell
                                    )
                                }


                            }
                        }
                    }
                }

            }
            Spacer(modifier = Modifier.padding(0.dp, 10.dp))
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(20.dp))
                    .background(WarehouseEmployeeTheme.colors.background_for_light_mode)
            ) {
                Text(
                    textAlign = TextAlign.Center,
                    text = "Оптимальный\nпуть",
                    style = WarehouseEmployeeTheme.typography.secondText,
                    color = WarehouseEmployeeTheme.colors.text_color_second_element,
                    modifier = Modifier
                        .padding(0.dp, 10.dp)

                )
                LazyColumn(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .padding(vertical = 5.dp)
                        .fillMaxWidth()
                ) {
                    items(
                        orderInOptimalPath
                    ) { itemList ->
                        if (itemList == orderInOptimalPath.last()) {
                            Row(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(20.dp))
                                    .background(WarehouseEmployeeTheme.colors.background)
                                    .padding(25.dp, 25.dp)
                            ) {
                                Text(
                                    text = itemList.idCell.abbreviatedName,
                                    color = WarehouseEmployeeTheme.colors.text_color_in_cell,
                                    style = WarehouseEmployeeTheme.typography.secondText
                                )
                            }
                        } else {
                            Row(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(20.dp))
                                    .background(WarehouseEmployeeTheme.colors.background)
                                    .padding(25.dp, 25.dp)
                            ) {
                                Text(
                                    text = itemList.idCell.abbreviatedName,
                                    color = WarehouseEmployeeTheme.colors.text_color_in_cell,
                                    style = WarehouseEmployeeTheme.typography.secondText
                                )
                            }
                            Spacer(modifier = Modifier.padding(0.dp, 5.dp))
                            Row(
                                modifier = Modifier
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.arrow_down),
                                    contentDescription = ""
                                )
                            }
                            Spacer(modifier = Modifier.padding(0.dp, 5.dp))
                        }

                    }
                }
            }
            Spacer(modifier = Modifier.padding(0.dp, 10.dp))

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .weight(0.5f)
                    .fillMaxWidth()
                    .padding(bottom = 50.dp)
            ) {
                Button(
                    onClick = {
                        navController.navigate(
                            "${OptimalPlanDestination.route}/${Json.encodeToString(worker)}/${Json.encodeToString(currentTask)}/${themeUI.title}"
                        )
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = WarehouseEmployeeTheme.colors.background_important_element,
                        contentColor = WarehouseEmployeeTheme.colors.text_color_important_element
                    ),
                    contentPadding = PaddingValues(vertical = 10.dp),
                    shape = RoundedCornerShape(20.dp),
                    modifier = Modifier
                        .padding(end = 20.dp)
                        .fillMaxSize()
                        .weight(3f)
                ) {

                    Text(
                        modifier = Modifier
                            .padding(10.dp, 0.dp),
                        text = "Посмотреть путь\nсхематично",
                        style = WarehouseEmployeeTheme.typography.primaryTitle.copy(
                            fontSize = 20.sp
                        ),
                        color = WarehouseEmployeeTheme.colors.text_color_important_element,
                        textAlign = TextAlign.Center
                    )
                }

                IconButton(
                    onClick = {
                        if (worker.idRole == 1) {
                            navController.navigate(
                                "${MessagesDestination.route}/${Json.encodeToString(worker)}/${Json.encodeToString(currentTask.idResponsibleWorker)}/${Json.encodeToString(currentTask)}/${themeUI.title}"
                            )
                        }
                        else {
                            visibleChatMessages = true
                        }
                    },
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f)
                        .clip(RoundedCornerShape(20.dp))
                        .background(WarehouseEmployeeTheme.colors.background_important_element)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.message),
                        contentDescription = "",
                        tint = WarehouseEmployeeTheme.colors.color_icon
                    )

                }

            }
        }
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
                                        "${MessagesDestination.route}/${Json.encodeToString(worker)}/${Json.encodeToString(workerToMessage)}/${Json.encodeToString(currentTask)}/${themeUI.title}"
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



