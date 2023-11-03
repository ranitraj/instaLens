package com.example.instalens.data.utils

import androidx.datastore.preferences.core.booleanPreferencesKey
import com.example.instalens.utils.Constants

/**
 * Defines keys for accessing data in the preferences DataStore.
 */
object PreferenceDatastoreKeys {
    val USER_CONFIG = booleanPreferencesKey(name = Constants.USER_CONFIG)
}