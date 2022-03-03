package com.example.firstkmmapp.datasource.localdatasource

import com.example.db.AlbumEntity
import com.example.firstkmmapp.data.Album
import com.example.firstkmmapp.datasource.DatabaseQueries
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

interface AlbumDataSource {
   fun getAlbumsByUserId(userId: Int): Flow<List<Album>>
   fun deleteAlbumById(albumId: Int)
   fun insertAlbum(album: Album)
}

class AlbumDataSourceImpl(private val database: DatabaseQueries): AlbumDataSource {

   private val queries = database.getAlbumTableQueries()

    override fun getAlbumsByUserId(userId: Int): Flow<List<Album>> {
   return queries.getAllAlbums().asFlow().mapToList()
       .map { list -> mapUsersToAlbumList(list)
           //.filter { it.userId == userId }
       }
    }

    override fun deleteAlbumById(albumId: Int) {
       queries.deleteAlbum(albumId.toLong())
    }

    override fun insertAlbum(album: Album) {
        queries.insertAlbum(album.id.toLong(), album.title, album.userId.toLong())
    }


    private fun mapUsersToAlbumList(list: List<AlbumEntity>): List<Album> {
        return list.map {
            Album(
                id = it.id.toInt(), userId = it.userId.toInt(), title = it.title
            )
        }
    }
}