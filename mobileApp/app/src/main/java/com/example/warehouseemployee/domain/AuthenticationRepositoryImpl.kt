package com.example.warehouseemployee.domain

import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.auth.providers.builtin.Phone
import javax.inject.Inject

class AuthenticationRepositoryImpl @Inject constructor(
    private val auth: Auth
) : AuthenticationRepository {
    override suspend fun signIn(phone: String, password: String): Boolean {
        return try {
            auth.signInWith(Phone) {
                this.phone = phone
                this.password = password
            }
            true
        } catch (e: Exception) {
            false
        }
    }
}
