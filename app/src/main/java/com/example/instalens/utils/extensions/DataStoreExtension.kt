package com.example.instalens.utils.extensions

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.instalens.utils.Constants.USER_CONFIG_DATASTORE_NAME

/**
 * Extension property to access the preferences DataStore from any Context object.
 * This property is initialized with a read-only property delegate that ensures
 * a single instance of DataStore<Preferences> is used throughout the app.
 * The datastore is uniquely identified by the 'USER_CONFIG_DATASTORE_NAME'.
 */
private val readOnlyProperty = preferencesDataStore(name = USER_CONFIG_DATASTORE_NAME)
val Context.datastore: DataStore<Preferences> by readOnlyProperty