package com.example.firstkmmapp.datasource

import com.example.db.TestDatabase
import com.example.firstkmmapp.KMMContext
import com.squareup.sqldelight.db.SqlDriver

actual class DriverFactory actual constructor(context: KMMContext) {
    actual fun createDriver(): SqlDriver {
        return NativeSqliteDriver(TestDatabase.Schema, "TestDatabase.db")
    }
}