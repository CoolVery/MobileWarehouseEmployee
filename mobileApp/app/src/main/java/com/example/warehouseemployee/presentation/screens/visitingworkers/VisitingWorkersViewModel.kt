package com.example.warehouseemployee.presentation.screens.visitingworkers

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.warehouseemployee.data.classes.Worker
import com.example.warehouseemployee.domain.auth.AuthenticationRepository
import com.example.warehouseemployee.domain.user.WorkerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VisitingWorkersViewModel @Inject constructor(
    private val workerRepository: WorkerRepository
) : ViewModel() {
    private val _workersList = MutableStateFlow<List<Worker>>(listOf())
    val workerList: Flow<List<Worker>> = _workersList

    fun getWorkersForShift(workerMainId: String) {
        viewModelScope.launch {
            val listWorkers = workerRepository.getWorkersForShift(workerMainId)
            _workersList.value = listWorkers
        }
    }

}