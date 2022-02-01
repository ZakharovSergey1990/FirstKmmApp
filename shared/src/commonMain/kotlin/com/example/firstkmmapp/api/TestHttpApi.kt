package com.example.firstkmmapp.api

import com.example.firstkmmapp.data.User
import io.ktor.client.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.logging.*
import io.ktor.client.request.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json

interface TestHttpApi {
    @Throws(Exception::class)
    suspend fun getUsers(): List<User>
}

class TestHttpApiImpl():TestHttpApi {

    private val httpClient = HttpClient(){
        install(Logging){
            level = LogLevel.ALL
        }
        install(JsonFeature){
            val json = kotlinx.serialization.json.Json { ignoreUnknownKeys = true }
            serializer = KotlinxSerializer(json)
        }
    }

    @Throws(Exception::class)
    override suspend fun getUsers(): List<User> {
        return withContext(Dispatchers.Default) {
            try {
                httpClient.get("https://jsonplaceholder.typicode.com/users")
            } catch (c: Throwable) {
                emptyList()
            }
        }
    }
}