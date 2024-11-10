package com.example.warehouseemployee.data.classes

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WorkersWorkShift (
    @SerialName("id")
    val id: Int,
    @SerialName("id_worker")
    val idWorker: Worker,
    @SerialName("id_work_shift")
    val idWorkShift: Int,
    @SerialName("is_came")
    var isCame: Boolean
)