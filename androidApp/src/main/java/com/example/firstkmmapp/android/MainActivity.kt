package com.example.firstkmmapp.android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.firstkmmapp.Greeting
import android.widget.TextView
import com.example.firstkmmapp.data.DriverFactory

fun greet(): String {
    return Greeting().greeting()
}

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
val driver = DriverFactory.createDriver(application)
        val userDataSource = UserDataSourceImpl(driver)

        val tv: TextView = findViewById(R.id.text_view)
        tv.text = greet()
    }
}
