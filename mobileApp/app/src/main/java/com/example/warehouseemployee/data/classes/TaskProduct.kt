package com.example.warehouseemployee.data.classes

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TaskProduct (
    @SerialName("id")
    val id: Int,
    @SerialName("id_task")
    val idTask: Int,
    @SerialName("id_product")
    val idProduct: Product,
    @SerialName("id_cell")
    val idCell: Cell,
    @SerialName("count_product")
    val countProduct: Int,
    @SerialName("position_in_optimal_in_path")
    val positionInOptimaInPath: Int
)