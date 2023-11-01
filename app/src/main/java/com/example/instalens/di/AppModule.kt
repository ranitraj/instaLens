package com.example.instalens.di

import android.app.Application
import com.example.instalens.data.manager.datastore.LocalUserConfigManagerImpl
import com.example.instalens.domain.manager.datastore.LocalUserConfigManager
import com.example.instalens.domain.usecases.userconfig.ReadUserConfig
import com.example.instalens.domain.usecases.userconfig.UserConfigUseCases
import com.example.instalens.domain.usecases.userconfig.WriteUserConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    @Singleton
    fun provideLocalUserConfigManager(
        application: Application
    ): LocalUserConfigManager = LocalUserConfigManagerImpl(application)


    // Use-Cases
    @Provides
    @Singleton
    fun provideUserConfigUseCases(
        localUserConfigManager: LocalUserConfigManager
    ): UserConfigUseCases = UserConfigUseCases(
        readUserConfig = ReadUserConfig(localUserConfigManager),
        writeUserConfig = WriteUserConfig(localUserConfigManager)
    )
}