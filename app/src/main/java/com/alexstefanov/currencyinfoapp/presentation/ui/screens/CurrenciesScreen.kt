package com.alexstefanov.currencyinfoapp.presentation.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.alexstefanov.currencyinfoapp.R
import com.alexstefanov.currencyinfoapp.app.utils.showToastMessage
import com.alexstefanov.currencyinfoapp.presentation.ui.components.AppTopBar
import com.alexstefanov.currencyinfoapp.presentation.ui.components.CurrencyCard
import com.alexstefanov.currencyinfoapp.presentation.ui.components.CurrencyDropdown
import com.alexstefanov.currencyinfoapp.presentation.ui.components.FilterButton
import com.alexstefanov.currencyinfoapp.presentation.viewmodel.CurrencyViewModel

@Composable
fun CurrenciesScreen(
    viewModel: CurrencyViewModel = hiltViewModel(),
    onFilterClick: () -> Unit
) {
    val currencies by viewModel.currencies.collectAsState()

    Scaffold(
        topBar = {
            AppTopBar(title = stringResource(R.string.currencies))
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    start = padding.calculateLeftPadding(LayoutDirection.Ltr),
                    top = padding.calculateTopPadding(),
                    end = padding.calculateRightPadding(LayoutDirection.Ltr)
                )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.surface)
                    .padding(horizontal = 16.dp)
                    .padding(bottom = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                val selectedCurrency by viewModel.selectedCurrencyCode.collectAsState()
                val currencySymbols by viewModel.currencySymbols.collectAsState()

                CurrencyDropdown(
                    modifier = Modifier.weight(1f),
                    selectedCurrency = selectedCurrency,
                    currencyList = currencySymbols.map { it.key }
                ) { newSelectedCurrency ->
                    viewModel.selectCurrency(newSelectedCurrency)
                }

                Spacer(modifier = Modifier.width(8.dp))

                FilterButton(onClick = onFilterClick)
            }

            val context = LocalContext.current
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(
                    start = 16.dp,
                    top = 16.dp,
                    end = 16.dp,
                    bottom = 16.dp
                )
            ) {
                items(currencies) { currency ->
                    CurrencyCard(
                        currencyUiModel = currency
                    ) {
                        if (currency.isFavorite) {
                            viewModel.removePairFromFavorites(currency)
                            showToastMessage(context, R.string.message_removed_from_favorites)
                        } else {
                            viewModel.addPairToFavorites(currency)
                            showToastMessage(context, R.string.message_added_to_favorites)
                        }
                    }
                }
            }
        }
    }
}