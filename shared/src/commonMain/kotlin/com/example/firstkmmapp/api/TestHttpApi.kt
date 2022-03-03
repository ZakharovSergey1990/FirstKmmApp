package com.example.firstkmmapp.api

import com.example.firstkmmapp.data.Album
import com.example.firstkmmapp.data.Photo
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
    @Throws(Exception::class)
    suspend fun getAlbums(): List<Album>
    @Throws(Exception::class)
    suspend fun getPhotos(): List<Photo>
}

class TestHttpApiImpl():TestHttpApi {

    private val baseUrl = "https://jsonplaceholder.typicode.com"

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
                httpClient.get("$baseUrl/users")
            } catch (c: Throwable) {
                emptyList()
            }
        }
    }

    @Throws(Exception::class)
    override suspend fun getAlbums(): List<Album> {
        return withContext(Dispatchers.Default) {
            try {
                httpClient.get("$baseUrl/albums")
            } catch (c: Throwable) {
                emptyList()
            }
        }
    }

    @Throws(Exception::class)
    override suspend fun getPhotos(): List<Photo> {
        return withContext(Dispatchers.Default) {
            try {
                httpClient.get("$baseUrl/photos")
            } catch (c: Throwable) {
                emptyList()
            }
        }
    }
}