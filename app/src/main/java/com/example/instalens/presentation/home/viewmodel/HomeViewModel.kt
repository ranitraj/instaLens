package com.example.instalens.presentation.home.viewmodel

import android.content.Context
import android.util.Log
import androidx.camera.core.CameraSelector
import androidx.camera.view.CameraController
import androidx.camera.view.LifecycleCameraController
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(

): ViewModel() {
    companion object {
        private val TAG: String? = HomeViewModel::class.simpleName
    }

    /**
     * Initializes and returns a `LifecycleCameraController` instance with the specified use cases.
     * This function sets up the camera controller for image analysis and image capture.
     *
     * @param context context used for managing the lifecycle of the camera controller.
     * @return Returns a fully initialized `LifecycleCameraController` instance.
     */
    fun prepareCameraController(context: Context): LifecycleCameraController {
        Log.d(TAG, "prepareCameraController() called")
        return LifecycleCameraController(context).apply {
            setEnabledUseCases(
                CameraController.IMAGE_ANALYSIS or
                        CameraController.IMAGE_CAPTURE
            )
            // TODO: Initialize Image Analyzer
        }
    }

    /**
     * Retrieves the selected camera (front or back) based on the current camera selection of the provided camera controller.
     *
     * @param cameraController The controller managing the camera operations.
     * @return Returns [CameraSelector.DEFAULT_FRONT_CAMERA] if the current camera is the back camera,
     *         and [CameraSelector.DEFAULT_BACK_CAMERA] if it's the front camera.
     */
    fun getSelectedCamera(cameraController: LifecycleCameraController): CameraSelector {
        Log.d(TAG, "getSelectedCamera() called")
        return if (cameraController.cameraSelector == CameraSelector.DEFAULT_BACK_CAMERA) {
            CameraSelector.DEFAULT_FRONT_CAMERA
        } else {
            CameraSelector.DEFAULT_BACK_CAMERA
        }
    }

}