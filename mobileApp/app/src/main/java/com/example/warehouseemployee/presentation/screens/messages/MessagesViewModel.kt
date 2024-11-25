package com.example.warehouseemployee.presentation.screens.messages

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.warehouseemployee.data.classes.MessageInChat
import com.example.warehouseemployee.data.classes.Worker
import com.example.warehouseemployee.data.objects.SupabaseContext
import com.example.warehouseemployee.domain.messages.MessagesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.annotations.SupabaseExperimental
import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.postgrest.query.filter.FilterOperation
import io.github.jan.supabase.postgrest.query.filter.FilterOperator
import io.github.jan.supabase.realtime.selectAsFlow
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class MessagesViewModel @Inject constructor(
    private val messagesRepository: MessagesRepository
) : ViewModel() {

    private var _isError = MutableStateFlow<Boolean>(false)
    val isError = _isError.asStateFlow()

    private var _messageList: MutableStateFlow<MutableList<MessageInChat>> =
        MutableStateFlow(mutableListOf())
    val messageList: StateFlow<MutableList<MessageInChat>> = _messageList.asStateFlow()

    private var _chatId = 0

    fun getMessages(senderWorker: Worker, recipientWorker: Worker) {
        viewModelScope.launch {
            _chatId = messagesRepository.getMessagesWorkers(senderWorker, recipientWorker)
            try {


                withContext(Dispatchers.IO) {
                    @OptIn(SupabaseExperimental::class)
                    val messageFlow: Flow<List<MessageInChat>> =
                        SupabaseContext.provideSupabaseClient().postgrest.from("messages_in_chat")
                            .selectAsFlow(
                                MessageInChat::id,
                                filter = FilterOperation(
                                    "id_chat",
                                    FilterOperator.EQ,
                                    _chatId
                                )
                            )
                    messageFlow.collect {
                        _messageList.value = it.toMutableList()
                    }
                }
            } catch (e: Exception) {

            }
        }
    }

    fun sendMessage(contentMessage: String, worker: Worker) {
        val newMessage = MessageInChat(
            id = UUID.randomUUID().toString(),
            idWorkerSender = worker.idWorker,
            contentMessage = contentMessage,
            idChat = _chatId
        )
        viewModelScope.launch {
            _isError.value = messagesRepository.insertNewMessages(newMessage)
        }
    }
}