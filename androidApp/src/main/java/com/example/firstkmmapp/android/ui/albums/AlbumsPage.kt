package com.example.firstkmmapp.android.ui.albums

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.firstkmmapp.android.NavActions
import com.example.firstkmmapp.android.ui.components.DeleteDialog
import com.example.firstkmmapp.data.Album

@ExperimentalAnimationApi
@Composable
fun AlbumPage(vm: AlbumsPageViewModel = hiltViewModel(), navAction: NavActions, id: Int) {
    Log.d("AlbumPage", "id = $id")
    LaunchedEffect(key1 = id, block = { vm.getData(id) })

    val albums by vm.albums.collectAsState()
    val albumIndex = remember { mutableStateOf(0) }
    val number = remember { mutableStateOf(0) }
    val openDialog = remember { mutableStateOf(false) }
    val search = remember { mutableStateOf("") }

    LazyColumn {
        if (albums.isNullOrEmpty()) {
            item {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(modifier = Modifier.size(100.dp))
                }
            }
        } else {
            albums.filter { it.title.contains(search.value) }.forEachIndexed() { index, album ->
                item {

                    AlbumCard(album = album, onDelete = {
                        openDialog.value = true
                        albumIndex.value = index
                    }) {
                        navAction.showPhotos(album.id.toString())
                    }
                    if (openDialog.value) {
                        DeleteDialog(title = "Delete Album", onConfirmClick = {
                            openDialog.value = false
                            Log.d(
                                "log", "delete album = ${albums[albumIndex.value]}"
                            )
                            vm.deleteAlbum(albums[albumIndex.value])
                        }, onDismissClick = {
                            openDialog.value = false
                        })


                    }
                }
            }
        }
    }
}


@ExperimentalAnimationApi
@Composable
fun AlbumCard(
    album: Album, onDelete: () -> Unit, onClick: () -> Unit
) {

    var extended = remember { mutableStateOf(false) }
    Card(modifier = Modifier
        .padding(10.dp)
        .fillMaxWidth()
        .pointerInput(Unit) {
            detectHorizontalDragGestures { change, dragAmount ->
                if (dragAmount < -50f) {
                    extended.value = true
                }
                if (dragAmount > 50f) {
                    extended.value = false
                }
            }
        }, elevation = 5.dp) {

        Row(modifier = Modifier
            .clickable { onClick() }
            .fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = album.title,
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth(0.91f)
                    .fillMaxHeight(),
                fontSize = 18.sp
            )

            AnimatedVisibility(visible = extended.value, enter = slideInHorizontally(
                initialOffsetX = { offset -> offset },
                animationSpec = tween(durationMillis = 500, easing = LinearOutSlowInEasing)
            ), modifier = Modifier
                .fillMaxSize()
                .fillMaxHeight(), exit = slideOutHorizontally(
                targetOffsetX = { offset -> offset },
                animationSpec = tween(durationMillis = 500, easing = LinearOutSlowInEasing)
            ), content = {
                IconButton(
                    onClick = {
                        onDelete()
                        extended.value = false
                    },
                    modifier = Modifier
                        .background(MaterialTheme.colors.primary)
                        .fillMaxHeight()
                ) {
                    Icon(
                        imageVector = Icons.Filled.Delete,
                        contentDescription = "",
                        tint = MaterialTheme.colors.background
                    )
                }
            })

        }
    }
}