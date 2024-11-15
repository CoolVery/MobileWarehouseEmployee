package com.example.warehouseemployee.presentation.screens.infotask

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.warehouseemployee.R
import com.example.warehouseemployee.data.classes.Task
import com.example.warehouseemployee.data.classes.Worker
import com.example.warehouseemployee.ui.theme.WarehouseEmployeeTheme
import kotlinx.datetime.Month

@Composable
fun InfoTaskLoading(
    navController: NavHostController,
    currentTask: Task,
    worker: Worker,
    viewModel: TasksWorkerViewModel = hiltViewModel()
) {
    val cellAndProductList by viewModel.cellAndProductList.collectAsState(listOf())

    viewModel.getTaskProduct(currentTask.id)

    Column (
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .background(WarehouseEmployeeTheme.colors.background)
            .padding(horizontal = 30.dp)
    ){
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
                    onClick = {}
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
                        fontSize = 16.sp
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
        Column (
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .background(WarehouseEmployeeTheme.colors.background_for_light_mode)
                .fillMaxWidth()
        ) {
            Text(
                text = "Товар - Ячейка"
            )
            LazyColumn(

            ) {
                items(
                    cellAndProductList
                ) { itemList ->
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(0.4f)

                            .background(Color.Green)

                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxSize()
                                .fillMaxHeight(1f
                                )
                                .border(
                                    width = 3.dp,
                                    color = Color.Red
                                )
                        ) {


                            Column(
                                modifier = Modifier
                                    .weight(1f)
                                    .fillMaxHeight(1f)
                                    .border(
                                        width = 3.dp,
                                        color = Color.Red
                                    )
                            ) {


                                Text(
                                    text = itemList.idProduct.article,
                                    style = WarehouseEmployeeTheme.typography.secondText,
                                    color = WarehouseEmployeeTheme.colors.text_color_second_element
                                )
                                Text(
                                    text = itemList.idProduct.productName,
                                    style = WarehouseEmployeeTheme.typography.secondText,
                                    color = WarehouseEmployeeTheme.colors.text_color_second_element
                                )
                                Text(
                                    text = "Кол-во: ${itemList.countProduct}",
                                    style = WarehouseEmployeeTheme.typography.secondText,
                                    color = WarehouseEmployeeTheme.colors.text_color_second_element
                                )
                                Text(
                                    text = "Вес: ${itemList.idProduct.weight * itemList.countProduct} кг",
                                    style = WarehouseEmployeeTheme.typography.secondText,
                                    color = WarehouseEmployeeTheme.colors.text_color_second_element
                                )
                            }
                            Column(
                                modifier = Modifier
                                    .weight(1f)
                                    .fillMaxHeight()
                                    .border(
                                        width = 3.dp,
                                        color = Color.Red
                                    )
                            ) {
                                Box(
                                    modifier = Modifier
                                        .border(

                                            width = 3.dp,
                                            color = Color.Red
                                        )
                                ) {
                                    Image(
                                        modifier = Modifier,
                                        painter = painterResource(id = R.drawable.arrow),
                                        contentDescription = ""
                                    )
                                }

                                Box(
                                    modifier = Modifier
                                        .border(
                                            width = 3.dp,
                                            color = Color.Red
                                        )
                                        .fillMaxSize()
                                        .background(WarehouseEmployeeTheme.colors.background)
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

        }
    }
    LazyColumn { }
}
}