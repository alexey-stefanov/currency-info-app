package com.alexstefanov.currencyinfoapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexstefanov.currencyinfoapp.app.utils.Result
import com.alexstefanov.currencyinfoapp.app.utils.ScreenState
import com.alexstefanov.currencyinfoapp.domain.model.SortOrder
import com.alexstefanov.currencyinfoapp.domain.repository.CurrencyRepository
import com.alexstefanov.currencyinfoapp.domain.repository.FavoritePairRepository
import com.alexstefanov.currencyinfoapp.presentation.model.CurrencyUiModel
import com.alexstefanov.currencyinfoapp.presentation.model.toCurrencyUi
import com.alexstefanov.currencyinfoapp.presentation.model.toDomain
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CurrencyViewModel @Inject constructor(
    private val currencyRepository: CurrencyRepository,
    private val favoritePairRepository: FavoritePairRepository
) : ViewModel() {

    private val _screenState =
        MutableStateFlow<ScreenState<List<CurrencyUiModel>>>(ScreenState.InitialState)
    val screenState: StateFlow<ScreenState<List<CurrencyUiModel>>> = _screenState

    private val _selectedCurrencyCode = MutableStateFlow<String>("USD")
    val selectedCurrencyCode: StateFlow<String> = _selectedCurrencyCode

    private val _currencySymbols = MutableStateFlow<Map<String, String>>(emptyMap())
    val currencySymbols: StateFlow<Map<String, String>> = _currencySymbols

    private val _currentFilter = MutableStateFlow<SortOrder>(SortOrder.CodeAZ)
    val currentFilter: StateFlow<SortOrder> = _currentFilter

    // refresh every 60 seconds
    private val periodicRefreshFlow = flow {
        while (true) {
            emit(Unit)
            kotlinx.coroutines.delay(60_000)
        }
    }

    init {
        viewModelScope.launch(Dispatchers.IO) {
            getCurrencySymbols()
            refreshData()
        }

        viewModelScope.launch(Dispatchers.IO) {
            observeFavorites()
        }
    }

    private suspend fun refreshData() {
        combine(
            selectedCurrencyCode,
            periodicRefreshFlow
        ) { currencyCode, _ ->
            fetchLatestCurrenciesWithFavorites(currencyCode)
        }.collect()
    }

    private suspend fun observeFavorites() {
        favoritePairRepository.getAllFavoritePairs().collect { favoritePairs ->
            val favoritePairCodes =
                favoritePairs.map { "${it.baseCurrencyCode}/${it.targetCurrencyCode}" }

            val currentList = (screenState.value as? ScreenState.DataState)?.data

            currentList?.let {
                val updatedList = currentList.map { currency ->
                    val isFavorite =
                        "${selectedCurrencyCode.value}/${currency.codeLabel}" in favoritePairCodes
                    if (currency.isFavorite != isFavorite) {
                        currency.copy(isFavorite = isFavorite)
                    } else {
                        currency
                    }
                }

                _screenState.value = ScreenState.DataState(updatedList)
            }
        }
    }

    fun selectCurrency(currency: String) {
        _selectedCurrencyCode.value = currency
    }

    private suspend fun getCurrencySymbols() {
        val currencySymbolsResult = currencyRepository.getCurrencySymbols()

        when (currencySymbolsResult) {
            is Result.Success -> {
                _currencySymbols.value = currencySymbolsResult.data
            }

            is Result.Failure -> {
                _screenState.value =
                    ScreenState.ErrorState(currencySymbolsResult.error.message ?: "")
            }
        }
    }

    private suspend fun fetchLatestCurrenciesWithFavorites(selectedCurrency: String) {
        _screenState.value = ScreenState.LoadingState
        val latestCurrenciesResult = currencyRepository.getLatestCurrencies(selectedCurrency)
        val favoritePairs = favoritePairRepository.getPairsForBaseCurrency(selectedCurrency)

        when (latestCurrenciesResult) {
            is Result.Success -> {
                val updatedCurrencies = latestCurrenciesResult.data.map { currency ->
                    currency.toCurrencyUi().copy(
                        isFavorite = currency.targetCurrencyCode in favoritePairs
                    )
                }

                val sortedCurrencies = sortCurrencies(updatedCurrencies, _currentFilter.value)
                if (sortedCurrencies.isNotEmpty()) {
                    _screenState.value = ScreenState.DataState(sortedCurrencies)
                } else {
                    _screenState.value = ScreenState.EmptyState
                }
            }

            is Result.Failure -> {
                _screenState.value =
                    ScreenState.ErrorState(latestCurrenciesResult.error.message ?: "")
            }
        }
    }

    fun addPairToFavorites(currencyUiModel: CurrencyUiModel) {
        viewModelScope.launch(Dispatchers.IO) {
            val currencyModel = currencyUiModel.toDomain(selectedCurrencyCode.value)
            favoritePairRepository.addPairToFavorites(currencyModel)

            if (screenState.value is ScreenState.DataState) {
                val updatedList = (screenState.value as ScreenState.DataState).data.map {
                    if (it.codeLabel == currencyUiModel.codeLabel) it.copy(isFavorite = true) else it
                }
                _screenState.value = ScreenState.DataState(updatedList)
            }
        }
    }

    fun removePairFromFavorites(currencyUiModel: CurrencyUiModel) {
        viewModelScope.launch(Dispatchers.IO) {
            val pairCode = "${selectedCurrencyCode.value}/${currencyUiModel.codeLabel}"
            favoritePairRepository.removePairFromFavorites(pairCode)

            if (screenState.value is ScreenState.DataState) {
                val updatedList = (screenState.value as ScreenState.DataState).data.map {
                    if (it.codeLabel == currencyUiModel.codeLabel) it.copy(isFavorite = false) else it
                }
                _screenState.value = ScreenState.DataState(updatedList)
            }
        }
    }

    fun applyFilter(sortOrder: SortOrder) {
        if (currentFilter.value != sortOrder) {
            _currentFilter.value = sortOrder
            val currencies = (screenState.value as? ScreenState.DataState)?.data ?: emptyList()
            val sortedCurrencies = sortCurrencies(currencies, _currentFilter.value)
            if (sortedCurrencies.isNotEmpty()) {
                _screenState.value = ScreenState.DataState(sortedCurrencies)
            }
        }
    }

    private fun sortCurrencies(
        currencies: List<CurrencyUiModel>,
        sortOrder: SortOrder
    ): List<CurrencyUiModel> {
        return when (sortOrder) {
            SortOrder.CodeAZ -> currencies.sortedBy { it.codeLabel }
            SortOrder.CodeZA -> currencies.sortedByDescending { it.codeLabel }
            SortOrder.QuoteAsc -> currencies.sortedBy { it.rateValue.toDouble() }
            SortOrder.QuoteDesc -> currencies.sortedByDescending { it.rateValue.toDouble() }
        }
    }

    fun retry() {
        viewModelScope.launch(Dispatchers.IO) {
            fetchLatestCurrenciesWithFavorites(selectedCurrencyCode.value)
        }
    }
}