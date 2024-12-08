package com.alexstefanov.currencyinfoapp.presentation.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.alexstefanov.currencyinfoapp.R
import com.alexstefanov.currencyinfoapp.app.utils.showToastMessage
import com.alexstefanov.currencyinfoapp.presentation.ui.components.AppTopBar
import com.alexstefanov.currencyinfoapp.presentation.ui.components.FiltersRadioButtonGroup
import com.alexstefanov.currencyinfoapp.presentation.ui.theme.Gray
import com.alexstefanov.currencyinfoapp.presentation.viewmodel.CurrencyViewModel

@Composable
fun FiltersScreen(
    viewModel: CurrencyViewModel = hiltViewModel(),
    onBackClick: () -> Unit
) {
    val sortOrder by viewModel.currentFilter.collectAsState()
    var newSortOrder by remember { mutableStateOf(sortOrder) }

    Scaffold(
        topBar = {
            AppTopBar(
                title = stringResource(R.string.filters),
                onBackButtonClick = onBackClick
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = stringResource(R.string.sort_by).toUpperCase(Locale.current),
                style = MaterialTheme.typography.labelMedium,
                color = Gray
            )

            FiltersRadioButtonGroup(
                selectedSortOrder = newSortOrder,
                onSortOrderSelected = { newSortOrder = it }
            )

            Spacer(modifier = Modifier.weight(1f))

            val context = LocalContext.current
            Button(
                onClick = {
                    viewModel.applyFilter(sortOrder = newSortOrder)
                    showToastMessage(context, R.string.message_filter_applied)
                    onBackClick()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(stringResource(R.string.apply))
            }
        }
    }
}