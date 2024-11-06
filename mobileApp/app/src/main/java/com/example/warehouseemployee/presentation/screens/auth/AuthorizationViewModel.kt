package com.example.warehouseemployee.presentation.screens.auth

import android.util.Log
import androidx.compose.ui.focus.FocusState
import androidx.lifecycle.ViewModel
import com.example.warehouseemployee.domain.auth.AuthenticationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class AuthorizationViewModel @Inject constructor(
    private val authenticationRepository: AuthenticationRepository,
) : ViewModel() {
    private val _phone = MutableStateFlow("")
    val phone: Flow<String> = _phone

    private val _password = MutableStateFlow("")
    val password = _password

    fun onPhoneChange(phone: String) {
        if (phone.length <= 12) _phone.value = phone
        Log.d("DS", _phone.value)
    }
    fun onPhoneFocusChange(focus: FocusState, value: String, funChange: (String) -> Unit) {
        if (focus.isFocused && value.isEmpty()) {
            funChange("+")
        }
    }
    fun onPasswordChange(password: String) {
        _password.value = password
    }
}