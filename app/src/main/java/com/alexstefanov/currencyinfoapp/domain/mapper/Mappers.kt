package com.alexstefanov.currencyinfoapp.domain.mapper

import com.alexstefanov.currencyinfoapp.data.local.entity.FavoritePairEntity
import com.alexstefanov.currencyinfoapp.data.remote.response.CurrenciesResponse
import com.alexstefanov.currencyinfoapp.domain.model.CurrencyModel
import com.alexstefanov.currencyinfoapp.presentation.model.CurrencyUiModel

fun CurrenciesResponse.toDomain(): List<CurrencyModel> {
    return rates.map { (code, rate) ->
        CurrencyModel(
            baseCurrencyCode = base,
            targetCurrencyCode = code,
            rate = rate
        )
    }
}

fun FavoritePairEntity.toDomain(): CurrencyModel {
    return CurrencyModel(
        baseCurrencyCode = pair.split("/").first(),
        targetCurrencyCode = pair.split("/").last(),
        rate = rate
    )
}

fun CurrencyModel.toEntity(): FavoritePairEntity {
    return FavoritePairEntity(
        pair = "$baseCurrencyCode/$targetCurrencyCode",
        baseCurrencyCode = baseCurrencyCode,
        targetCurrencyCode = targetCurrencyCode,
        rate = rate
    )
}

fun CurrencyModel.toCurrencyUi(targetCurrencyCodes: List<String>): CurrencyUiModel {
    return CurrencyUiModel(
        codeLabel = targetCurrencyCode,
        rateValue = rate.toString(),
        isFavorite = targetCurrencyCode in targetCurrencyCodes
    )
}

fun CurrencyModel.toFavoritePairUi(): CurrencyUiModel {
    return CurrencyUiModel(
        codeLabel = "$baseCurrencyCode/$targetCurrencyCode",
        rateValue = rate.toString(),
        isFavorite = true
    )
}

// only for currency
fun CurrencyUiModel.toDomain(baseCurrencyCode: String): CurrencyModel {
    return CurrencyModel(
        baseCurrencyCode = baseCurrencyCode,
        targetCurrencyCode = codeLabel,
        rate = rateValue.toDouble()
    )
}