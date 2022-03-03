package com.example.firstkmmapp.android.ui.components

import android.util.Log
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.calculateTargetValue
import androidx.compose.animation.splineBasedDecay
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.horizontalDrag
import androidx.compose.foundation.layout.offset
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.positionChange
import androidx.compose.ui.input.pointer.util.VelocityTracker
import androidx.compose.ui.unit.IntOffset
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlin.math.absoluteValue
import kotlin.math.roundToInt


@Composable
fun DeleteDialog(
    title: String,
    onConfirmClick: () -> Unit,
    onDismissClick: () -> Unit

){

    AlertDialog(
        onDismissRequest = {
            onDismissClick()
        },
        title = {
            Text(text = title)
        },
        text = {
            Text("Are you sure?")
        },
        confirmButton = {
            Button(

                onClick = {
                    onConfirmClick()
                }) {
                Text("Yes")
            }
        },
        dismissButton = {
            Button(

                onClick = {
                    onDismissClick()
                }) {
                Text("No")
            }
        }
    )
}

@Composable
fun Modifier.swipeToDismiss(
    onDismissed: () -> Unit
): Modifier = composed {
    // Этот " Анимируемый` сохраняет горизонтальное смещение для элемента.
    val offsetX = remember { Animatable(0f) }
    pointerInput(Unit) {
        // Used to calculate a settling position of a fling animation.
        // Используется для расчета положения установки анимации броска.
        val decay = splineBasedDecay<Float>(this)
        // Wrap in a coroutine scope to use suspend functions for touch events and animation.
        coroutineScope {
            while (true) {
                // Wait for a touch down event.
                // Дождитесь события приземления.
                val pointerId = awaitPointerEventScope { awaitFirstDown().id }
                // Interrupt any ongoing animation.
                offsetX.stop()
                // Прервать любую текущую анимацию.
                val velocityTracker = VelocityTracker()
                // Wait for drag events.
                // Дождитесь событий перетаскивания.
                awaitPointerEventScope {
                    //  horiz
                    horizontalDrag(pointerId) { change ->
                        launch {
                            // Overwrite the `Animatable` value while the element is dragged.
                            // Перезапишите значение "Animatable" во время перетаскивания элемента.
                            offsetX.snapTo(offsetX.value + change.positionChange().x)

                        }
                        // Record the velocity of the drag.
                        velocityTracker.addPosition(change.uptimeMillis, change.position)
                    }
                }
                // Dragging finished. Calculate the velocity of the fling.
                val velocity = velocityTracker.calculateVelocity().x
                // Calculate where the element eventually settles after the fling animation.
                val targetOffsetX = decay.calculateTargetValue(offsetX.value, velocity)
                // The animation should end as soon as it reaches these bounds.
                offsetX.updateBounds(
                    lowerBound = (-size.width).toFloat(),
                    upperBound = size.width.toFloat()
                )
                launch {
                    Log.d("log", "targetOffsetX.absoluteValue = ${targetOffsetX.absoluteValue}")
                    Log.d("log", "size.width/2 = ${size.width/2}")
                    if (targetOffsetX.absoluteValue <= size.width/2) {
                        // Not enough velocity; Slide back to the default position.
                        offsetX.animateTo(targetValue = 0f, initialVelocity = velocity)
                    } else {
                        // Enough velocity to slide away the element to the edge.
                        //  offsetX.animateDecay(velocity, decay)
                        // The element was swiped away.
                        onDismissed()
                        offsetX.animateTo(targetValue = size.width.toFloat(), initialVelocity = velocity)
                        offsetX.snapTo(0f)
                    }
                }
            }
        }
    }
        // Apply the horizontal offset to the element.
        .offset { IntOffset(offsetX.value.roundToInt(), 0) }
}
