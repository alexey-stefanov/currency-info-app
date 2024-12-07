package com.alexstefanov.currencyinfoapp.presentation.navigation

sealed class Screen(val route: String) {
    object Currencies : Screen("currencies")
    object Favorites : Screen("favorites")
    object Filters : Screen("filters")
}