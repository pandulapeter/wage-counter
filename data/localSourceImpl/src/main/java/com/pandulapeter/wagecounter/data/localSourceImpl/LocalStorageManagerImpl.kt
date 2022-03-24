package com.pandulapeter.wagecounter.data.localSourceImpl

import android.content.Context
import com.pandulapeter.wagecounter.data.model.Configuration
import com.pandulapeter.wagecounter.data.model.CurrencyFormat
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

internal class LocalStorageManagerImpl(
    context: Context
) : LocalStorageManager {

    private val preferences = context.applicationContext.getSharedPreferences("preferences", Context.MODE_PRIVATE)
    private var isConfigurationSet by MutablePreferenceFieldDelegate.Boolean("isConfigurationSet")
    private var hourlyWage by MutablePreferenceFieldDelegate.Float("hourlyWage")
    private var shouldUseCurrencyPrefix by MutablePreferenceFieldDelegate.Boolean("shouldUseCurrencyPrefix")
    private var currencyData by MutablePreferenceFieldDelegate.String("currencyData")
    private var workDayLengthInMinutes by MutablePreferenceFieldDelegate.Int("workDayLengthInMinutes")
    private var workDayStartHour by MutablePreferenceFieldDelegate.Int("workDayStartHour")
    private var workDayStartMinute by MutablePreferenceFieldDelegate.Int("workDayStartMinute")
    override var savedConfiguration: Configuration?
        get() = if (isConfigurationSet) Configuration(
            hourlyWage = hourlyWage,
            currencyFormat = if (shouldUseCurrencyPrefix) CurrencyFormat.Prefix(currencyData) else CurrencyFormat.Suffix(currencyData),
            workDayLengthInMinutes = workDayLengthInMinutes,
            workDayStartHour = workDayStartHour,
            workDayStartMinute = workDayStartMinute
        ) else null
        set(value) {
            isConfigurationSet = value != null
            value?.let {
                hourlyWage = value.hourlyWage
                shouldUseCurrencyPrefix = value.currencyFormat is CurrencyFormat.Prefix
                currencyData = value.currencyFormat.data
                workDayLengthInMinutes = value.workDayLengthInMinutes
                workDayStartHour = value.workDayStartHour
                workDayStartMinute = value.workDayStartMinute
            }
        }

    private sealed class MutablePreferenceFieldDelegate<T>(protected val key: kotlin.String) : ReadWriteProperty<LocalStorageManagerImpl, T> {

        class Boolean(key: kotlin.String) : MutablePreferenceFieldDelegate<kotlin.Boolean>(key) {

            override fun getValue(thisRef: LocalStorageManagerImpl, property: KProperty<*>) = thisRef.preferences.getBoolean(key, DEFAULT_BOOLEAN)

            override fun setValue(thisRef: LocalStorageManagerImpl, property: KProperty<*>, value: kotlin.Boolean) = thisRef.preferences.edit().putBoolean(key, value).apply()
        }

        class Int(key: kotlin.String) : MutablePreferenceFieldDelegate<kotlin.Int>(key) {

            override fun getValue(thisRef: LocalStorageManagerImpl, property: KProperty<*>) = thisRef.preferences.getInt(key, DEFAULT_INT)

            override fun setValue(thisRef: LocalStorageManagerImpl, property: KProperty<*>, value: kotlin.Int) = thisRef.preferences.edit().putInt(key, value).apply()
        }

        class Float(key: kotlin.String) : MutablePreferenceFieldDelegate<kotlin.Float>(key) {

            override fun getValue(thisRef: LocalStorageManagerImpl, property: KProperty<*>) = thisRef.preferences.getFloat(key, DEFAULT_FLOAT)

            override fun setValue(thisRef: LocalStorageManagerImpl, property: KProperty<*>, value: kotlin.Float) = thisRef.preferences.edit().putFloat(key, value).apply()
        }

        class String(key: kotlin.String) : MutablePreferenceFieldDelegate<kotlin.String>(key) {

            override fun getValue(thisRef: LocalStorageManagerImpl, property: KProperty<*>) = thisRef.preferences.getString(key, DEFAULT_STRING).orEmpty()

            override fun setValue(thisRef: LocalStorageManagerImpl, property: KProperty<*>, value: kotlin.String) = thisRef.preferences.edit().putString(key, value).apply()
        }

        companion object {
            private const val DEFAULT_BOOLEAN = false
            private const val DEFAULT_INT = 0
            private const val DEFAULT_FLOAT = 0f
            private const val DEFAULT_STRING = ""
        }
    }
}