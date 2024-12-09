package com.alexstefanov.currencyinfoapp.app.utils

sealed class Result<out H> {
    data class Success<out T>(val data: T): Result<T>()
    data class Failure(val error: Throwable): Result<Nothing>()
}