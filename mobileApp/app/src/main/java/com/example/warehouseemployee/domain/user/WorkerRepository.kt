package com.example.warehouseemployee.domain.user

import com.example.warehouseemployee.data.classes.Worker

interface WorkerRepository {
    suspend fun getWorker(workerId: String): Worker?
    suspend fun getWorkersForShift(mainWorkerId: String): List<Worker>
}