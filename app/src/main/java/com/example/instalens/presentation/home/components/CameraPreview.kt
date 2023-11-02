package com.example.instalens.presentation.home.components

import android.view.ViewGroup
import androidx.camera.core.AspectRatio
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.view.LifecycleCameraController
import androidx.camera.view.PreviewView
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.LifecycleOwner

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
    modifier: Modifier = Modifier
) {
    val lifeCycleOwner: LifecycleOwner = LocalLifecycleOwner.current

    AndroidView(
        factory = {
            PreviewView(it).apply {
                // Mapping Aspect-Ratio as 4:3
                this.layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    (ViewGroup.LayoutParams.MATCH_PARENT * (4f/3f)).toInt()
                )
                this.controller = controller
                controller.bindToLifecycle(lifeCycleOwner)
            }
        },
        modifier = modifier
    )
}