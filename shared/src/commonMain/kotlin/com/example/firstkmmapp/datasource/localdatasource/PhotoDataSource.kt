package com.example.firstkmmapp.datasource.localdatasource

import com.example.db.PhotoEntity
import com.example.firstkmmapp.datasource.DatabaseQueries
import com.example.firstkmmapp.data.Photo
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

interface PhotoDataSource {
    fun getPhotosByAlbumId(albumId: Int): Flow<List<Photo>>
    fun deletePhotoById(photoId: Int)
    fun insertPhoto(photo: Photo)
}

class PhotoDataSourceImpl(private val database: DatabaseQueries) : PhotoDataSource {

   private val queries = database.getPhotoTableQueries()

    override fun getPhotosByAlbumId(albumId: Int): Flow<List<Photo>> {
        return queries.getAllPhotos().asFlow().mapToList()
            .map { list -> mapUsersToPhotoList(list).filter { it.albumId == albumId } }
    }

    override fun deletePhotoById(photoId: Int) {
        queries.deletePhoto(photoId.toLong())
    }

    override fun insertPhoto(photo: Photo) {
        queries.insertPhoto(photo.id.toLong(), photo.title, photo.albumId.toLong(), photo.url, photo.thumbnailUrl)
    }


    private fun mapUsersToPhotoList(list: List<PhotoEntity>): List<Photo> {
        return list.map {
            Photo(
                id = it.id.toInt(), title = it.title,
                albumId = it.albumId.toInt(),
                url = it.url,
                thumbnailUrl = it.thumbnailUrl
            )
        }
    }
}