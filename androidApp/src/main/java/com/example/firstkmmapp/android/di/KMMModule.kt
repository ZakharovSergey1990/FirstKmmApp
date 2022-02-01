package com.example.firstkmmapp.android.di

import com.example.firstkmmapp.di.MultiplatformSDK
import com.example.firstkmmapp.di.userRepository
import com.example.firstkmmapp.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
object KMMModule {

    @Provides
    fun provideUserRepository(): UserRepository {
        return MultiplatformSDK.userRepository
    }
}