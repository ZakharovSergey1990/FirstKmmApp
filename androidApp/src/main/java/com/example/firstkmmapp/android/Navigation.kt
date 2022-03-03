package com.example.firstkmmapp.android

import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.Navigator

object Page {
    const val Users = "/users"
    const val Albums = "/albums/{id}"
    const val Photos = "/photos/{id}"}


class NavActions(val navController: NavController) {

    val goBack: () -> Unit = {
        navController.popBackStack()
    }
    val showUsers: () -> Unit = {
        navController.navigate(Page.Users)
    }
    val showAlbums: (id: String) -> Unit = {
        navController.navigate(route = Page.Albums.replace("{id}", it))
    }
    val showPhotos: (id: String) -> Unit = {
        navController.navigate(Page.Photos.replace("{id}", it))
    }
}