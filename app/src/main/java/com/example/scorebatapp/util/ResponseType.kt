package com.example.scorebatapp.util

sealed class ResponseType<out T> {

    object Loading : ResponseType<Nothing>()

    data class Success<T> (val response: T): ResponseType<T>()

    class Error(val e: String): ResponseType<Nothing>()
}