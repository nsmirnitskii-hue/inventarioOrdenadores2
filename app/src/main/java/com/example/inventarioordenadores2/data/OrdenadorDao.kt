package com.example.inventarioordenadores2.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface OrdenadorDao {

    @Query("SELECT * from ordenador ORDER BY identificador ASC")
    fun getAllOrdenador(): Flow<List<Ordenador>>

    @Query("SELECT * from or WHERE id = :id")
    fun getOrdenador(id: Int): Flow<Ordenador>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(entity: Ordenador)

    @Update()
    suspend fun update(entity: Ordenador)

    @Delete
    suspend fun delete(entity: Ordenador)


    @Query("""
    SELECT * FROM ordenador
    WHERE (:identificador IS NULL OR identificador LIKE '%' || :identificador || '%')
      AND (:modelo IS NULL OR modelo LIKE '%' || :modelo || '%')
""")
    fun filtrarOrdenador(identificador: String?, modelo: String?): Flow<List<Ordenador>>

}