package com.example.inventarioordenadores2.shared

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.inventarioordenadores2.MyLog
import com.example.inventarioordenadores2.data.Ordenador
import com.example.inventarioordenadores2.data.OrdenadorRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import java.security.cert.Extension

class OrdenadorViewModel(
    private val repositorio: OrdenadorRepository
): ViewModel(){
private  val _ordenadorFiltradas = MutableStateFlow<List<Ordenador>>(emptyList())
    val ordenadorFiltradas: StateFlow<List<Ordenador>> = _ordenadorFiltradas

    var identificador by mutableStateOf("")
        private set
    var modelo by mutableStateOf("")
        private set
    var añoActivacion by mutableStateOf("")
        private set
    var filtrarIdentificador by mutableStateOf("")
        private set
    var filtrarModelo by mutableStateOf("")
        private set
    var errorMensaje by mutableStateOf<String?>(null)
        private set
    init {
            viewModelScope.launch {
                repositorio.todosLosOrdenadores.collect { ordenador ->
                    _ordenadorFiltradas.value = ordenador
                }
            }
    }
    //Funciones de cambio (Eventos)
    fun aplicarOrdenadores(identificador: String, modelo: String){
        viewModelScope.launch {
            if (filtrarIdentificador.isEmpty() && filtrarModelo.isEmpty()){
                repositorio.todosLosOrdenadores.collect { ordenador ->
                    _ordenadorFiltradas.value = ordenador
                }
            }else{
                repositorio.filtrarOrdenador(identificador, modelo).collect { ordenador ->
                    _ordenadorFiltradas.value = ordenador
                }
            }
        }
    }
    fun onFiltroIdentificadorChanged(nuevoTexto: String){
        filtrarIdentificador = nuevoTexto
        if (errorMensaje != null) errorMensaje = null //Limpiar error al escribir
    }
    fun onFIltrarModeloChanged(nuevoTexto: String){
        filtrarModelo = nuevoTexto
        if (errorMensaje != null) errorMensaje = null
    }
    fun onModeloChanged(nuevoTexto: String){
        modelo = nuevoTexto
        if (errorMensaje !=null) errorMensaje = null
    }
    fun onIdentificadorChanged(nuevoTexto: String){
        identificador = nuevoTexto
        if (errorMensaje !=null) errorMensaje = null
    }
    fun onAñoActivacionChanged(nuevoTexto: String){
        añoActivacion = nuevoTexto
        if (errorMensaje !=null) errorMensaje = null
    }
    //Logica de negocio
    fun guardarOrdenador(onSucces: () -> Unit){
        // Validacion basica
        if (identificador.isBlank() || modelo.isBlank()){
            errorMensaje = "Por favor, cimoleta todos los campos"
            return
        }
        //Ejecutar en segundo plano (Corrutina)
        viewModelScope.launch {
            try {
                val nuevoOrdenador = Ordenador(
                    identificador = identificador,
                    modelo = modelo.trim(),
                    añoActivacion = añoActivacion.toInt()
                )
                repositorio.insertarOrdenador(nuevoOrdenador)
                onSucces()
                limpiarFormulario()
            }catch (e: Exception){
                errorMensaje = "Error al guardar: ${e.message}"
            }
        }
    }
    fun editarOrdenador(ordenadorId: Int, onSucces: () -> Unit){
        // Validacion basica
        if (identificador.isBlank() || modelo.isBlank()){
            errorMensaje = "Por favor, completa todos los campos"
            return
        }
        viewModelScope.launch {
            try {
                //Consultar el objeto actualizando manteniendo el id
                val ordenadorActulizado = Ordenador(
                    id = ordenadorId,
                    identificador = identificador,
                    modelo = modelo.trim(),
                    añoActivacion = añoActivacion.toInt()
                )
                //Actulizar en BD (debe existir en el repositorio/DAO)
                repositorio.actualizarOrdenador(ordenadorActulizado)
                //Notificar exito y limpiar
                onSucces()
                limpiarFormulario()
            }catch (e: Exception){
                errorMensaje = "Error al editar: ${e.message}"
            }
        }
    }
    private fun limpiarFormulario(){
        identificador = ""
        modelo = ""
        añoActivacion = ""
        errorMensaje = null
    }
    // Observa el ordenador por id y actualoza los campos del viewModel continuamente
    fun observaOrdenador(id: Int){
        viewModelScope.launch {
            try {
                repositorio.getOrdenador(id)
                    .distinctUntilChanged()
                    .collect { ordenador ->
                        identificador = ordenador.identificador.toString()
                        modelo = ordenador.modelo
                        añoActivacion = ordenador.añoActivacion.toString()
                    }
            }catch (e: Exception){
                errorMensaje ="Error al observar ordenador: ${e.message}"
            }
        }
    }

    suspend fun eliminarOrdenador(ordenadorId: Int){
        MyLog.d("Solicitud eliminada")
        repositorio.eliminarOrdenador(Ordenador(ordenadorId))
    }
    fun insertarDatosPrueba(){
        viewModelScope.launch {
            val ordenadores = listOf(
                Ordenador(0, "PC-001","HP EliteBook 850 G6",2021),
                Ordenador(1, "PC-002","HP EliteBook 850 G6",2020),
                Ordenador(2, "PC-003","HP EliteBook 850 G6",2019),
                Ordenador(3, "PC-004","HP EliteBook 850 G6",2022),
                Ordenador(4, "PC-005","HP EliteBook 850 G6",2023),
                Ordenador(5, "PC-006","Acer Veriton X",2021),
                Ordenador(6, "PC-007","Acer Veriton X",2020),
                Ordenador(7, "PC-008","Acer Veriton X",2019),
                Ordenador(8, "PC-009","Acer Veriton X",2022),
            )
            ordenadores.forEach { ordenador ->
                repositorio.insertarOrdenador(ordenador)
            }
        }
    }
}