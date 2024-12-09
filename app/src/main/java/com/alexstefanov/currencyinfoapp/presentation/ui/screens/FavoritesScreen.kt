package com.alexstefanov.currencyinfoapp.presentation.ui.screens

import android.widget.Toast
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.LayoutDirection
import androidx.hilt.navigation.compose.hiltViewModel
import com.alexstefanov.currencyinfoapp.R
import com.alexstefanov.currencyinfoapp.presentation.ui.components.AppTopBar
import com.alexstefanov.currencyinfoapp.presentation.ui.components.CurrencyCard
import com.alexstefanov.currencyinfoapp.presentation.ui.components.EmptyView
import com.alexstefanov.currencyinfoapp.presentation.viewmodel.FavoritePairViewModel

@Composable
fun FavoritesScreen(viewModel: FavoritePairViewModel = hiltViewModel()) {
    Scaffold(
        topBar = {
            AppTopBar(title = stringResource(R.string.favorites))
        }
    ) { padding ->
        val favoritePairs by viewModel.favoritePairs.collectAsState(emptyList())

        if (favoritePairs.isNotEmpty()) {
            val context = LocalContext.current
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        start = padding.calculateLeftPadding(LayoutDirection.Ltr),
                        top = padding.calculateTopPadding(),
                        end = padding.calculateRightPadding(LayoutDirection.Ltr)
                    ),
                contentPadding = PaddingValues(
                    start = 16.dp,
                    top = 16.dp,
                    end = 16.dp,
                    bottom = 16.dp
                )
            ) {
                items(favoritePairs) { currencyUiModel ->
                    CurrencyCard(
                        currencyUiModel = currencyUiModel
                    ) {
                        viewModel.removePairFromFavorites(currencyUiModel.codeLabel)
                        Toast.makeText(
                            context, context.getString(R.string.message_removed_from_favorites),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        } else {
            EmptyView()
        }
    }
}