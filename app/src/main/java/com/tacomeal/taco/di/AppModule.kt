package com.tacomeal.taco.di

import com.tacomeal.taco.data.SharedPref
import com.tacomeal.taco.remote.Api
import com.tacomeal.taco.remote.Client
import com.tacomeal.taco.repository.ApiRepository
import com.tacomeal.taco.repository.AuthRepository
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
    fun provideSharedPreference() : com.tacomeal.taco.data.SharedPref = com.tacomeal.taco.data.SharedPref.invoke()

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