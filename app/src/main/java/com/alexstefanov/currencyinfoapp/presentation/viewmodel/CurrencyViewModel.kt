package com.alexstefanov.currencyinfoapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexstefanov.currencyinfoapp.data.repository.currency.CurrencyRepository
import com.alexstefanov.currencyinfoapp.data.repository.favoritecurrency.FavoritePairRepository
import com.alexstefanov.currencyinfoapp.domain.mapper.toCurrencyUi
import com.alexstefanov.currencyinfoapp.domain.mapper.toDomain
import com.alexstefanov.currencyinfoapp.domain.model.CurrencyModel
import com.alexstefanov.currencyinfoapp.domain.model.SortOrder
import com.alexstefanov.currencyinfoapp.presentation.model.CurrencyUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CurrencyViewModel @Inject constructor(
    private val currencyRepository: CurrencyRepository,
    private val favoritePairRepository: FavoritePairRepository
) : ViewModel() {

    private val _selectedCurrencyCode = MutableStateFlow<String>("USD")
    val selectedCurrencyCode: StateFlow<String> = _selectedCurrencyCode

    private val _currencySymbols = MutableStateFlow<Map<String, String>>(emptyMap())
    val currencySymbols: StateFlow<Map<String, String>> = _currencySymbols

    private val _currencies = MutableStateFlow<List<CurrencyUiModel>>(emptyList())
    val currencies: StateFlow<List<CurrencyUiModel>> = _currencies

    private val _currentFilter = MutableStateFlow<SortOrder>(SortOrder.CodeAZ)
    val currentFilter: StateFlow<SortOrder> = _currentFilter

    init {
        viewModelScope.launch(Dispatchers.IO) {
            getLatestCurrencies()
            getCurrencySymbols()
            observeFavorites()
        }
    }

    private suspend fun observeFavorites() {
        favoritePairRepository.getPairsForBaseCurrencyFlow(selectedCurrencyCode.value)
            .collect { favoritePairs ->
                val latestCurrencies = _currencies.value
                _currencies.value = latestCurrencies.map { currency ->
                    currency.copy(isFavorite = currency.codeLabel in favoritePairs)
                }
            }
    }

    fun selectCurrency(currency: String) {
        _selectedCurrencyCode.value = currency
        viewModelScope.launch(Dispatchers.IO) {
            getLatestCurrencies()
            applyFilter(currentFilter.value)
        }
    }

    private suspend fun getCurrencySymbols() {
        //val currencySymbols = currencyRepository.getCurrencySymbols()
        val currencySymbols = mapOf("USD" to "", "BYN" to "", "RUB" to "", "EUR" to "")
        _currencySymbols.value = currencySymbols
    }

    private suspend fun getLatestCurrencies() {
        val selectedCurrencyCode = selectedCurrencyCode.value
        //val latestCurrencies = currencyRepository.getLatestCurrencies(selectedCurrencyCode)
        val latestCurrencies = listOf(
            CurrencyModel("USD", "BYN", 2.0),
            CurrencyModel("USD", "PLN", -1.0),
            CurrencyModel("USD", "RUB", 0.2),
            CurrencyModel("USD", "EUR", 3.5),
            CurrencyModel("USD", "JYP", 1.0)
        )
        val targetCurrencyCodes = favoritePairRepository.getPairsForBaseCurrency(selectedCurrencyCode)
        val mappedCurrencies = latestCurrencies.map { it.toCurrencyUi(targetCurrencyCodes) }
        _currencies.value = sortCurrencies(mappedCurrencies, currentFilter.value)
    }

    fun addPairToFavorites(currencyUiModel: CurrencyUiModel) {
        viewModelScope.launch(Dispatchers.IO) {
            val currencyModel = currencyUiModel.toDomain(selectedCurrencyCode.value)
            favoritePairRepository.addPairToFavorites(currencyModel)
            _currencies.value = currencies.value.map {
                if (it.codeLabel == currencyUiModel.codeLabel) it.copy(isFavorite = true) else it
            }
        }
    }

    fun removePairFromFavorites(currencyUiModel: CurrencyUiModel) {
        viewModelScope.launch(Dispatchers.IO) {
            val pairCode = "${selectedCurrencyCode.value}/${currencyUiModel.codeLabel}"
            favoritePairRepository.removePairFromFavorites(pairCode)
            _currencies.value = currencies.value.map {
                if (it.codeLabel == currencyUiModel.codeLabel) it.copy(isFavorite = false) else it
            }
        }
    }

    fun applyFilter(sortOrder: SortOrder) {
        if (currentFilter.value != sortOrder) {
            _currentFilter.value = sortOrder
            _currencies.value = sortCurrencies(currencies.value, sortOrder)
        }
    }

    private fun sortCurrencies(currencies: List<CurrencyUiModel>, sortOrder: SortOrder): List<CurrencyUiModel> {
        return when (sortOrder) {
            SortOrder.CodeAZ -> currencies.sortedBy { it.codeLabel }
            SortOrder.CodeZA -> currencies.sortedByDescending { it.codeLabel }
            SortOrder.QuoteAsc -> currencies.sortedBy { it.rateValue.toDouble() }
            SortOrder.QuoteDesc -> currencies.sortedByDescending { it.rateValue.toDouble() }
        }
    }
}