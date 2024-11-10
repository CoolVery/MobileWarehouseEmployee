package com.example.warehouseemployee.domain.user

import com.example.warehouseemployee.data.classes.Worker
import com.example.warehouseemployee.data.classes.WorkersWorkShift

interface WorkerRepository {
    suspend fun getWorker(workerId: String): Worker?
    suspend fun getWorkersForShift(mainWorkerId: String): List<WorkersWorkShift>
    suspend fun updateIsCameShiftWorker(workersWorkShiftList: List<WorkersWorkShift>): Boolean
}