package com.alexstefanov.currencyinfoapp.app.utils

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

suspend inline fun <T> safeApiCall(
    crossinline body: suspend () -> T
): Result<T> {
    return try {
        val users = withContext(Dispatchers.IO) {
            body()
        }
        Result.Success(users)
    } catch (e: Exception) {
        Result.Failure(e)
    }
}