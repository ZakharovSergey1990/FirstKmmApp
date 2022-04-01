package com.example.firstkmmapp.android.ui.photos

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
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
import kotlinx.coroutines.flow.onEach


@SuppressLint("FlowOperatorInvokedInComposition")
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PhotoPage(vm: PhotosPageViewModel = hiltViewModel(), navAction: NavActions, id: Int) {
    Log.d("log", "id = $id")
    LaunchedEffect(key1 = id, block = { vm.getData(id) })
    val search = remember { mutableStateOf("") }
    val photos by vm.photos.onEach { Log.d("PhotoPage", "size = ${it.size}") }.collectAsState(initial = emptyList())

        if (photos.isNullOrEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .fillMaxWidth()
                        .fillMaxHeight(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(modifier = Modifier.size(100.dp))
                }
            }
         else {
            PhotosList(photos = photos, searchValue = search.value, delete = {vm.deletePhoto(it)})
            }
        }



@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PhotosList(photos: List<Photo>, searchValue: String, delete: (Photo) -> Unit) {
    LazyColumn() {
        items(photos.filter { it.title.contains(searchValue) }, key = { it.id }) { photo ->
            Surface(modifier = Modifier.animateItemPlacement()) {
                PhotoCard(photo = photo) {
                    delete(photo)
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PhotoCard(photo: Photo,  delete: () -> Unit) {

    val dismissState = DismissState(initialValue = DismissValue.Default, confirmStateChange = {
        if (it == DismissValue.DismissedToEnd || it == DismissValue.DismissedToStart) {
            Log.d("PhotoPage", "deletePhoto")
            delete()
        }
        true
    })

    SwipeToDismiss(state = dismissState, background = { }) {
        Card(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth()
                .clickable { }, elevation = 5.dp
        ) {
            Column(
                modifier = Modifier
            ) {
                photo.url?.let { it1 -> LoadPicture(url = it1) }
                Text(
                    text = photo.title.toString(),
                    modifier = Modifier.padding(10.dp),
                    fontSize = 18.sp
                )
            }
        }
    }
}