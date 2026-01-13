package com.example.inventarioordenadores2.data

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "ordenador", indices = [Index(value = ["identificador"], unique = true)])
data class Ordenador(
@PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val identificador: String = "",
    val modelo: String = "",
    val añoActivacion: Int = 0
)