package com.example.warehouseemployee.domain.user

import com.example.warehouseemployee.data.classes.Worker
import com.example.warehouseemployee.data.classes.WorkersWorkShift

interface WorkerRepository {
    /**
     * Получить рабочего по его uuid
     *
     * @param workerId uuid рабочего
     *
     * @return Рабочий (может вернуть null)
     */
    suspend fun getWorker(workerId: String): Worker?
    /**
     * Получить рабочих, которые должны прийти на смену
     *
     * @param mainWorkerId uuid Главного по смене
     *
     * @return Список рабочих, которые должны прийти на смену
     */
    suspend fun getWorkersForShift(mainWorkerId: String): List<WorkersWorkShift>
    /**
     * Обновить посещение рабочего на смене
     *
     * @param workersWorkShiftList список рабочих, которые пришли на смену
     *
     * @return Вернет успешность обновления
     */
    suspend fun updateIsCameShiftWorker(workersWorkShiftList: List<WorkersWorkShift>): Boolean
}