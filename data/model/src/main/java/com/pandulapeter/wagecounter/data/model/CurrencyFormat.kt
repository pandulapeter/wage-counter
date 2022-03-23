package com.pandulapeter.wagecounter.data.model

sealed class CurrencyFormat {

    abstract val data: String

    data class Prefix(override val data: String) : CurrencyFormat()

    data class Suffix(override val data: String) : CurrencyFormat()
}