package com.example.warehouseemployee.data.classes

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TaskProduct (
    @SerialName("id")
    val id: Int,
    @SerialName("id_product")
    val idProduct: Int,
    @SerialName("count_call")
    val countCall: Int,
    @SerialName("count_product")
    val countProduct: Int,
    @SerialName("position_in_optimal_list")
    val positionInOptimalList: Int
)