package com.alexstefanov.currencyinfoapp.presentation.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.alexstefanov.currencyinfoapp.R
import com.alexstefanov.currencyinfoapp.presentation.ui.components.AppTopBar

@Composable
fun FavoritesScreen() {
    Scaffold(
        topBar = {
            AppTopBar(title = stringResource(R.string.favorites))
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            Text("Favorites Screen Content")
        }
    }
}