package com.example.instalens.domain.usecases.userconfig

import com.example.instalens.domain.manager.datastore.LocalUserConfigManager

/**
 * Encapsulates the operation of writing the user's configuration settings to a persistent store.
 *
 * This class serves as a use-case within the clean architecture, focusing on the action of persisting
 * the user's configuration. The inclusion of the 'operator' keyword allows for invoking an instance of this class
 * as if it were a function, simplifying the syntax when this operation needs to be performed in the code.
 *
 * @property userConfigManager The manager responsible for handling the persistence of user configuration data.
 */
class WriteUserConfig(
    private val userConfigManager: LocalUserConfigManager
) {

    /**
     * Performs the write operation to save the user's configuration settings.
     * The function is marked 'suspend' to allow for the operation to be performed
     * asynchronously, ensuring that the main thread is not blocked.
     *
     * Usage of this function will require a coroutine context, reflecting the asynchronous nature
     * of the operation it performs.
     */
    suspend operator fun invoke() {
        userConfigManager.writeUserConfig()
    }
}