package com.example.instalens.domain.manager.datastore

import kotlinx.coroutines.flow.Flow

interface LocalUserConfigManager {
    suspend fun writeUserConfig()
    fun readUserConfig(): Flow<Boolean>
}