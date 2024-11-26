package com.example.warehouseemployee.domain.task

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.jan.supabase.postgrest.Postgrest

@Module
@InstallIn(SingletonComponent::class)
object TaskRepositoryModule {
    //Провайдер репозитория задачи
    @Provides
    fun provideTaskRepository(postgrest: Postgrest): TaskRepository {
        return TaskRepositoryImpl(postgrest)
    }
}