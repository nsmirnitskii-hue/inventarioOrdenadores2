package com.example.inventarioordenadores2.data

import kotlinx.coroutines.flow.Flow

class OrdenadorRepository(private val ordenadorDao: OrdenadorDao) {

    val todosLosOrdenadores: Flow<List<Ordenador>> = ordenadorDao.getAllOrdenador()

    suspend fun insertarOrdenador(ordenador: Ordenador){
        ordenadorDao.insert(ordenador)
    }
    fun getOrdenador(id: Int): Flow<Ordenador>{
        return ordenadorDao.getOrdenador(id)
    }
    //Borrar un ordenador
    suspend fun eliminarOrdenador(ordenador: Ordenador){
        ordenadorDao.delete(ordenador)
    }
    //Actulizar un ordenador existente
    suspend fun actualizarOrdenador(ordenador: Ordenador){
        ordenadorDao.update(ordenador)
    }
    fun filtrarOrdenador(identificador: String?, modelo: String?): Flow<List<Ordenador>> =
        ordenadorDao.filtrarOrdenador(identificador, modelo )
}