package com.pandulapeter.wagecounter.domain.di

import com.pandulapeter.wagecounter.domain.CalculateWorkStatusUseCase
import com.pandulapeter.wagecounter.domain.FormatHoursAndMinutesUseCase
import com.pandulapeter.wagecounter.domain.FormatHoursMinutesAndSecondsUseCase
import com.pandulapeter.wagecounter.domain.FormatMonetaryAmountUseCase
import com.pandulapeter.wagecounter.domain.FormatWorkHoursUseCase
import com.pandulapeter.wagecounter.domain.LoadConfigurationUseCase
import com.pandulapeter.wagecounter.domain.SaveConfigurationUseCase
import org.koin.dsl.module

val domainModule = module {
    factory { CalculateWorkStatusUseCase(get()) }
    factory { FormatHoursAndMinutesUseCase() }
    factory { FormatHoursMinutesAndSecondsUseCase(get()) }
    factory { FormatMonetaryAmountUseCase() }
    factory { FormatWorkHoursUseCase(get()) }
    factory { LoadConfigurationUseCase(get()) }
    factory { SaveConfigurationUseCase(get()) }
}