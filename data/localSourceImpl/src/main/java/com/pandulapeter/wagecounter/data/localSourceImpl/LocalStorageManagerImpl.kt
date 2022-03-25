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
    private var hourlyWage by MutablePreferenceFieldDelegate.Float(key = "hourlyWage", defaultValue = 15f)
    private var shouldUseCurrencyPrefix by MutablePreferenceFieldDelegate.Boolean(key = "shouldUseCurrencyPrefix", defaultValue = false)
    private var currencyData by MutablePreferenceFieldDelegate.String(key = "currencyData", defaultValue = "$")
    private var workDayLengthInMinutes by MutablePreferenceFieldDelegate.Int(key = "workDayLengthInMinutes", defaultValue = 8 * 60)
    private var workDayStartHour by MutablePreferenceFieldDelegate.Int(key = "workDayStartHour", defaultValue = 9)
    private var workDayStartMinute by MutablePreferenceFieldDelegate.Int(key = "workDayStartMinute", defaultValue = 0)
    override var savedConfiguration: Configuration
        get() = Configuration(
            hourlyWage = hourlyWage,
            currencyFormat = if (shouldUseCurrencyPrefix) CurrencyFormat.Prefix(currencyData) else CurrencyFormat.Suffix(currencyData),
            dayLengthInMinutes = workDayLengthInMinutes,
            startHour = workDayStartHour,
            startMinute = workDayStartMinute
        )
        set(value) {
            hourlyWage = value.hourlyWage
            shouldUseCurrencyPrefix = value.currencyFormat is CurrencyFormat.Prefix
            currencyData = value.currencyFormat.data
            workDayLengthInMinutes = value.dayLengthInMinutes
            workDayStartHour = value.startHour
            workDayStartMinute = value.startMinute
        }

    private sealed class MutablePreferenceFieldDelegate<T>(protected val key: kotlin.String, val defaultValue: T) : ReadWriteProperty<LocalStorageManagerImpl, T> {

        class Boolean(key: kotlin.String, defaultValue: kotlin.Boolean) : MutablePreferenceFieldDelegate<kotlin.Boolean>(key, defaultValue) {

            override fun getValue(thisRef: LocalStorageManagerImpl, property: KProperty<*>) = thisRef.preferences.getBoolean(key, defaultValue)

            override fun setValue(thisRef: LocalStorageManagerImpl, property: KProperty<*>, value: kotlin.Boolean) = thisRef.preferences.edit().putBoolean(key, value).apply()
        }

        class Int(key: kotlin.String, defaultValue: kotlin.Int) : MutablePreferenceFieldDelegate<kotlin.Int>(key, defaultValue) {

            override fun getValue(thisRef: LocalStorageManagerImpl, property: KProperty<*>) = thisRef.preferences.getInt(key, defaultValue)

            override fun setValue(thisRef: LocalStorageManagerImpl, property: KProperty<*>, value: kotlin.Int) = thisRef.preferences.edit().putInt(key, value).apply()
        }

        class Float(key: kotlin.String, defaultValue: kotlin.Float) : MutablePreferenceFieldDelegate<kotlin.Float>(key, defaultValue) {

            override fun getValue(thisRef: LocalStorageManagerImpl, property: KProperty<*>) = thisRef.preferences.getFloat(key, defaultValue)

            override fun setValue(thisRef: LocalStorageManagerImpl, property: KProperty<*>, value: kotlin.Float) = thisRef.preferences.edit().putFloat(key, value).apply()
        }

        class String(key: kotlin.String, defaultValue: kotlin.String) : MutablePreferenceFieldDelegate<kotlin.String>(key, defaultValue) {

            override fun getValue(thisRef: LocalStorageManagerImpl, property: KProperty<*>) = thisRef.preferences.getString(key, defaultValue).orEmpty()

            override fun setValue(thisRef: LocalStorageManagerImpl, property: KProperty<*>, value: kotlin.String) = thisRef.preferences.edit().putString(key, value).apply()
        }
    }
}