package com.example.warehouseemployee.presentation.screens.tasks

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.warehouseemployee.data.classes.Worker

@Composable
fun TasksWorker(
    worker: Worker,
    navController: NavController
) {
    Text(
        text = worker.lastName
    )
}