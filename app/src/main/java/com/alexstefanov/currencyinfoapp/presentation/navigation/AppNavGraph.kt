package com.alexstefanov.currencyinfoapp.presentation.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.alexstefanov.currencyinfoapp.presentation.ui.screens.CurrenciesScreen
import com.alexstefanov.currencyinfoapp.presentation.ui.screens.FavoritesScreen
import com.alexstefanov.currencyinfoapp.presentation.ui.screens.FiltersScreen

@Composable
fun AppNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Currencies.route,
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None }
    ) {
        composable(
            Screen.Currencies.route,
            enterTransition = {
                fadeIn() + slideIntoContainer(towards = AnimatedContentTransitionScope.SlideDirection.End)
            },
            exitTransition = {
                fadeOut() + slideOutOfContainer(towards = AnimatedContentTransitionScope.SlideDirection.Start)

            }
        ) {
            CurrenciesScreen(
                onFilterClick = {
                    navController.navigate(Screen.Filters.route)
                }
            )
        }
        composable(
            Screen.Favorites.route,
            enterTransition = {
                fadeIn() + slideIntoContainer(towards = AnimatedContentTransitionScope.SlideDirection.Start)
            },
            exitTransition = {
                fadeOut() + slideOutOfContainer(towards = AnimatedContentTransitionScope.SlideDirection.End)

            }
        ) {
            FavoritesScreen()
        }
        composable(
            Screen.Filters.route,
            enterTransition = {
                fadeIn() + slideIntoContainer(towards = AnimatedContentTransitionScope.SlideDirection.Start)
            },
            exitTransition = {
                fadeOut() + slideOutOfContainer(towards = AnimatedContentTransitionScope.SlideDirection.End)

            }
        ) {
            FiltersScreen(
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
    }
}