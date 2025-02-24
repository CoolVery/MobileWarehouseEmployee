package com.example.warehouseemployee.data.classes

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class Chat (
    @SerialName("id")
    val id: Int,
    @SerialName("worker_first")
    val workerFirst: String,
    @SerialName("worker_second")
    val workerSecond: String
)