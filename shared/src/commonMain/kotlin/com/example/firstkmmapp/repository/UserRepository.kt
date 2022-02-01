package com.example.firstkmmapp.repository

import com.example.firstkmmapp.api.TestHttpApi
import com.example.firstkmmapp.data.User
import com.example.firstkmmapp.data.UserDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

interface UserRepository {
    @Throws(Exception::class)
    suspend fun getAllUsers(): Flow<List<User>?>
    suspend fun insertUser(user: User)
    suspend fun deleteUser(id: Long)
}


class UserRepositoryImpl(
    val userDataSource: UserDataSource,
    val testHttpApi: TestHttpApi
) : UserRepository {
    override suspend fun getAllUsers(): Flow<List<User>?> {
        var users = userDataSource.getAllUsers()
        if (!users.isNullOrEmpty()) {
            return userDataSource.getAllUsersAsFlow()
        }
        users = testHttpApi.getUsers()
        users.forEach { insertUser(it) }
        return flow { emit(users) }
    }

    override suspend fun insertUser(user: User) {
        userDataSource.insertUser(user)
    }

    override suspend fun deleteUser(id: Long) {
        userDataSource.deleteUserById(id)
    }
}