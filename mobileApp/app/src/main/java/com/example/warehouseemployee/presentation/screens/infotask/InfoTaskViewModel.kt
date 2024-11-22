package com.example.warehouseemployee.presentation.screens.infotask

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.warehouseemployee.data.classes.TaskProduct
import com.example.warehouseemployee.data.classes.Worker
import com.example.warehouseemployee.domain.task.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TasksWorkerViewModel @Inject constructor(
    private val taskRepository: TaskRepository
) : ViewModel() {
    private val _cellAndProductList = MutableStateFlow<List<TaskProduct>>(listOf())
    val cellAndProductList: Flow<List<TaskProduct>> = _cellAndProductList

    private var _workerListToMessage = MutableStateFlow<List<Worker>>(listOf())
    var workerListToMessage: Flow<List<Worker>> = _workerListToMessage

    private val _orderInOptimalPath = MutableStateFlow<List<TaskProduct>>(listOf())
    val orderInOptimalPath: Flow<List<TaskProduct>> = _orderInOptimalPath

    fun getTaskProduct(taskId: Int) {
        viewModelScope.launch {
            val result = taskRepository.getTaskProducts(taskId)
            _cellAndProductList.value = result
            _orderInOptimalPath.value = result.sortedBy { it.positionInOptimaInPath }
        }
    }
    fun getWorkersToMessage(taskId: Int, worker: Worker) {
        viewModelScope.launch {
            val result = taskRepository.getWorkersInOneTask(taskId)
            if (worker.idRole == 2) {
                val result = taskRepository.getWorkersInOneTask(taskId)
                _workerListToMessage.value = result
            }
        }
    }
}
