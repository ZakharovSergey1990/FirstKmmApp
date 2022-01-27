package com.example.firstkmmapp.android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.firstkmmapp.Greeting
import android.widget.TextView
import com.example.firstkmmapp.api.TestHttpApi
import com.example.firstkmmapp.api.TestHttpApiImpl
import com.example.firstkmmapp.data.*
import com.example.firstkmmapp.repository.UserRepositoryImpl
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

fun greet(): String {
    return Greeting().greeting()
}

class MainActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
val driver = DriverFactory(application)
        val userDataSource = UserDataSourceImpl(driver)
val userRepository = UserRepositoryImpl(userDataSource, TestHttpApiImpl())
        val tv: TextView = findViewById(R.id.text_view)
        tv.text = greet()

        MainScope().launch {
//            userDataSource.insertUser(user = User(1, "sdf", "adsfg", "sdgf",
//                Address("sdf", "asdf", "sd", "sdf", Geo("sdf", "sdf")),
//                "123213", "dsf@", Company("sdef", "sdf", "sdfg")
//            ))
           tv.text = userRepository.getAllUsers().toString()
        }
    }
}
