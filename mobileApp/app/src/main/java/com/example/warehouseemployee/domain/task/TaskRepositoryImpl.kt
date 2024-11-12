package com.example.warehouseemployee.domain.task

import com.example.warehouseemployee.data.classes.Task
import com.example.warehouseemployee.data.classes.TaskWorker
import com.example.warehouseemployee.data.classes.Worker
import com.example.warehouseemployee.presentation.screens.tasks.TasksWorker
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.query.Columns
import io.github.jan.supabase.postgrest.query.filter.FilterOperator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.datetime.LocalDate
import java.text.SimpleDateFormat
import java.util.Date
import javax.inject.Inject

class TaskRepositoryImpl @Inject constructor(
    private val postgrest: Postgrest
) : TaskRepository {
    override suspend fun getTasksWorker(worker: Worker): List<Task> {
        return try {
            withContext(Dispatchers.IO) {
                val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                val currentDateAndTime = sdf.format(Date())
                val startOfDay = currentDateAndTime.substring(0, 10) + " 00:00:00"
                val endOfDay = currentDateAndTime.substring(0, 10) + " 23:59:59"

                val tempWorkerTasks = postgrest.from("tasks_workers").select {
                    filter {
                        eq("id_worker", worker.idWorker)
                    }
                }.decodeList<TaskWorker>().map { it.id }

                val result = postgrest.from("tasks").select(
                    Columns.raw("id, " +
                            "id_category_task(id, name_category), " +
                            "id_responsible_worker(id_worker, first_name, last_name, patronymic, id, id_role, id_warehouse), " +
                            "date_create, " +
                            "img_optimal_path, " +
                            "date_execution_task, " +
                            "is_completed")
                ) {
                    filter {
                        and {
                            isIn("id", tempWorkerTasks)
                            gte("date_execution_task", startOfDay)
                            lte("date_execution_task", endOfDay)
                        }
                    }
                }.decodeList<Task>()
                result
            }
        }
        catch (e: Exception) {
            listOf()
        }
    }
    override suspend fun getTasksMainWorker(worker: Worker): List<Task> {
        return try {
            withContext(Dispatchers.IO) {
                val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                val currentDateAndTime = sdf.format(Date())
                val startOfDay = currentDateAndTime.substring(0, 10) + " 00:00:00"
                val endOfDay = currentDateAndTime.substring(0, 10) + " 23:59:59"

                val result = postgrest.from("tasks").select(
                    Columns.raw("id, " +
                            "id_category_task(id, name_category), " +
                            "id_responsible_worker(id_worker, first_name, last_name, patronymic, id, id_role, id_warehouse), " +
                            "date_create, " +
                            "img_optimal_path, " +
                            "date_execution_task, " +
                            "is_completed")
                ) {
                    filter {
                        and {
                            gte("date_execution_task", startOfDay)
                            lte("date_execution_task", endOfDay)
                            eq("id_responsible_worker", worker.idWorker)
                        }
                    }
                }.decodeList<Task>()
                result
            }
        }
        catch (e: Exception) {
            listOf()
        }
    }
}