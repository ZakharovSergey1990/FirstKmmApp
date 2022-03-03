package com.example.firstkmmapp.repository

import com.example.firstkmmapp.data.Photo
import com.example.firstkmmapp.data.User
import com.example.firstkmmapp.datasource.localdatasource.AlbumDataSource
import com.example.firstkmmapp.datasource.localdatasource.PhotoDataSource
import kotlinx.coroutines.flow.Flow

interface PhotoRepository {
    @Throws(Exception::class)
    suspend fun getAllPhotos(photoId: Int): Flow<List<Photo>?>
    suspend fun insertPhoto(photo: Photo)
    suspend fun deletePhoto(id: Int)
}

class PhotoRepositoryImpl(
    val photoDataSource: PhotoDataSource
): PhotoRepository{
    override suspend fun getAllPhotos(photoId: Int): Flow<List<Photo>?> {
        return photoDataSource.getPhotosByAlbumId(photoId)
    }

    override suspend fun insertPhoto(photo: Photo) {
      photoDataSource.insertPhoto(photo)
    }

    override suspend fun deletePhoto(id: Int) {
        photoDataSource.deletePhotoById(id)
    }
}