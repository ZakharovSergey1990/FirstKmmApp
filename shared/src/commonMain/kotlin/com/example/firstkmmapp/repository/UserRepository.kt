package com.example.firstkmmapp.repository

import com.example.firstkmmapp.api.TestHttpApi
import com.example.firstkmmapp.data.User
import com.example.firstkmmapp.data.UserDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

interface UserRepository {
    @Throws(Exception::class)
    suspend fun getAllUsers(): List<User>?
    suspend fun insertUser(user: User)
}

class UserRepositoryImpl(
    val userDataSource: UserDataSource,
    val testHttpApi: TestHttpApi
) : UserRepository {

    @Throws(Exception::class)
    override suspend fun getAllUsers(): List<User>? {
        var users = userDataSource.getAllUsers()
        if (!users.isNullOrEmpty()) {
            return users
        }
        users = testHttpApi.getUsers()
        users.forEach { insertUser(it) }
        return users
    }

    override suspend fun insertUser(user: User) {
        userDataSource.insertUser(user)
    }
}