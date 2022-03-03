package com.example.firstkmmapp.android.ui.albums

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.firstkmmapp.data.Album
import com.example.firstkmmapp.repository.AlbumRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AlbumsPageViewModel @Inject constructor(
    val albumRepository: AlbumRepository
) : ViewModel() {

    var albums = MutableStateFlow<List<Album>>(emptyList())

    fun getData(id: Int) {
        Log.d("AlbumsPageViewModel", "getData id = $id")
        viewModelScope.launch {
            albumRepository.getAllAlbums(userId = id).collect { albumsList ->
                Log.d("AlbumsPageViewModel", "getData albumsList = $albumsList")
                albumsList?.let {
                    albums.value = it
                }
            }
        }
    }

    fun deleteAlbum(album: Album) {
        viewModelScope.launch(Dispatchers.IO) {
            albumRepository.deleteAlbum(album.id.toLong())
        }
    }
}