package com.example.firstkmmapp.data

import com.squareup.sqldelight.db.SqlDriver
import testdatabase.TestDatabase

actual class DriverFactory {
    actual fun createDriver(): SqlDriver {
        return NativeSqliteDriver(TestDatabase.Schema, "TestDatabase.db")
    }
}