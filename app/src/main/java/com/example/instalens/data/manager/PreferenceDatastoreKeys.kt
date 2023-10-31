package com.example.instalens.data.manager

import androidx.datastore.preferences.core.booleanPreferencesKey
import com.example.instalens.utils.Constants

object PreferenceDatastoreKeys {
    val USER_CONFIG = booleanPreferencesKey(name = Constants.USER_CONFIG)
}