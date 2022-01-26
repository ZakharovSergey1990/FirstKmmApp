package com.example.firstkmmapp.data

import com.example.db.TestDatabase
import com.example.db.UserEntity
import com.example.db.UserEntityQueries
import com.squareup.sqldelight.ColumnAdapter
import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json


interface UserDataSource {
   // fun getAllUsersAsFlow(): Flow<List<User>?>

    suspend fun deleteUserById(id: Long)

    suspend fun insertUser(user: User)

    fun getAllUsers(): List<User>?

}

class UserDataSourceImpl(private val driver: DriverFactory) : UserDataSource {

    private val database = createDatabase(driver.createDriver())

    private var queries: UserEntityQueries = database.userEntityQueries

   // override fun getAllUsersAsFlow(): Flow<List<User>> {
//
//        return queries.getAllUsers().asFlow().mapToList().map { list -> mapUsersToUserList(list) }
//    }

    override suspend fun deleteUserById(id: Long) {

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


    private fun createDatabase(driver: SqlDriver): TestDatabase {

        val addressAdapter = object : ColumnAdapter<Address, String> {
            override fun decode(databaseValue: String): Address {
                return Json.decodeFromString<Address>(databaseValue)
            }

            override fun encode(value: Address): String {
                return Json.encodeToString(value)
            }

        }

        val companyAdapter = object : ColumnAdapter<Company, String> {
            override fun decode(databaseValue: String): Company {
                return Json.decodeFromString<Company>(databaseValue)
            }

            override fun encode(value: Company): String {
                return Json.encodeToString(value)
            }
        }

        return TestDatabase(
            driver,
            UserEntity.Adapter(addressAdapter = addressAdapter, companyAdapter = companyAdapter)
        )
    }
}