package com.example.warehouseemployee.presentation.screens.infocell

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.pointer.motionEventSpy
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.warehouseemployee.R
import com.example.warehouseemployee.data.classes.Cell
import com.example.warehouseemployee.data.classes.Task
import com.example.warehouseemployee.data.classes.Worker
import com.example.warehouseemployee.presentation.navigathion.InfoCellDestination
import com.example.warehouseemployee.presentation.navigathion.InfoTaskLoadingDestination
import com.example.warehouseemployee.presentation.navigathion.TasksWorkerDestination
import com.example.warehouseemployee.ui.theme.ThemeMode
import com.example.warehouseemployee.ui.theme.WarehouseEmployeeTheme
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Composable
fun InfoCell(
    navController: NavHostController,
    themeUI: ThemeMode,
    cell: Cell,
    worker: Worker,
    task: Task,
    viewModel: InfoCellViewModel = hiltViewModel()
) {
    val section by viewModel.section.collectAsState(null)

    viewModel.getSection(cell)

    WarehouseEmployeeTheme(
        themeMode = themeUI
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(WarehouseEmployeeTheme.colors.background)
                .padding(30.dp, 0.dp)

        ) {
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .padding(0.dp, 50.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .clip(RoundedCornerShape(20.dp))
                        .background(WarehouseEmployeeTheme.colors.background_important_element)
                        .padding(0.dp, 50.dp)
                ) {

                    IconButton(
                        modifier = Modifier
                            .padding(start = 20.dp),
                        onClick = {
                            navController.navigate("${InfoTaskLoadingDestination.route}/${Json.encodeToString(worker)}/${Json.encodeToString(task)}/${themeUI.title}")
                        }
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.arrow_left),
                            contentDescription = ""
                        )
                    }
                    Text(
                        textAlign = TextAlign.Center,
                        text = cell.abbreviatedName,
                        style = WarehouseEmployeeTheme.typography.primaryTitle.copy(
                            fontSize = 40.sp
                        ),
                        color = WarehouseEmployeeTheme.colors.text_color_important_element,
                        modifier = Modifier
                            .weight(2f)
                            .padding(end = 20.dp),
                    )
                }
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(20.dp))
                    .background(WarehouseEmployeeTheme.colors.background_for_light_mode)
            ) {
                Spacer(modifier = Modifier.padding(vertical = 10.dp))
                Text(
                    text = "Товар",
                    style = WarehouseEmployeeTheme.typography.secondText,
                    color = WarehouseEmployeeTheme.colors.text_color_second_element,

                    )
                Spacer(modifier = Modifier.padding(vertical = 10.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp, 0.dp)
                        .clip(RoundedCornerShape(20.dp))
                        .background(WarehouseEmployeeTheme.colors.background)

                ) {
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = 10.dp)
                    ) {
                        Spacer(modifier = Modifier.padding(vertical = 10.dp))
                        Text(
                            text = cell.idProduct.article,
                            style = WarehouseEmployeeTheme.typography.secondText.copy(
                                fontSize = 14.sp
                            ),
                            color = WarehouseEmployeeTheme.colors.text_color_in_cell,
                            modifier = Modifier
                                .padding(bottom = 20.dp)
                        )
                        Text(
                            text = "Кол-во: ${cell.countProductInCell}",
                            style = WarehouseEmployeeTheme.typography.secondText.copy(
                                fontSize = 14.sp
                            ),
                            color = WarehouseEmployeeTheme.colors.text_color_in_cell
                        )
                    }
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .padding(end = 10.dp)
                    ) {
                        Spacer(modifier = Modifier.padding(vertical = 10.dp))
                        Text(
                            text = cell.idProduct.productName,
                            style = WarehouseEmployeeTheme.typography.secondText.copy(
                                fontSize = 14.sp
                            ),
                            color = WarehouseEmployeeTheme.colors.text_color_in_cell,
                            modifier = Modifier
                                .padding(bottom = 20.dp)
                        )
                        Text(
                            text = "Вес: ${cell.weightProductInCell}",
                            style = WarehouseEmployeeTheme.typography.secondText.copy(
                                fontSize = 14.sp
                            ),
                            color = WarehouseEmployeeTheme.colors.text_color_in_cell,

                        )
                        Spacer(modifier = Modifier.padding(vertical = 10.dp))

                    }

                }
                Spacer(modifier = Modifier.padding(vertical = 10.dp))

                Text(
                    text = "Секция",
                    style = WarehouseEmployeeTheme.typography.secondText,
                    color = WarehouseEmployeeTheme.colors.text_color_second_element,

                    )
                Spacer(modifier = Modifier.padding(vertical = 10.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp, 0.dp)
                        .clip(RoundedCornerShape(20.dp))
                        .background(WarehouseEmployeeTheme.colors.background)
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Spacer(modifier = Modifier.padding(vertical = 10.dp))
                        Text(
                            text = "Номер: ${section?.id ?: "Неизвестно"}",
                            style = WarehouseEmployeeTheme.typography.secondText.copy(
                                fontSize = 14.sp
                            ),
                            color = WarehouseEmployeeTheme.colors.text_color_in_cell
                        )
                        Spacer(modifier = Modifier.padding(vertical = 10.dp))

                        Text(
                            text = "Название: ${section?.nameSection ?: "Неизвестно"}",
                            style = WarehouseEmployeeTheme.typography.secondText.copy(
                                fontSize = 14.sp
                            ),
                            color = WarehouseEmployeeTheme.colors.text_color_in_cell
                        )
                        Spacer(modifier = Modifier.padding(vertical = 10.dp))

                    }

                }
                Spacer(modifier = Modifier.padding(vertical = 20.dp))

            }
        }
    }
}