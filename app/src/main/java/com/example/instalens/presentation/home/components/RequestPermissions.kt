package com.example.instalens.presentation.home.components

import android.os.Build
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState

/**
 * Composable function that handles the runtime request of necessary permissions.
 * It requests camera permissions and, conditionally, external storage write permissions for SDK versions <= 29 (Q).
 *
 * Permissions are automatically requested when the composable enters the Composition via a `LaunchedEffect`.
 * The use of `ExperimentalPermissionsApi` indicates that the API used for permission handling is subject to change.
 */
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun RequestPermissions() {
    // Camera permission state
    val cameraPermissionState = rememberPermissionState(
        android.Manifest.permission.CAMERA
    )

    // External storage write permission state for SDK <= 29
    val storagePermissionState = if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.Q) {
        rememberPermissionState(
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
    } else null

    // Check the grant status of permissions (if null, granting storage permission by default)
    val isCameraGranted = cameraPermissionState.status.isGranted
    val isStorageGranted = storagePermissionState?.status?.isGranted ?: true

    if (!isCameraGranted || !isStorageGranted) {
        // Automatically launch the permission request when the composable appears on the screen
        LaunchedEffect(cameraPermissionState, storagePermissionState) {
            cameraPermissionState.launchPermissionRequest()
            storagePermissionState?.launchPermissionRequest()
        }
    }
}
