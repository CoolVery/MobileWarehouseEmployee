package com.example.warehouseemployee.data.classes

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MessageInChat (
    @SerialName("id")
    val id: Int,
    @SerialName("id_chat")
    val idChat: Int,
    @SerialName("id_worker_sender")
    val idWorkerSender: Int,
    @SerialName("content_message")
    val contentMessage: String
)