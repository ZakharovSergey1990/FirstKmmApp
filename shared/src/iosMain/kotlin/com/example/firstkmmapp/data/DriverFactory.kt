package com.example.firstkmmapp.data

import com.example.db.TestDatabase
import com.example.firstkmmapp.KMMContext
import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.drivers.native.NativeSqliteDriver

actual class DriverFactory actual constructor(context: KMMContext) {
    actual fun createDriver(): SqlDriver {
        return NativeSqliteDriver(TestDatabase.Schema, "TestDatabase.db")
    }
}