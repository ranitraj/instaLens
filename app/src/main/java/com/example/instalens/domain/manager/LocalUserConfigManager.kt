package com.example.instalens.domain.manager

import kotlinx.coroutines.flow.Flow

interface LocalUserConfigManager {
    suspend fun writeUserConfig()
    suspend fun readUserConfig(): Flow<Boolean>
}