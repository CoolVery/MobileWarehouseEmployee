package com.example.warehouseemployee.domain.auth

import android.content.Context
import com.example.warehouseemployee.data.classes.Worker

//Интерфейс репозитория, где будут перечислены абстрактные методы авторизации
interface AuthenticationRepository {
    /**
     * Авторизация
     *
     * @param phone телефон рабочего
     * @param password пароль рабочего
     *
     * @return UUID рабочего
     */
    suspend fun signIn(phone: String, password: String): String
}