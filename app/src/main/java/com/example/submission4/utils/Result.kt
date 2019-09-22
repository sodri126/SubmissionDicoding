package com.example.submission4.utils

sealed class Result<out T : Any> {
    data class Success<out T : Any>(val data: T) : Result<T>()
    object Loading : Result<Nothing>()
    data class Error(val message: String) : Result<Nothing>()
}