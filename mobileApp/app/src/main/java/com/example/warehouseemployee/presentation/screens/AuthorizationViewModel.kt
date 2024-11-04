package com.example.warehouseemployee.presentation.screens

import androidx.lifecycle.ViewModel
import com.example.warehouseemployee.domain.auth.AuthenticationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AuthorizationViewModel @Inject constructor(
    private val authenticationRepository: AuthenticationRepository,
) : ViewModel() { }