package com.example.instalens.data.manager

import android.content.Context
import com.example.instalens.domain.manager.LocalUserConfigManager
import kotlinx.coroutines.flow.Flow

class LocalUserConfigManagerImpl(
    private val context: Context
): LocalUserConfigManager {

    override suspend fun writeUserConfig() {
        TODO("Not yet implemented")
    }

    override suspend fun readUserConfig(): Flow<Boolean> {
        TODO("Not yet implemented")
    }

}