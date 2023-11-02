package com.example.instalens.utils.extensions

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.instalens.utils.Constants.USER_CONFIG_DATASTORE_NAME

private val readOnlyProperty = preferencesDataStore(name = USER_CONFIG_DATASTORE_NAME)
val Context.datastore: DataStore<Preferences> by readOnlyProperty