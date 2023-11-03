package com.example.instalens.domain.usecases.userconfig

import com.example.instalens.domain.manager.datastore.LocalUserConfigManager
import kotlinx.coroutines.flow.Flow

/**
 * Provides a functional interface to read user configuration settings.
 *
 * This class acts as a use-case in the clean architecture framework, encapsulating
 * the action of reading the user's configuration settings from a persistent store.
 * By using the 'operator' keyword with the 'invoke' function, instances of this class
 * can be invoked as if they were functions, enhancing readability and simplicity.
 *
 * @property userConfigManager The manager responsible for accessing user configuration data.
 */
class ReadUserConfig(
    private val userConfigManager: LocalUserConfigManager
) {

    /**
     * Invokes the read operation to obtain the user's configuration settings as a Flow.
     * The returned Flow emits the current user configuration settings allowing
     * the UI to update reactively as the configuration changes.
     *
     * @return A Flow that emits the current user configuration as a Boolean.
     */
    operator fun invoke(): Flow<Boolean> {
        return userConfigManager.readUserConfig()
    }
}