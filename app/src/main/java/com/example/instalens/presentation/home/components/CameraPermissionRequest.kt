package com.example.instalens.presentation.home.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun CameraPermissionRequest() {

    // Camera permission state
    val cameraPermissionState = rememberPermissionState(
        android.Manifest.permission.CAMERA
    )

    if (!cameraPermissionState.status.isGranted) {
        // Automatically launch the permission request when the composable appears on the screen
        LaunchedEffect(cameraPermissionState) {
            cameraPermissionState.launchPermissionRequest()
        }
    }
}
