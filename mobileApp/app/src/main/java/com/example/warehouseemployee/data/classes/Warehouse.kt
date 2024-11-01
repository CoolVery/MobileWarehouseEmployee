package com.example.warehouseemployee.data.classes

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Warehouse (
    @SerialName("id")
    val id: Int,
    @SerialName("name_warehouse")
    val nameWarehouse: String,
    @SerialName("location")
    val location: String
)