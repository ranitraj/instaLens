package com.example.instalens.data.extensions

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.instalens.utils.Constants.USER_CONFIG_DATASTORE_NAME

val Context.datastore: DataStore<Preferences> by preferencesDataStore(name = USER_CONFIG_DATASTORE_NAME)