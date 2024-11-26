package com.example.warehouseemployee.domain.auth

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.jan.supabase.auth.Auth


@Module
@InstallIn(SingletonComponent::class)
object AuthenticationRepositoryModule {
    //Провайдер репозитория авторизации
    @Provides
    fun provideAuthenticationRepository(auth: Auth): AuthenticationRepository {
        return AuthenticationRepositoryImpl(auth)
    }
}