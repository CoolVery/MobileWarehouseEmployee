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
    //Хранение ошибки
    private var _isError = MutableStateFlow<String?>("")
    val isError = _isError.asStateFlow()
    //Хранение перехода на следующий экран
    private var _navigateTo = MutableStateFlow<String?>(null)
    val navigateTo = _navigateTo.asStateFlow()
    //Хранение uuid рабочего
    private val _worker = MutableStateFlow<String>("")
    val worker: Flow<String> = _worker
    //Хранение телефона
    private val _phone = MutableStateFlow("")
    val phone: Flow<String> = _phone

    var phoneValue: String
        get() = _phone.value
        set(value) {
            _phone.value = value
        }
    var passwordValue: String
        get() = _password.value
        set(value) {
            _phone.value = value
        }
    //Хранение пароля
    private val _password = MutableStateFlow("")
    val password = _password
    /**
     * Изменение телефона
     *
     * @param phone строка телефона
     *
     */
    fun onPhoneChange(phone: String) {
        //Проверяет длину телефона, если меньше 12, то обновляет его
        if (phone.length <= 12) _phone.value = phone
    }
    /**
     * Изменение телефона
     *
     * @param password строка пароля
     *
     */
    fun onPasswordChange(password: String) {
        _password.value = password
    }
    /**
     * Метод авторизации
     */
    fun onSignIn() {
        viewModelScope.launch {
            //Вызываем метод авторизации и получаем результат Строку
            val result = authenticationRepository.signIn(
                phone = _phone.value,
                password = _password.value
            )
            _isError.value = result
            //Меняет навигацию, если пришел айди с префиксом S
            if(result.startsWith("S")) {
                //Получаем uuid, убрав символ S
                val worker = workerRepository.getWorker(result.substring(1))
                //Сериализуем объект в json
                _worker.value = Json.encodeToString(worker)
                //Если id рабочего
                when(worker!!.idRole) {
                    // 1 - обычный рабочий - экран Задач
                    1 -> _navigateTo.value = TasksWorkerDestination.route
                    // 2 - главный по смене - экран Работники на смене
                    2 -> _navigateTo.value = VisitingWorkersDestination.route
                }
            }
        }

    }
}