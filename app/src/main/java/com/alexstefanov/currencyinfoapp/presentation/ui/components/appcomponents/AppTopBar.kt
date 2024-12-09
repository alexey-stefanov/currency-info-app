package com.alexstefanov.currencyinfoapp.presentation.ui.components.appcomponents

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.alexstefanov.currencyinfoapp.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar(title: String, onBackButtonClick: (() -> Unit)? = null) {
    TopAppBar(
        title = { Text(text = title, style = MaterialTheme.typography.titleLarge) },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        navigationIcon = {
            onBackButtonClick?.let {
                IconButton(onClick = onBackButtonClick) {
                    Icon(
                        painter = painterResource(R.drawable.ic_arrow_back),
                        tint = MaterialTheme.colorScheme.primary,
                        contentDescription = stringResource(R.string.back)
                    )
                }
            }
        }
    )
}
