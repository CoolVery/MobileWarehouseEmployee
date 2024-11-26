package com.example.warehouseemployee.domain.task

import android.util.Log
import com.example.warehouseemployee.data.classes.Task
import com.example.warehouseemployee.data.classes.TaskCategory
import com.example.warehouseemployee.data.classes.TaskProduct
import com.example.warehouseemployee.data.classes.TaskWorker
import com.example.warehouseemployee.data.classes.Worker
import com.example.warehouseemployee.presentation.screens.tasks.TasksWorker
import io.github.jan.supabase.auth.exception.AuthRestException
import io.github.jan.supabase.exceptions.HttpRequestException
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
                //Создаем формат даты
                val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                //Получаем дату с телефона и преобразуем ее в наш формат
                val currentDateAndTime = sdf.format(Date())
                //Создаем начало и конец даты - это подстрока с Днем, Месяцем, Годом и время начала и конца дня
                val startOfDay = currentDateAndTime.substring(0, 10) + " 00:00:00"
                val endOfDay = currentDateAndTime.substring(0, 10) + " 23:59:59"
                //Т.к. обычные рабочие и задачи перечислены в таблице Много ко многим, то
                //Получаем задачи, где упоминается наш рабочий, после чего в листе оставляем только id этих задач через map
                val tempWorkerTasks = postgrest.from("tasks_workers").select (Columns.raw(
                    "id, id_worker(id, id_worker, id_role, first_name, last_name, patronymic, id_warehouse), id_task, is_worker_completed"
                )) {
                    filter {
                        eq("id_worker", worker.idWorker)
                    }
                }.decodeList<TaskWorker>().map { it.id }
                //Получаем теперь Задачи, у которых id содержится в листе выше, а дата выполнения попадает в диапазон дат
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
        //Если произошла ошибка, то вернется пустой лист
        catch (e: HttpRequestException) {
            listOf()
        } catch (e: Exception) {
            listOf()
        }
    }
    override suspend fun getTasksMainWorker(worker: Worker): List<Task> {
        return try {
            withContext(Dispatchers.IO) {
                //Создаем формат и делаем даты конца и начала даты
                val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                val currentDateAndTime = sdf.format(Date())
                val startOfDay = currentDateAndTime.substring(0, 10) + " 00:00:00"
                val endOfDay = currentDateAndTime.substring(0, 10) + " 23:59:59"
                //Т.к. главные рабочие по задаче сразу прописаны в задачах, то делаем запрос, где дата выполнения в диапазоне дат
                //А главный рабочий задачи - наш рабочий
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
        //Если произошла ошибка, то вернется пустой лист
        catch (e: Exception) {
            listOf()
        }
    }

    override suspend fun getTaskProducts(taskId: Int): List<TaskProduct> {
        return try {
            withContext(Dispatchers.IO) {
                //Делаем запрос к таблице, где id задачи - наш id
                val result = postgrest.from("tasks_products").select (
                    Columns.raw("id," +
                            "id_task," +
                            "id_product(id, product_name, article, weight, id_product_category)," +
                            "id_cell(id, id_section, id_product(id, product_name, article, weight, id_product_category), name_cell, max_count_product_in_cell, count_product_in_cell, weight_product_in_cell, abbreviated_name)," +
                            "count_product," +
                            "position_in_optimal_in_path")
                ) {
                    filter {
                        eq("id_task", taskId)
                    }
                }.decodeList<TaskProduct>()
                result
            }
        }
        //Если произошла ошибка, то вернется пустой лист
        catch (e: Exception) {
            listOf()
        }
    }

    override suspend fun getWorkersTask(listTask: List<Task>): List<Worker> {
        return try {
            //Нам нужны только id задач, поэтому через map преобразуем лист, оставив там только id
            val listIdTask = listTask.map { it.id }
            //Получаем из таблицы Много ко Многим те записи, где id задачи находится в списке выше
            val result = postgrest.from("tasks_workers").select(
                Columns.raw(
                    "id, id_worker(id, id_worker, id_role, first_name, last_name, patronymic, id_warehouse), id_task, is_worker_completed"
                )
            ){
                filter {
                    isIn("id_task", listIdTask)
                }
            }.decodeList<TaskWorker>()
            //Т.к. нам нужны только uuid рабочих, то с помощью map оставляем только их
            val resultWorkers = result.map { it.idWorker }
            resultWorkers
        }
        //Если произошла ошибка, то вернется пустой лист
        catch (e: Exception) {
            listOf()
        }
    }

    override suspend fun getWorkersInOneTask(taskID: Int): List<Worker> {
        return try {
            //Получаем из таблицы Много ко Многим те записи, где id задачи - наш id
            val taskWorkersList = postgrest.from("tasks_workers").select (
                Columns.raw(
                    "id, id_worker(id, id_worker, id_role, first_name, last_name, patronymic, id_warehouse), id_task, is_worker_completed"
                )
            ) {
                filter {
                    eq("id_task", taskID)
                }
            }.decodeList<TaskWorker>()
            //Т.к. нам нужны только uuid рабочих, то с помощью map оставляем только их
            val result = taskWorkersList.map { it.idWorker }
            result
        }
        //Если произошла ошибка, то вернется пустой лист
        catch (e: Exception) {
            listOf()
        }
    }

    override suspend fun getTaskById(taskID: Int): Task? {
        return try {
            //Получаем задачу по ее id
            val result = postgrest.from("tasks").select (
                Columns.raw("id, " +
                        "id_category_task(id, name_category), " +
                        "id_responsible_worker(id_worker, first_name, last_name, patronymic, id, id_role, id_warehouse), " +
                        "date_create, " +
                        "img_optimal_path, " +
                        "date_execution_task, " +
                        "is_completed")
            ) {
                filter {
                    eq("id", taskID)
                }
            }.decodeSingle<Task>()
            result
        }
        //Если произошла ошибка, то вернется null
        catch (e: Exception) {
            null
        }
    }
}