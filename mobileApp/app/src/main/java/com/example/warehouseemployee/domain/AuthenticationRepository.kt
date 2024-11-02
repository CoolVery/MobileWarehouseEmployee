package com.example.warehouseemployee.domain

interface AuthenticationRepository {
    suspend fun signIn(phone: String, password: String): Boolean

}