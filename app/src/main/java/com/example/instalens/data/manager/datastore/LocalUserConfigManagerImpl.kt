package com.example.instalens.data.manager.datastore

import android.content.Context
import androidx.datastore.preferences.core.edit
import com.example.instalens.data.extensions.datastore
import com.example.instalens.data.utils.PreferenceDatastoreKeys
import com.example.instalens.domain.manager.datastore.LocalUserConfigManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LocalUserConfigManagerImpl @Inject constructor(
    private val context: Context
): LocalUserConfigManager {

    override suspend fun writeUserConfig() {
        // Using extension function obtain instance of 'userConfigDatastore' to write value
        context.datastore.edit { userConfigDatastore ->
            userConfigDatastore[PreferenceDatastoreKeys.USER_CONFIG] = true
        }
    }

    override fun readUserConfig(): Flow<Boolean> {
        // Using extension function obtain instance of 'userConfigDatastore' to read keys
        return context.datastore.data
            .map { preferences ->
                // Initializing value to 'false' if key does-not-exist
                preferences[PreferenceDatastoreKeys.USER_CONFIG] ?: false
        }
    }

}