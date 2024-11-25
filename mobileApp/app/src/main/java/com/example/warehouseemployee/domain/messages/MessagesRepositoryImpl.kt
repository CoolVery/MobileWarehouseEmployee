package com.example.warehouseemployee.domain.messages

import android.util.Log
import com.example.warehouseemployee.data.classes.Chat
import com.example.warehouseemployee.data.classes.MessageInChat
import com.example.warehouseemployee.data.classes.Worker
import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.auth.exception.AuthRestException
import io.github.jan.supabase.auth.providers.builtin.Phone
import io.github.jan.supabase.exceptions.HttpRequestException
import io.github.jan.supabase.postgrest.Postgrest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MessagesRepositoryImpl @Inject constructor(
    private val postgrest: Postgrest
) : MessagesRepository {
    override suspend fun getMessagesWorkers(
        sendWorker: Worker,
        recipientWorker: Worker
    ): Int {
        return try {
            withContext(Dispatchers.IO) {
                val chat = postgrest.from("chats").select {
                    filter {

                        or {
                            and {
                                eq("worker_first", sendWorker.idWorker)
                                eq("worker_second", recipientWorker.idWorker)
                            }
                            and {
                                eq("worker_first", recipientWorker.idWorker)
                                eq("worker_second", sendWorker.idWorker)
                            }
                        }
                    }
                }.decodeSingle<Chat>()
                chat.id
            }
        } catch (e: Exception) {
            -1
        }
    }

    override suspend fun insertNewMessages(newMessage: MessageInChat): Boolean {
        return try {
            withContext(Dispatchers.IO) {
                postgrest.from("messages_in_chat").insert(newMessage)
                false
            }
        } catch (e: Exception) {

            true
        }
    }
}
