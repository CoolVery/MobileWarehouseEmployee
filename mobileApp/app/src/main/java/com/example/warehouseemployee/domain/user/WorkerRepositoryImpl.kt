package com.example.warehouseemployee.domain.user

import android.util.Log
import androidx.compose.foundation.layout.Column
import com.example.warehouseemployee.data.classes.WorkShift
import com.example.warehouseemployee.data.classes.Worker
import com.example.warehouseemployee.data.classes.WorkersWorkShift
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
            //Получаем рабочего по его uuid
            withContext(Dispatchers.IO) {
                val result = postgrest.from("workers")
                    .select() {
                        filter {
                            eq("id_worker", workerId)
                        }
                    }.decodeSingle<Worker>()
                result
            }
        //Если ошибка, то вернет null
        } catch (e: Exception) {
            null
        }
    }

    override suspend fun getWorkersForShift(mainWorkerId: String): List<WorkersWorkShift> {
        return try {
            withContext(Dispatchers.IO) {
                //Создаем формат даты и в ней получаем дату с телефона
                val sdf = SimpleDateFormat("yyyy-MM-dd")
                val currentDateAndTime = sdf.format(Date())
                //Получаем смену, где главный по смене - наш рабочий, а дата смены совпадает с сегодняшней датой
                val main_user_on_shift = postgrest.from("work_shifts")
                    .select {
                        filter {
                            eq("id_main_shift_worker", mainWorkerId)
                            eq("date_shift", currentDateAndTime)
                        }
                    }.decodeSingle<WorkShift>()
                //Получаем рабочих из таблицы Много ко Многим, где id смены - id сегодняшняя смена
                val result = postgrest.from("workers_work_shifts")
                    .select (
                        Columns.raw("id, id_work_shift, id_worker(id_worker, first_name, last_name, patronymic, id, id_role, id_warehouse), is_came")
                    ){
                        filter {
                            eq("id_work_shift", main_user_on_shift.id)
                        }
                    }.decodeList<WorkersWorkShift>()
                result
            }
        //Если ошибка, то вернет пустой лист
        } catch (e: Exception) {
            listOf()
        }
    }

    override suspend fun updateIsCameShiftWorker(
        workersWorkShiftList: List<WorkersWorkShift>
    ): Boolean {
        return try {
            withContext(Dispatchers.IO) {
                //Для каждого рабочего из списка обновляем поле (он пришел) на true
                for (worker in workersWorkShiftList) {
                    if (worker.isCame) {
                        postgrest.from("workers_work_shifts").update(
                            {
                                set("is_came", true)
                            }
                        ) {
                            filter {
                                eq("id", worker.id)
                            }
                        }
                    }
                }
            }
            true
        }
        catch (e: Exception) {
            false
        }
    }
}