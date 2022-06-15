package com.example.emparejapp.core

import java.lang.Exception

sealed class Results<out T>{
    class Loading<out T>:Results<T>()
    data class Success<out T>(val data : T):Results<T>()
    data class Failure(val exception: Exception):Results<Nothing>()
}
