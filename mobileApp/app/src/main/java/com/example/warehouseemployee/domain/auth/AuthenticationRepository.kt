package com.example.warehouseemployee.domain.auth

import android.content.Context
import com.example.warehouseemployee.data.classes.Worker


interface AuthenticationRepository {
    suspend fun signIn(phone: String, password: String): String
}