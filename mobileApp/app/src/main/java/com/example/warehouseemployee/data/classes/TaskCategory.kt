package com.example.warehouseemployee.data.classes

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TaskCategory (
    @SerialName("id")
    val id: Int,
    @SerialName("name_category")
    val nameCategory: String
)