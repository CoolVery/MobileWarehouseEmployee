package com.example.warehouseemployee.domain.auth

import android.content.Context
import android.net.http.HttpException
import android.util.Log
import android.widget.Toast
import androidx.compose.ui.platform.LocalContext
import com.example.warehouseemployee.data.classes.Worker
import com.example.warehouseemployee.data.objects.SupabaseContext
import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.auth.exception.AuthRestException
import io.github.jan.supabase.auth.providers.builtin.Phone
import io.github.jan.supabase.exceptions.HttpRequestException
import kotlinx.io.IOException
import java.util.concurrent.TimeoutException
import javax.inject.Inject

class AuthenticationRepositoryImpl @Inject constructor(
    private val auth: Auth
) : AuthenticationRepository {
    override suspend fun signIn(phone: String, password: String): String {
        return try {
            auth.signOut()
            auth.signInWith(Phone) {
                this.phone = phone
                this.password = password
            }
            //Для логики программы в начале UUID ставится S для идентификации НЕ ошибки
            val userId = auth.currentUserOrNull()!!.id
            "S$userId"
            //Неверная авторизация
        } catch (e: AuthRestException) {
            "Неверный телефон или пароль"
            //Нет подключения к интернету
        } catch (e: HttpRequestException) {
            "Нет соединения с Интернетом\nПроверьте свое подключение"
            //Неизвестная ошибка
        } catch (e: Exception) {
            "Непредвиденная ошибка"
        }
    }
}
