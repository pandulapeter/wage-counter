package com.pandulapeter.wagecounter.domain.di

import com.pandulapeter.wagecounter.domain.CalculateWageStatusUseCase
import com.pandulapeter.wagecounter.domain.FormatHoursAndMinutesUseCase
import com.pandulapeter.wagecounter.domain.FormatHoursMinutesAndSecondsUseCase
import com.pandulapeter.wagecounter.domain.FormatMonetaryAmountUseCase
import com.pandulapeter.wagecounter.domain.FormatWorkingHoursUseCase
import com.pandulapeter.wagecounter.domain.GetConfigurationUseCase
import com.pandulapeter.wagecounter.domain.UpdateConfigurationUseCase
import org.koin.dsl.module

val domainModule = module {
    factory { CalculateWageStatusUseCase(get()) }
    factory { FormatHoursAndMinutesUseCase() }
    factory { FormatHoursMinutesAndSecondsUseCase(get()) }
    factory { FormatMonetaryAmountUseCase() }
    factory { FormatWorkingHoursUseCase(get()) }
    factory { GetConfigurationUseCase(get()) }
    factory { UpdateConfigurationUseCase(get()) }
}