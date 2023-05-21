package com.android.taco.di

import com.android.taco.remote.Api
import com.android.taco.remote.Client
import com.android.taco.repository.ApiRepository
import com.android.taco.repository.AuthRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
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
    fun provideAuthRepository(
        auth: FirebaseAuth
    ) = AuthRepository(auth)

    @Singleton
    @Provides
    fun provideFirebaseAuth(): FirebaseAuth {
        return Firebase.auth
    }

    @Singleton
    @Provides
    fun provideApi(): Api {
        return Client.getInstance()
    }
}