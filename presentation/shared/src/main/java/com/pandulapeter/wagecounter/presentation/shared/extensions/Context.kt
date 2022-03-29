package com.pandulapeter.wagecounter.presentation.shared.extensions

import android.content.Context
import android.content.res.Resources
import android.util.TypedValue
import androidx.annotation.AttrRes
import androidx.annotation.ColorInt

@ColorInt
internal fun Context.resolveThemeAttribute(@AttrRes attributeResourceId: Int): Int {
    val typedValue = TypedValue()
    val theme: Resources.Theme = theme
    theme.resolveAttribute(attributeResourceId, typedValue, true)
    return typedValue.data
}