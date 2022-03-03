package com.example.firstkmmapp.datasource.localdatasource

import com.example.db.UserEntity
import com.example.db.UserEntityQueries
import com.example.firstkmmapp.datasource.DatabaseQueries
import com.example.firstkmmapp.data.User
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext


interface UserDataSource {
    fun getAllUsersAsFlow(): Flow<List<User>?>

    suspend fun deleteUserById(id: Long)

    suspend fun insertUser(user: User)

    fun getAllUsers(): List<User>?

}

class UserDataSourceImpl(private val database: DatabaseQueries) : UserDataSource {

    private var queries: UserEntityQueries = database.getUserTableQueries()

    override fun getAllUsersAsFlow(): Flow<List<User>> {
        return queries.getAllUsers().asFlow().mapToList().map { list -> mapUsersToUserList(list) }
    }

    override suspend fun deleteUserById(id: Long) {
        withContext(Dispatchers.Default) {
            queries.deleteUser(id)
        }
    }

    override suspend fun insertUser(user: User) {
        withContext(Dispatchers.Default) {
            queries.insertUser(
                user.id,
                user.name,
                user.username,
                user.email,
                user.address,
                user.phone,
                user.website,
                user.company
            )
        }
    }

    override fun getAllUsers(): List<User> {
        return queries.getAllUsers().executeAsList().map {
            User(
                id = it.id,
                name = it.name,
                username = it.username,
                email = it.email,
                address = it.address,
                phone = it.phone,
                website = it.website,
                company = it.company
            )
        }
    }


    private fun mapUsersToUserList(list: List<UserEntity>): List<User> {
        return list.map {
            User(
                id = it.id,
                name = it.name,
                username = it.username,
                email = it.email,
                address = it.address,
                phone = it.phone,
                website = it.website,
                company = it.company
            )
        }
    }



}