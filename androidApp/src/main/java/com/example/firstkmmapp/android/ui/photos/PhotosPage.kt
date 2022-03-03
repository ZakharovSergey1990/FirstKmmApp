package com.example.firstkmmapp.android.ui.photos

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.firstkmmapp.android.NavActions
import com.example.firstkmmapp.android.ui.components.LoadPicture
import com.example.firstkmmapp.android.ui.components.swipeToDismiss
import com.example.firstkmmapp.data.Photo
import kotlinx.coroutines.flow.filter


@Composable
fun PhotoPage (vm: PhotosPageViewModel = hiltViewModel(), navAction: NavActions, id: Int){
    Log.d("log", "id = $id")
    LaunchedEffect(key1 = id, block = { vm.getData(id) })

    val search = remember{ mutableStateOf("") }

    val photos by vm.photos.collectAsState()

        LazyColumn() {
            if (photos.isNullOrEmpty()) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .fillMaxWidth()
                            .fillMaxHeight(), contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(modifier = Modifier.size(100.dp))
                    }
                }
            } else {
                items( photos.filter { it.title.contains(search.value) }) {
                    PhotoCard(photo = it){
                        vm.deletePhoto(it)
                    }
                }
            }
        }
    }



@Composable
fun PhotoCard(photo: Photo, delete: () -> Unit){
    Card(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth()
            .clickable { },
        elevation = 5.dp
    ) {
        Column(modifier = Modifier. swipeToDismiss {
            Log.d("log", "delete Photo")
            delete() }) {
            photo.url?.let { it1 -> LoadPicture(url = it1) }
            Text(
                text = photo.title.toString(),
                modifier = Modifier.padding(10.dp),
                fontSize = 18.sp
            )
        }
    }
}