package com.alexstefanov.currencyinfoapp.app.utils

sealed class ScreenState<out T> {
    object InitialState : ScreenState<Nothing>()
    object LoadingState : ScreenState<Nothing>()
    object EmptyState : ScreenState<Nothing>()
    data class ErrorState(val message: String) : ScreenState<Nothing>()
    data class DataState<T>(val data: T) : ScreenState<T>()
}