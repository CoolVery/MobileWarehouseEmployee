package com.example.warehouseemployee.presentation.screens.visitingworkers

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.warehouseemployee.data.classes.Worker
import com.example.warehouseemployee.data.classes.WorkersWorkShift
import com.example.warehouseemployee.domain.auth.AuthenticationRepository
import com.example.warehouseemployee.domain.user.WorkerRepository
import com.example.warehouseemployee.presentation.navigathion.TasksWorkerDestination
import com.example.warehouseemployee.presentation.screens.tasks.TasksWorker
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
    private val _workersList = MutableStateFlow<List<WorkersWorkShift>>(listOf())
    val workerList: Flow<List<WorkersWorkShift>> = _workersList

    private var _navigateTo = MutableStateFlow<String?>(null)
    val navigateTo = _navigateTo.asStateFlow()

    fun getWorkersForShift(workerMainId: String) {
        viewModelScope.launch {
            val listWorkers = workerRepository.getWorkersForShift(workerMainId)
            _workersList.value = listWorkers
        }
    }
    fun updateIsCameWorkers(isCameWorkersList: List<Worker>) {
       viewModelScope.launch {
           for (worker in _workersList.value) {
               if (isCameWorkersList.contains(worker.idWorker)) {
                   worker.isCame = true
               }
           }
           if(workerRepository.updateIsCameShiftWorker(_workersList.value)) {
               _navigateTo.value = TasksWorkerDestination.route
           }
       }
    }

}