package com.example.inventarioordenadores2

import android.util.Log


/*
* Es una forma global de llamar a un log, asi podemos estandarizar el mansaje
* Aseguramos que todos los logs tengan un [MyApp]
* Tambien informa desde que funcion se esta realizando el log*/
object MyLog {
    private const val  PREFIX = "[MyApp]"

    private fun caller(): String{
        val trace = Throwable().stackTrace
        val frame = trace.firstOrNull{element ->
            element.className.startsWith("com.example.inventarioordenadores2") &&
                    !element.className.contains("MyLog")
        }
        val className = frame?.className?.substringAfterLast(".")?.substringBefore("$")
            ?: "UnknownClass"
        val file = frame?.fileName ?: "Unknown.kt"
        val line = frame?.lineNumber ?: -1
        return "($frame:$line)"
    }
    fun d(message: String){
        Log.d(caller(), PREFIX + message)
    }
    fun e(message: String, throwable: Throwable? = null){
        if (throwable !=null){
            //Log.e acepta un Throwable como tercer parametro
            Log.e(caller(), PREFIX + message, throwable)
        }else{
            Log.e(caller(), PREFIX + message)
        }
    }
}