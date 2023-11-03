package com.example.instalens.domain.usecases.userconfig

/**
 * Groups all the use cases related to the user configuration into a single data class.
 *
 * This class is part of an application's use case layer, following the principles of clean architecture.
 * By grouping related use cases, the class provides a cohesive API for the user configuration operations.
 * It can be passed around in the codebase wherever these operations are needed, thus promoting the Dependency Inversion principle.
 *
 * @property readUserConfig An instance of [ReadUserConfig], representing the use case to read the user's configuration.
 * @property writeUserConfig An instance of [WriteUserConfig], representing the use case to write the user's configuration.
 */
data class UserConfigUseCases(
    val readUserConfig: ReadUserConfig,
    val writeUserConfig: WriteUserConfig
)