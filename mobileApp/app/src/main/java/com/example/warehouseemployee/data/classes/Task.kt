package com.example.warehouseemployee.data.classes

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Task (
    @SerialName("id")
    val id: Int,
    @SerialName("id_category_task")
    val idCategoryTask: Int,
    @SerialName("id_responsible_worker")
    val idResponsibleWorker: Int,
    @SerialName("date_create")
    val dateCreate: String,
    @SerialName("img_optimal_path")
    val imgOptimalPath: String,
    @SerialName("date_execution_task")
    val dateExecutionTask: String,
    @SerialName("is_completed")
    val isCompleted: Boolean
)