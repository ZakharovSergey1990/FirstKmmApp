package com.example.firstkmmapp.android.ui.components

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import com.squareup.picasso.Picasso

@Composable
fun LoadPicture(url: String){

    val image = remember{ mutableStateOf<Bitmap?>(null) }

    val target = object: com.squareup.picasso.Target{
        override fun onPrepareLoad(placeHolderDrawable: Drawable?) {
        }

        override fun onBitmapFailed(e: Exception?, errorDrawable: Drawable?) {
        }

        override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
            image.value = bitmap
        }
    }

    Picasso.get().load(url).into(target)
    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
        if (image.value == null) {
            CircularProgressIndicator()
        } else {
            image.value?.let {
                Image(
                    bitmap = it.asImageBitmap(),
                    contentDescription = "",
                    modifier = Modifier.fillMaxWidth(),
                    contentScale = ContentScale.FillWidth
                )
            }
        }
    }
}
