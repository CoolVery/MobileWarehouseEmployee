package com.example.warehouseemployee.presentation.screens.tasks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.warehouseemployee.data.classes.Task
import com.example.warehouseemployee.data.classes.TaskWorker
import com.example.warehouseemployee.data.classes.Worker
import com.example.warehouseemployee.data.classes.WorkersWorkShift
import com.example.warehouseemployee.domain.task.TaskRepository
import com.example.warehouseemployee.domain.user.WorkerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TasksWorkerViewModel @Inject constructor(
    private val taskRepository: TaskRepository
) : ViewModel() {
    //Лист задач
    private val _taskList = MutableStateFlow<List<Task>>(listOf())
    val taskList: Flow<List<Task>> = _taskList
    //Лист рабочих, которым можно написать
    private var _workerListToMessage = MutableStateFlow<List<Worker>>(listOf())
    var workerListToMessage: Flow<List<Worker>> = _workerListToMessage

    val taskListValue: List<Task>
        get() = _taskList.value
    fun getTaskList(worker: Worker) {
        viewModelScope.launch {
            //Если роль 2 - получаем задачи для главного по смене и работников по задачам
            if (worker.idRole == 2) {
                _taskList.value = taskRepository.getTasksMainWorker(worker)
                _workerListToMessage.value = taskRepository.getWorkersTask(_taskList.value)
            }
            else {
                //Иначе получаем задачи работника, а в лист работников для сообщениц кладем всех главный работников по задаче
                val newWorkerList = mutableListOf<Worker>()
                _taskList.value = taskRepository.getTasksWorker(worker)
                for (task in _taskList.value) {
                    newWorkerList.add(task.idResponsibleWorker)
                }
                _workerListToMessage.value = newWorkerList
            }
        }
    }
}