package com.example.instalens.presentation.home

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Matrix
import android.util.Log
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.ImageProxy
import androidx.camera.view.CameraController
import androidx.camera.view.LifecycleCameraController
import androidx.core.content.ContextCompat
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

    /**
     * Captures a photo using the provided camera controller and processes the captured image.
     *
     * This function initiates a photo capture and once successful, it rotates the image based on
     * its rotation degrees. Also, if the image is captured using the front camera, it
     * inverts the image along the X-axis. After processing the image, the resulting bitmap is
     * passed to the provided callback function `onPhotoTaken`.
     *
     * @param context The application's context.
     * @param cameraController The lifecycle-aware camera controller to manage the photo capture.
     * @param onPhotoTaken A callback that is invoked with the processed bitmap once the photo is captured and processed.
     */
    fun capturePhoto(
        context: Context,
        cameraController: LifecycleCameraController,
        onPhotoTaken: (Bitmap) -> Unit
    ) {
        cameraController.takePicture(
            ContextCompat.getMainExecutor(context),
            object: ImageCapture.OnImageCapturedCallback() {

                override fun onCaptureSuccess(image: ImageProxy) {
                    super.onCaptureSuccess(image)
                    Log.d(TAG, "onCaptureSuccess() called for capturePhoto")

                    // Rotating the image by transforming it via Matrix using rotationDegrees
                    val rotatedImageMatrix: Matrix =
                        Matrix().apply {
                            postRotate(image.imageInfo.rotationDegrees.toFloat())

                            // Inverting image along X-axis when captured via Front-Camera
                            val selectedCamera = getSelectedCamera(cameraController)
                            if (selectedCamera == CameraSelector.DEFAULT_FRONT_CAMERA) {
                                postScale(-1f, 1f)
                            }
                    }

                    // Creating a new Bitmap via createBitmap using 'rotatedImageMatrix'
                    val rotatedBitmap: Bitmap = Bitmap.createBitmap(
                        image.toBitmap(),
                        0,
                        0,
                        image.width,
                        image.height,
                        rotatedImageMatrix,
                        true
                    )

                    onPhotoTaken(rotatedBitmap)
                }

                override fun onError(exception: ImageCaptureException) {
                    super.onError(exception)
                    Log.e(TAG, "onError() called for capturePhoto with: exception = $exception")
                }
            }
        )
    }

}