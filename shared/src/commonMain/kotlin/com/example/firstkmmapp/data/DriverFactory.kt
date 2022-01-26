package com.example.firstkmmapp.data

import com.example.firstkmmapp.KMMContext
import com.squareup.sqldelight.db.SqlDriver

expect class DriverFactory(context : KMMContext) {
    fun createDriver(): SqlDriver
}