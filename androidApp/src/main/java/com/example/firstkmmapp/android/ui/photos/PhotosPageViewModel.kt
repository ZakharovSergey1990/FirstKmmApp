package com.example.firstkmmapp.android.ui.photos

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.firstkmmapp.data.Album
import com.example.firstkmmapp.data.Photo
import com.example.firstkmmapp.repository.PhotoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class PhotosPageViewModel @Inject constructor(val photoRepository: PhotoRepository): ViewModel(){

    var photos = MutableStateFlow<List<Photo>>(emptyList())

    fun getData(id: Int){
        viewModelScope.launch {
            photoRepository.getAllPhotos(id).collect{ photoList ->
                photoList?.let{
                    photos.value = it
                }
            }
        }
    }

    fun deletePhoto(photo: Photo){
        viewModelScope.launch(Dispatchers.IO) {
            photoRepository.deletePhoto(photo.id)
        }
    }
}
