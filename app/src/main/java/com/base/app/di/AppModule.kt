package com.base.app.di

import com.base.app.remote.Api
import com.base.app.remote.Client
import com.base.app.repository.ApiRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Singleton
    @Provides
    fun provideApiRepository(
        api: Api
    ) = ApiRepository(api)

    @Singleton
    @Provides
    fun provideApi(): Api {
        return Client.getInstance()
    }
}