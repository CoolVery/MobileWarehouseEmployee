package com.example.warehouseemployee.domain.user

import androidx.compose.foundation.layout.Column
import com.example.warehouseemployee.data.classes.WorkShift
import com.example.warehouseemployee.data.classes.Worker
import com.example.warehouseemployee.domain.auth.AuthenticationRepository
import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.auth.exception.AuthRestException
import io.github.jan.supabase.auth.providers.builtin.Phone
import io.github.jan.supabase.exceptions.HttpRequestException
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.query.Columns
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.datetime.LocalDate
import java.text.SimpleDateFormat
import java.util.Date
import javax.inject.Inject

class WorkerRepositoryImpl @Inject constructor(
    private val postgrest: Postgrest
) : WorkerRepository {
    override suspend fun getWorker(workerId: String): Worker? {
        return try {
            withContext(Dispatchers.IO) {
                val result = postgrest.from("workers")
                    .select() {
                        filter {
                            eq("id_worker", workerId)
                        }
                    }.decodeSingle<Worker>()
                result
            }
        } catch (e: Exception) {
            null
        }
    }

    override suspend fun getWorkersForShift(mainWorkerId: String): List<Worker> {
        return try {
            withContext(Dispatchers.IO) {
                val sdf = SimpleDateFormat("yyyy-MM-dd")
                val currentDateAndTime = sdf.format(Date())

                val main_user_on_shift = postgrest.from("work_shifts")
                    .select {
                        filter {
                            eq("id_main_shift_worker", mainWorkerId)
                            eq("date_shift", currentDateAndTime)
                        }
                    }.decodeSingle<WorkShift>()
                val result = postgrest.from("workers_work_shifts")
                    .select {
                        filter {
                            eq("id_work_shift", main_user_on_shift.id)
                        }
                    }.decodeList<Worker>()
                result
            }
        } catch (e: Exception) {
            listOf()
        }
    }
}