package com.example.warehouseemployee.domain.task

import com.example.warehouseemployee.data.classes.Task
import com.example.warehouseemployee.data.classes.TaskProduct
import com.example.warehouseemployee.data.classes.Worker

interface TaskRepository {
    suspend fun getTasksWorker(worker: Worker): List<Task>
    suspend fun getTasksMainWorker(worker: Worker): List<Task>
    suspend fun getTaskProducts(taskId: Int): List<TaskProduct>
}