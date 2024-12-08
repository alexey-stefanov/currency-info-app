package com.alexstefanov.currencyinfoapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexstefanov.currencyinfoapp.data.repository.currency.CurrencyRepository
import com.alexstefanov.currencyinfoapp.data.repository.favoritecurrency.FavoritePairRepository
import com.alexstefanov.currencyinfoapp.domain.mapper.toCurrencyUi
import com.alexstefanov.currencyinfoapp.domain.mapper.toDomain
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
                    currency.copy(
                        isFavorite = currency.codeLabel in favoritePairs
                    )
                }
            }
    }

    fun selectCurrency(currency: String) {
        _selectedCurrencyCode.value = currency
        viewModelScope.launch(Dispatchers.IO) {
            getLatestCurrencies()
        }
    }

    private suspend fun getCurrencySymbols() {
        val currencySymbols = currencyRepository.getCurrencySymbols()
        _currencySymbols.value = currencySymbols
    }

    private suspend fun getLatestCurrencies() {
        val selectedCurrencyCode = selectedCurrencyCode.value
        val latestCurrencies = currencyRepository.getLatestCurrencies(selectedCurrencyCode)
        val targetCurrencyCodes = favoritePairRepository.getPairsForBaseCurrency(selectedCurrencyCode)
        val mappedCurrencies = latestCurrencies.map { it.toCurrencyUi(targetCurrencyCodes) }
        _currencies.value = mappedCurrencies
    }

    fun addPairToFavorites(currencyUiModel: CurrencyUiModel) {
        viewModelScope.launch(Dispatchers.IO) {
            val currencyModel = currencyUiModel.toDomain(selectedCurrencyCode.value)
            favoritePairRepository.addPairToFavorites(currencyModel)
            _currencies.value = _currencies.value.map {
                if (it.codeLabel == currencyUiModel.codeLabel) it.copy(isFavorite = true) else it
            }
        }
    }

    fun removePairFromFavorites(currencyUiModel: CurrencyUiModel) {
        viewModelScope.launch(Dispatchers.IO) {
            val pairCode = "${selectedCurrencyCode.value}/${currencyUiModel.codeLabel}"
            favoritePairRepository.removePairFromFavorites(pairCode)
            _currencies.value = _currencies.value.map {
                if (it.codeLabel == currencyUiModel.codeLabel) it.copy(isFavorite = false) else it
            }
        }
    }
}