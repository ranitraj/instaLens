package com.example.instalens.presentation.home.components

import androidx.camera.view.LifecycleCameraController
import androidx.camera.view.PreviewView
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.LifecycleOwner

/**
 * CameraPreview is a Composable that provides a preview of the camera feed, integrating CameraX's `PreviewView`.
 * Since `PreviewView` is not natively supported by Jetpack Compose, it is inflated within a composable using `AndroidView`.
 * A `LifecycleCameraController` is utilized to bind and manage the camera lifecycle seamlessly within the Composable's lifecycle.
 *
 * @param controller The `LifecycleCameraController` responsible for camera operations and lifecycle management.
 * @param modifier Optional [Modifier] for styling the underlying `AndroidView` that inflates the `PreviewView`.
 * @param onPreviewSizeChanged A callback that is triggered when the size of the preview changes, providing the new size as [IntSize].
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
            // Retrieve and update the size of the preview when the layout is positioned globally.
            val size = coordinates.size
            previewSizeState.value = size
            // Invoke the callback with the new size.
            onPreviewSizeChanged(size)
        }
    )
}