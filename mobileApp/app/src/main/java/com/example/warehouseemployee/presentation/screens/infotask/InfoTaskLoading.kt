package com.example.warehouseemployee.presentation.screens.infotask

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
    viewModel.getTaskProduct(currentTask.id)
    WarehouseEmployeeTheme(themeMode = themeUI) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .background(WarehouseEmployeeTheme.colors.background)
                .padding(horizontal = 30.dp)
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
                            contentDescription = ""
                        )
                    }
                    Text(
                        text = currentTask.idCategoryTask.nameCategory,
                        style = WarehouseEmployeeTheme.typography.primaryTitle
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
                        )

                    )
                    Text(
                        text = "${currentTask.dateExecutionTask.substring(11, 16)}",
                        style = WarehouseEmployeeTheme.typography.primaryTitle.copy(
                            fontSize = 24.sp
                        )

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
                                    color = WarehouseEmployeeTheme.colors.text_color_second_element
                                )
                                Text(
                                    text = itemList.idProduct.productName,
                                    style = WarehouseEmployeeTheme.typography.secondText.copy(
                                        fontSize = 14.sp
                                    ),
                                    color = WarehouseEmployeeTheme.colors.text_color_second_element
                                )
                                Text(
                                    text = "Кол-во: ${itemList.countProduct}",
                                    style = WarehouseEmployeeTheme.typography.secondText.copy(
                                        fontSize = 14.sp
                                    ),
                                    color = WarehouseEmployeeTheme.colors.text_color_second_element
                                )
                                Text(
                                    text = "Вес: %.2f кг".format(itemList.idProduct.weight * itemList.countProduct),
                                    style = WarehouseEmployeeTheme.typography.secondText.copy(
                                        fontSize = 14.sp
                                    ),
                                    color = WarehouseEmployeeTheme.colors.text_color_second_element
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
                            ) {

                                Text(
                                    text = itemList.idCell.abbreviatedName,
                                    style = WarehouseEmployeeTheme.typography.secondText,
                                    color = WarehouseEmployeeTheme.colors.text_color_second_element,
                                    modifier = Modifier
                                )
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
                                    color = WarehouseEmployeeTheme.colors.text_color_second_element,
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
                                    color = WarehouseEmployeeTheme.colors.text_color_second_element,
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
                    onClick = {},
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f)

                        .clip(RoundedCornerShape(20.dp))
                        .background(WarehouseEmployeeTheme.colors.background_important_element)
                ) {

                    Icon(
                        painter = painterResource(id = R.drawable.message),
                        contentDescription = ""
                    )

                }

            }
        }
    }
}



