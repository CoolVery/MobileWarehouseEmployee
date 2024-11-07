package com.example.warehouseemployee.presentation.screens.auth

import android.util.Log
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.warehouseemployee.data.classes.Worker
import com.example.warehouseemployee.data.objects.SupabaseContext
import com.example.warehouseemployee.domain.auth.AuthenticationRepository
import com.example.warehouseemployee.domain.user.WorkerRepository
import com.example.warehouseemployee.presentation.navigathion.TasksWorkerDestination
import com.example.warehouseemployee.presentation.navigathion.VisitingWorkersDestination
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.jan.supabase.auth.auth
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import javax.inject.Inject

@HiltViewModel
class AuthorizationViewModel @Inject constructor(
    private val authenticationRepository: AuthenticationRepository,
    private val workerRepository: WorkerRepository
) : ViewModel() {
    private var _isError = MutableStateFlow<String?>("")
    val isError = _isError.asStateFlow()

    private var _navigateTo = MutableStateFlow<String?>(null)
    val navigateTo = _navigateTo.asStateFlow()

    private val _worker = MutableStateFlow<String>("")
    val worker: Flow<String> = _worker

    private val _phone = MutableStateFlow("")
    val phone: Flow<String> = _phone

    private val _password = MutableStateFlow("")
    val password = _password

    fun onPhoneChange(phone: String) {
        if (phone.length <= 12) _phone.value = phone
        Log.d("DS", _phone.value)
    }
    fun onPasswordChange(password: String) {
        _password.value = password
    }
    fun onSignIn() {
        viewModelScope.launch {
            val result = authenticationRepository.signIn(
                phone = _phone.value,
                password = _password.value
            )
            _isError.value = result
            //Меняет навигацию, если пришел айди с префиксом S
            if(result.startsWith("S")) {
                val worker = workerRepository.getWorker(result.substring(1))
                _worker.value = Json.encodeToString(worker)
                when(worker!!.idRole) {
                    1 -> _navigateTo.value = TasksWorkerDestination.route
                    2 -> _navigateTo.value = VisitingWorkersDestination.route
                }
            }
        }

    }
}