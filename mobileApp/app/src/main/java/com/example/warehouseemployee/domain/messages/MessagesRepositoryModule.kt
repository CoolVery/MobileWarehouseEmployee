package com.example.warehouseemployee.domain.messages

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.postgrest.Postgrest


@Module
@InstallIn(SingletonComponent::class)
object MessagesRepositoryModule {
    //Провайдер репозитория авторизации
    @Provides
    fun provideMessagesRepository(postgrest: Postgrest): MessagesRepository {
        return MessagesRepositoryImpl(postgrest)
    }
}