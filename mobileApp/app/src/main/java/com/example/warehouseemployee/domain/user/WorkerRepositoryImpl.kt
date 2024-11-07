package com.example.warehouseemployee.domain.user

import com.example.warehouseemployee.data.classes.Worker
import com.example.warehouseemployee.domain.auth.AuthenticationRepository
import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.auth.exception.AuthRestException
import io.github.jan.supabase.auth.providers.builtin.Phone
import io.github.jan.supabase.exceptions.HttpRequestException
import io.github.jan.supabase.postgrest.Postgrest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class WorkerRepositoryImpl @Inject constructor(
    private val postgrest: Postgrest
) : WorkerRepository {
    override suspend fun getWorker(workerId: String): Worker? {
        return try {
            withContext(Dispatchers.IO) {
                val result = postgrest.from("workers")
                    .select().decodeSingle<Worker>()
                result
            }
        } catch (e: Exception) {
            null
        }
    }
}