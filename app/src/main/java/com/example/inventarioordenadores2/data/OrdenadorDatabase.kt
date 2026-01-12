package com.example.inventarioordenadores2.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Ordenador::class], version = 1, exportSchema = false)
abstract class OrdenadorDatabase: RoomDatabase() {

    abstract  fun ordenadorDao(): OrdenadorDao

    companion object{

        @Volatile
        private var Instance: OrdenadorDatabase? = null

        fun getDatabase(context: Context): OrdenadorDatabase {
            return Instance ?: synchronized(this){
                Room.databaseBuilder(context,
                    OrdenadorDatabase::class.java,
                    "Ordenador_database")
                    .build()
                    .also { Instance = it }
            }
        }
    }
}