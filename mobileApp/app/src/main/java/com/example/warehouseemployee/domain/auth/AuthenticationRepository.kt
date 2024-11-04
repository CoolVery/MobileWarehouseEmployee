package com.example.warehouseemployee.domain.auth


interface AuthenticationRepository {
    suspend fun signIn(phone: String, password: String): Boolean

}