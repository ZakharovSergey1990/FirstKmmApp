package com.example.firstkmmapp.data

import android.content.Context
import com.example.firstkmmapp.KMMContext
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver
import com.example.db.TestDatabase


actual class DriverFactory actual constructor (val context : KMMContext) {
    actual fun createDriver(): SqlDriver {
        return AndroidSqliteDriver(TestDatabase.Schema, (context as Context) , "TestDatabase.db")
    }
}