package com.example.warehouseemployee.data.classes

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WorkShift (
    @SerialName("id") val id: Int,
    @SerialName("date_shift") val dateShift: String,
    @SerialName("id_warehouse") val idWarehouse: Int,
    @SerialName("id_main_shift_worker") val idMainShiftWorker: String
)
