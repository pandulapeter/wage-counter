package com.pandulapeter.wagecounter.data.localSourceImpl

import com.pandulapeter.wagecounter.data.model.Configuration

internal interface LocalStorageManager {

    var savedConfiguration: Configuration
}