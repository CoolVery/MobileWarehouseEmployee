package com.example.warehouseemployee.presentation.screens.optimalplan

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.warehouseemployee.domain.task.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OptimalPlanViewModel @Inject constructor(
    private val taskRepository: TaskRepository
): ViewModel() {
    private val _imageUrl: MutableStateFlow<String?> = MutableStateFlow<String?>(null)
    val imageUrl: StateFlow<String?> = _imageUrl.asStateFlow()
    //Получаем изображение из задачи
    fun getImg(taskId: Int) {
        viewModelScope.launch {
            val taskFull = taskRepository.getTaskById(taskId)
            _imageUrl.value = taskFull!!.imgOptimalPath
            Log.d("IMG", _imageUrl.value.toString())
        }
    }
}