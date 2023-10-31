package com.example.instalens.domain.usecases.userconfig

data class UserConfigUseCases(
    val readUserConfig: ReadUserConfig,
    val writeUserConfig: WriteUserConfig
)