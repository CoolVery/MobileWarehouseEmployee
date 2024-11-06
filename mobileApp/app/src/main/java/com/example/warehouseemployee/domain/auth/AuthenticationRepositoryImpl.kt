package com.example.warehouseemployee.domain.auth

import android.content.Context
import android.widget.Toast
import androidx.compose.ui.platform.LocalContext
import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.auth.providers.builtin.Phone
import kotlinx.io.IOException
import retrofit2.HttpException
import java.util.concurrent.TimeoutException
import javax.inject.Inject

class AuthenticationRepositoryImpl @Inject constructor(
    private val auth: Auth
) : AuthenticationRepository {
    override suspend fun signIn(phone: String, password: String): String {
        return try {
            auth.signInWith(Phone) {
                this.phone = phone
                this.password = password
            }
            val userId = auth.currentUserOrNull()!!.id
            "S$userId"
        } catch (e: HttpException) {
            // Ошибка HTTP
            "Ошибка сети"
        } catch (e: TimeoutException) {
            // Истекло время ожидания
            "Истекло время ожидания"
        } catch (e: IOException) {
            // Ошибка сетевого чтения
            "Ошибка связи"
        } catch (e: Exception) {
            // Неверный телефон или пароль
            "Неверный телефон или пароль"
        }
    }
}
