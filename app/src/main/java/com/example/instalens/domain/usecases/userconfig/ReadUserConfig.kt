package com.example.instalens.domain.usecases.userconfig

import com.example.instalens.domain.manager.LocalUserConfigManager
import kotlinx.coroutines.flow.Flow

class ReadUserConfig(
    private val userConfigManager: LocalUserConfigManager
) {
    // Adding 'operator' keyword to use instance of the class directly as a function without explicitly calling .invoke()
    operator fun invoke(): Flow<Boolean> {
        return userConfigManager.readUserConfig()
    }
}