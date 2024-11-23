package com.example.warehouseemployee.presentation.screens.optimalplan

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.warehouseemployee.domain.task.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OptimalPlanViewModel @Inject constructor(
    private val taskRepository: TaskRepository
): ViewModel() {
    private val _imageUrl = MutableStateFlow("")
    val imageUrl: Flow<String> = _imageUrl

    fun getImg(taskId: Int) {
        viewModelScope.launch {
            val taskFull = taskRepository.getTaskById(taskId)
            _imageUrl.value = taskFull!!.imgOptimalPath
        }
    }
}