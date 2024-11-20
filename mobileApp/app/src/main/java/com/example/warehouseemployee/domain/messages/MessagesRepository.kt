package com.example.warehouseemployee.domain.messages

import com.example.warehouseemployee.data.classes.MessageInChat
import com.example.warehouseemployee.data.classes.Worker


interface MessagesRepository {
    suspend fun getMessagesWorkers(sendWorker: Worker, recipientWorker: Worker): Int
}