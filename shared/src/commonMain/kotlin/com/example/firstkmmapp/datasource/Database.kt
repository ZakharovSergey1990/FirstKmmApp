package com.example.firstkmmapp.datasource

import com.example.db.*
import com.example.firstkmmapp.data.Address
import com.example.firstkmmapp.data.Company
import com.example.firstkmmapp.datasource.DriverFactory
import com.squareup.sqldelight.ColumnAdapter
import com.squareup.sqldelight.db.SqlDriver
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json


interface DatabaseQueries{
   fun getUserTableQueries(): UserEntityQueries
   fun getAlbumTableQueries(): AlbumQueries
   fun getPhotoTableQueries(): PhotoEntityQueries
}

class DatabaseQueriesImpl(private val driver: DriverFactory): DatabaseQueries {

    private val database = createDatabase(driver.createDriver())

    override fun getUserTableQueries(): UserEntityQueries {
        return database.userEntityQueries
    }

    override fun getAlbumTableQueries(): AlbumQueries {
        return database.albumQueries
    }

    override fun getPhotoTableQueries(): PhotoEntityQueries {
        return database.photoEntityQueries
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