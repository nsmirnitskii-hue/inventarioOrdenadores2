package com.example.inventarioordenadores2.shared

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.inventarioordenadores2.data.Ordenador
import com.example.inventarioordenadores2.data.OrdenadorRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class OrdenadorViewModel(
    private val repositorio: OrdenadorRepository
): ViewModel(){
private  val _ordenadorFiltradas = MutableStateFlow<List<Ordenador>>(emptyList())
    val ordenadorFiltradas: StateFlow<List<Ordenador>> = _ordenadorFiltradas

    var identificador by mutableStateOf("")
        private set


}