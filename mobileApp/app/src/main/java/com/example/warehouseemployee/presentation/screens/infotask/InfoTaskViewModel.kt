package com.example.warehouseemployee.presentation.screens.infotask

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.warehouseemployee.data.classes.TaskProduct
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

    fun getTaskProduct(taskId: Int) {
        viewModelScope.launch {
            val result = taskRepository.getTaskProducts(taskId)
            _cellAndProductList.value = result
        }
    }
}
