package com.example.warehouseemployee.domain.messages

import com.example.warehouseemployee.data.classes.MessageInChat
import com.example.warehouseemployee.data.classes.Worker

//Интерфейс репозитория, где будут перечислены абстрактные методы для сообщений пользователей
interface MessagesRepository {
    /**
     * Получить айди чата рабочих
     *
     * @param sendWorker рабочий, который отправляет сообщения
     * @param recipientWorker рабочий, который получит сообщения
     *
     * @return ID чата этих рабочих
     */
    suspend fun getChatIDToWorkers(sendWorker: Worker, recipientWorker: Worker): Int
    /**
     * Добавить новое сообщение
     *
     * @param newMessage объект класса MessageInChat
     *
     * @return Отправилось сообщение (false - ошибок нет) или нет (true - ошибки есть)
     */
    suspend fun insertNewMessages(newMessage: MessageInChat): Boolean
}