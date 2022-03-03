package com.example.firstkmmapp.repository

import com.example.firstkmmapp.api.TestHttpApi
import com.example.firstkmmapp.datasource.localdatasource.AlbumDataSource
import com.example.firstkmmapp.datasource.localdatasource.PhotoDataSource
import com.example.firstkmmapp.data.User
import com.example.firstkmmapp.datasource.localdatasource.UserDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

interface UserRepository {
    @Throws(Exception::class)
    suspend fun getAllUsers(): Flow<List<User>?>
    suspend fun insertUser(user: User)
    suspend fun deleteUser(id: Long)
    suspend fun updateUsers(): List<User>
}


class UserRepositoryImpl(
    val userDataSource: UserDataSource,
    val testHttpApi: TestHttpApi,
    val albumDataSource: AlbumDataSource,
    val photoDataSource: PhotoDataSource
) : UserRepository {
    override suspend fun getAllUsers(): Flow<List<User>?> {
        var users = userDataSource.getAllUsers()
        if (!users.isNullOrEmpty()) {
            return userDataSource.getAllUsersAsFlow()
        }
        users = updateUsers()
        return flow { emit(users) }
    }

    override suspend fun insertUser(user: User) {
        userDataSource.insertUser(user)
    }

    override suspend fun deleteUser(id: Long) {
        userDataSource.deleteUserById(id)
    }

    override suspend fun updateUsers(): List<User> {
     val users = testHttpApi.getUsers()
     users.forEach {
            insertUser(it)
        }
        testHttpApi.getAlbums().forEach {
            albumDataSource.insertAlbum(it)
        }
        testHttpApi.getPhotos().forEach {
            photoDataSource.insertPhoto(it) }
        return users
    }
}