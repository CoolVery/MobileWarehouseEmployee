package com.example.warehouseemployee.domain.task

import com.example.warehouseemployee.data.classes.Task
import com.example.warehouseemployee.data.classes.TaskProduct
import com.example.warehouseemployee.data.classes.Worker

//Интерфейс репозитория, где будут перечислены абстрактные методы для задач
interface TaskRepository {
    /**
     * Получить задачи Обычного рабочего
     *
     * @param worker рабочий, который авторизировался
     *
     * @return Список его задач
     */
    suspend fun getTasksWorker(worker: Worker): List<Task>
    /**
     * Получить задачи Главного по смене
     *
     * @param worker рабочий, который авторизировался
     *
     * @return Список его задач
     */
    suspend fun getTasksMainWorker(worker: Worker): List<Task>
    /**
     * Получить информацию о ячейках и продуктах в задаче
     *
     * @param taskId id задачи
     *
     * @return Список ячеек и продуктов, участвующие в задаче
     */
    suspend fun getTaskProducts(taskId: Int): List<TaskProduct>
    /**
     * Получить рабочих, которые должны выполнять задачи
     *
     * @param listTask список задач
     *
     * @return Список рабочих, которые должны выполнить задачи
     */
    suspend fun getWorkersTask(listTask: List<Task>): List<Worker>
    /**
     * Получить рабочих, которые должны выполнить Одну задачу
     *
     * @param taskID id задачи
     *
     * @return Список рабочих, которые должны выполнить задачу
     */
    suspend fun getWorkersInOneTask(taskID: Int): List<Worker>
    /**
     * Получить задачу по ее id
     *
     * @param taskID id задачи
     *
     * @return Объект задачи (может вернуть null)
     */
    suspend fun getTaskById(taskID: Int): Task?
}