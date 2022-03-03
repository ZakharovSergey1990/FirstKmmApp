package com.example.firstkmmapp.android.di

import com.example.firstkmmapp.di.MultiplatformSDK
import com.example.firstkmmapp.di.albumRepository
import com.example.firstkmmapp.di.photoRepository
import com.example.firstkmmapp.di.userRepository
import com.example.firstkmmapp.repository.AlbumRepository
import com.example.firstkmmapp.repository.PhotoRepository
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

    @Provides
    fun provideAlbumRepository(): AlbumRepository {
        return MultiplatformSDK.albumRepository
    }

    @Provides
    fun providePhotoRepository(): PhotoRepository {
        return MultiplatformSDK.photoRepository
    }
}