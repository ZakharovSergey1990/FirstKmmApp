package com.example.firstkmmapp.datasource

import android.content.Context
import com.example.db.TestDatabase
import com.example.firstkmmapp.KMMContext
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver

actual class DriverFactory actual constructor (val context : KMMContext) {
    actual fun createDriver(): SqlDriver {
        return AndroidSqliteDriver(TestDatabase.Schema, (context as Context), "TestDatabase.db")
    }
}