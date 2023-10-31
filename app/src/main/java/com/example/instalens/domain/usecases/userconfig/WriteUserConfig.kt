package com.example.instalens.domain.usecases.userconfig

import com.example.instalens.domain.manager.LocalUserConfigManager

class WriteUserConfig(
    private val userConfigManager: LocalUserConfigManager
) {
    // Adding 'operator' keyword to use instance of the class directly as a function without explicitly calling .invoke()
    suspend operator fun invoke() {
        userConfigManager.writeUserConfig()
    }
}