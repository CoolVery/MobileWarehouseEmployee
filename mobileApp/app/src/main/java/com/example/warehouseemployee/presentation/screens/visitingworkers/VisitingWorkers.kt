package com.example.warehouseemployee.presentation.screens.visitingworkers

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.warehouseemployee.R
import com.example.warehouseemployee.data.classes.Worker
import com.example.warehouseemployee.ui.theme.WarehouseEmployeeTheme

@Composable
fun VisitingWorkers(
    worker: Worker,
    navController: NavController,
    viewModel: VisitingWorkersViewModel = hiltViewModel()
) {
    val selectedValue = remember { mutableStateOf<Worker?>(null) }

    val workersList by viewModel.workerList.collectAsState(initial = emptyList())

    viewModel.getWorkersForShift(worker.idWorker)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(WarehouseEmployeeTheme.colors.background)
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            Box(

                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(30.dp, 50.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .background(WarehouseEmployeeTheme.colors.background_important_element)
            ) {
                Text(
                    text = "Присутствие\nработников",
                    style = WarehouseEmployeeTheme.typography.primaryTitle,
                    color = WarehouseEmployeeTheme.colors.text_color_important_element

                )
            }

        }
        LazyColumn (
            modifier = Modifier
                .fillMaxWidth()
                .weight(2f)
                .padding(30.dp, 10.dp)
                .background(WarehouseEmployeeTheme.colors.background_second)
        ) {
            items(
                workersList
            ) { workerDate ->
                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()

                        .padding(10.dp, 20.dp)

                        .background(WarehouseEmployeeTheme.colors.background_second_element)

                ) {

                        Text(
                            color = WarehouseEmployeeTheme.colors.text_color_second_element,
                            style = WarehouseEmployeeTheme.typography.smallText.copy(fontSize = 20.sp),
                            textAlign = TextAlign.Center,
                            text = "${workerDate.firstName} ${workerDate.lastName}\n${workerDate.patronymic}",
                            modifier = Modifier

                                .padding(horizontal = 0.dp, vertical = 20.dp)
                        )



                    IconToggleButton(
                        checked = selectedValue.value == workerDate,
                        onCheckedChange = { isChecked ->
                            if (isChecked) {
                                selectedValue.value =
                                    workerDate
                            } else {
                                selectedValue.value = null
                            }
                        }) {
                        if (selectedValue.value == workerDate) {
                            Icon(
                                painter = painterResource(id = R.drawable.check),
                                contentDescription = ""
                            )
                        } else {
                            Icon(
                                painter = painterResource(id = R.drawable.eye_off),
                                contentDescription = ""
                            )
                        }
                    }
                }

            }
        }
    }
}