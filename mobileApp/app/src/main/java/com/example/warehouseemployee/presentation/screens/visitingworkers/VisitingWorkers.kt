package com.example.warehouseemployee.presentation.screens.visitingworkers

import android.widget.Toast
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
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
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
    val selectedValue = remember { mutableStateListOf<Worker?>(null) }

    val workersList by viewModel.workerList.collectAsState(initial = emptyList())

    viewModel.getWorkersForShift(worker.idWorker)

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
                .weight(1f)
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 40.dp, bottom = 20.dp)
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
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(20.dp))
                .weight(3f)
                .background(WarehouseEmployeeTheme.colors.background_second)
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp)
            ) {
                items(
                    workersList
                ) { workerDate ->
                    Spacer(modifier = Modifier.padding(top = 10.dp))
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(20.dp))
                            .background(WarehouseEmployeeTheme.colors.background_second_element)
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()

                        ) {
                            Column(modifier = Modifier.weight(3f)) {
                                Text(
                                    color = WarehouseEmployeeTheme.colors.text_color_second_element,
                                    style = WarehouseEmployeeTheme.typography.smallText.copy(
                                        fontSize = 20.sp
                                    ),
                                    textAlign = TextAlign.Center,
                                    text = "${workerDate.firstName} ${workerDate.lastName}\n${workerDate.patronymic}",
                                    modifier = Modifier

                                        .padding(start = 20.dp, top = 20.dp, bottom = 20.dp)
                                )
                            }


                            Column(modifier = Modifier.weight(1f)) {
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(15.dp))
                                        .background(Color.White)
                                ) {
                                    IconToggleButton(
                                        checked = selectedValue.contains(workerDate),
                                        onCheckedChange = { isChecked ->
                                            if (isChecked) {
                                                selectedValue.add(workerDate)
                                            }
                                        }) {
                                        if (selectedValue.contains(workerDate)) {
                                            Icon(
                                                painter = painterResource(id = R.drawable.check),
                                                contentDescription = "",
                                                tint = Color.Black
                                            )
                                        } else {
                                            Icon(
                                                painter = painterResource(id = R.drawable.not_check),
                                                contentDescription = "",
                                                tint = Color.Black

                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                    Spacer(modifier = Modifier.padding(bottom = 10.dp))
                }
            }
        }
        Button(

            modifier = Modifier
                .fillMaxWidth()
                .padding(0.dp, 20.dp),
            onClick = {

            },
            colors = ButtonDefaults.buttonColors(
                containerColor = WarehouseEmployeeTheme.colors.background_important_element,
                contentColor = WarehouseEmployeeTheme.colors.text_color_important_element
            ),
            contentPadding = PaddingValues(vertical = 30.dp),
            shape = RoundedCornerShape(20.dp)
        ) {
            Text(
                text = "К задачам",
                style = WarehouseEmployeeTheme.typography.primaryTitle,
                color = WarehouseEmployeeTheme.colors.text_color_important_element
            )
        }
    }
}