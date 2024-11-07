package com.example.warehouseemployee.domain.user

import com.example.warehouseemployee.domain.auth.AuthenticationRepository
import com.example.warehouseemployee.domain.auth.AuthenticationRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.postgrest.Postgrest

@Module
@InstallIn(SingletonComponent::class)
object WorkerRepositoryModule {

    @Provides
    fun provideWorkerRepository(postgrest: Postgrest): WorkerRepository {
        return WorkerRepositoryImpl(postgrest)
    }
}