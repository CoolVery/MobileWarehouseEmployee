package com.example.warehouseemployee.domain.auth

import android.content.Context


interface AuthenticationRepository {
    suspend fun signIn(phone: String, password: String): String

}