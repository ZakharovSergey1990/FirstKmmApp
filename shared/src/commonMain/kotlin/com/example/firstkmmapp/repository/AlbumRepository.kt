package com.example.firstkmmapp.repository

import com.example.firstkmmapp.data.Album
import com.example.firstkmmapp.datasource.localdatasource.AlbumDataSource
import kotlinx.coroutines.flow.Flow

interface AlbumRepository {
    @Throws(Exception::class)
    suspend fun getAllAlbums(userId: Int): Flow<List<Album>?>
    suspend fun insertAlbum(album: Album)
    suspend fun deleteAlbum(id: Long)
}

class AlbumRepositoryImpl(
   val albumDataSource: AlbumDataSource
): AlbumRepository {
    override suspend fun getAllAlbums(userId: Int): Flow<List<Album>?> {
        return  albumDataSource.getAlbumsByUserId(userId)
    }

    override suspend fun insertAlbum(album: Album) {
        albumDataSource.insertAlbum(album)
    }

    override suspend fun deleteAlbum(id: Long) {
       albumDataSource.deleteAlbumById(id.toInt())
    }

}