package com.example.warehouseemployee.data.classes

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TaskWorker (
    @SerialName("id")
    val id: Int,
    @SerialName("id_worker")
    val idWorker: Int,
    @SerialName("id_task")
    val idTask: Int,
    @SerialName("is_worker_completed")
    val isWorkerCompleted: Boolean
)