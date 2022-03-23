package com.pandulapeter.wagecounter.domain

import com.pandulapeter.wagecounter.data.model.CurrencyFormat


class FormatMonetaryAmountUseCase {

    operator fun invoke(
        currencyFormat: CurrencyFormat,
        amount: Float
    ): String {
        val prefix = (currencyFormat as? CurrencyFormat.Prefix)?.data ?: ""
        val suffix = (currencyFormat as? CurrencyFormat.Suffix)?.data ?: ""
        return "$prefix%.2f$suffix".format(amount)
    }
}