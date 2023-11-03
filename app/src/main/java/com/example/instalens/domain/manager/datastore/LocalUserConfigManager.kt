package com.example.instalens.domain.manager.datastore

import kotlinx.coroutines.flow.Flow

/**
 * Manages local user configuration settings.
 *
 * This interface defines methods for persisting and retrieving user configuration
 * preferences, abstracting the underlying data storage mechanism.
 */
interface LocalUserConfigManager {
    suspend fun writeUserConfig()
    fun readUserConfig(): Flow<Boolean>
}