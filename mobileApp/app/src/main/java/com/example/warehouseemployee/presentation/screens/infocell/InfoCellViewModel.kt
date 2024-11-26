package com.example.warehouseemployee.presentation.screens.infocell

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.warehouseemployee.data.classes.Cell
import com.example.warehouseemployee.data.classes.Section
import com.example.warehouseemployee.data.objects.SupabaseContext
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.jan.supabase.postgrest.postgrest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InfoCellViewModel @Inject constructor() : ViewModel() {
    private val _section = MutableStateFlow<Section?>(null)
    val section: Flow<Section?> = _section
    //Получаем секцию по id ячейке
    fun getSection(cell: Cell) {
        viewModelScope.launch {
            val result = SupabaseContext.provideSupabaseClient().postgrest.from("sections").select {
                filter {
                    eq("id", cell.idSection)
                }
            }.decodeSingle<Section>()
            _section.value = result
        }
    }
}