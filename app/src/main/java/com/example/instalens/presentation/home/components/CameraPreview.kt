package com.example.instalens.presentation.home.components

import android.view.ViewGroup
import androidx.camera.view.LifecycleCameraController
import androidx.camera.view.PreviewView
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.LifecycleOwner
import kotlin.math.roundToInt

/**
 * CameraPreview is a Composable that provides a preview of the camera feed using CameraX's `PreviewView`.
 * However, `PreviewView` is not directly supported in Jetpack Compose, so, we inflate it using the `AndroidView` composable.
 * This function requires a `LifecycleCameraController` to manage the camera lifecycle within the composable context.
 *
 * @param controller The camera controller to bind the camera lifecycle.
 * @param modifier Modifiers to apply to the underlying `AndroidView`.
 */
@Composable
fun CameraPreview(
    controller: LifecycleCameraController,
    modifier: Modifier = Modifier,
    onPreviewSizeChanged: (IntSize) -> Unit
) {
    val lifeCycleOwner: LifecycleOwner = LocalLifecycleOwner.current
    val previewSizeState = remember { mutableStateOf(IntSize(0, 0)) }

    AndroidView(
        factory = {
            PreviewView(it).apply {
                this.controller = controller
                controller.bindToLifecycle(lifeCycleOwner)
            }
        },
        modifier = modifier.onGloballyPositioned { coordinates ->
            val size = coordinates.size
            previewSizeState.value = size
            onPreviewSizeChanged(size)
        }
    )
}