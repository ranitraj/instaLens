package com.example.instalens.presentation.home

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Matrix
import android.graphics.RectF
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.ImageProxy
import androidx.camera.view.CameraController
import androidx.camera.view.LifecycleCameraController
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.instalens.domain.model.Detection
import com.example.instalens.domain.usecases.detection.DetectObjectUseCase
import com.example.instalens.utils.CameraFrameAnalyzer
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import android.graphics.Paint
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val detectObjectUseCase: DetectObjectUseCase
): ViewModel() {
    companion object {
        private val TAG: String? = HomeViewModel::class.simpleName
    }

    private val _isImageSavedStateFlow = MutableStateFlow(true)
    val isImageSavedStateFlow = _isImageSavedStateFlow.asStateFlow()

    /**
     * Initializes and returns a `LifecycleCameraController` instance with the specified use cases.
     * This function sets up the camera controller for image analysis and image capture.
     *
     * @param context context used for managing the lifecycle of the camera controller.
     * @return Returns a fully initialized `LifecycleCameraController` instance.
     */
    fun prepareCameraController(
        context: Context,
        cameraFrameAnalyzer: CameraFrameAnalyzer
    ): LifecycleCameraController {
        Log.d(TAG, "prepareCameraController() called")
        return LifecycleCameraController(context).apply {
            setEnabledUseCases(
                CameraController.IMAGE_ANALYSIS or
                        CameraController.IMAGE_CAPTURE
            )
            setImageAnalysisAnalyzer(
                ContextCompat.getMainExecutor(context),
                cameraFrameAnalyzer
            )
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
     * inverts the image along the X-axis. After processing the image, the resulting bitmap is saved
     * by calling the 'saveBitmapToDevice' private method,
     *
     * @param context The application's context.
     * @param cameraController The lifecycle-aware camera controller to manage the photo capture.
     */
    fun capturePhoto(
        context: Context,
        cameraController: LifecycleCameraController,
        detections: List<Detection>
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
//                            val selectedCamera = getSelectedCamera(cameraController)
//                            if (selectedCamera == CameraSelector.DEFAULT_FRONT_CAMERA) {
//                                postScale(-1f, 1f)
//                            }
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

                    val combinedBitmap = overlayDetectionsOnBitmap(rotatedBitmap, detections)

                    // Save the Image-Bitmap to Device
                    saveBitmapToDevice(
                        context = context,
                        capturedImageBitmap = combinedBitmap
                    )
                }

                override fun onError(exception: ImageCaptureException) {
                    super.onError(exception)
                    Log.e(TAG, "onError() called for capturePhoto with: exception = $exception")
                    isPhotoSuccessfullySaved(false)
                }
            }
        )
    }


    /**
     * Saves the provided bitmap to the device's external storage.
     *
     * This function saves the bitmap in the device's picture directory and gives it a unique name based on the current system time.
     * For devices running Android API 29 and above, the bitmap is saved using the MediaStore API which ensures that the saved image
     * is immediately visible in gallery apps without the need for any additional scanning.
     *
     * @param context The application context used for content resolution.
     * @param capturedImageBitmap The bitmap image to be saved.
     */
    private fun saveBitmapToDevice(
        context: Context,
        capturedImageBitmap: Bitmap
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                Log.d(TAG, "saveBitmapToDevice() called for Version = ${Build.VERSION.SDK_INT}")
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    val values = ContentValues().apply {
                        put(MediaStore.Images.Media.DISPLAY_NAME, generateImageName())
                        put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
                        put(MediaStore.Images.Media.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
                    }

                    val uri: Uri? = context.contentResolver.insert(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values
                    )

                    uri?.let {
                        context.contentResolver.openOutputStream(it).use { outputStream ->
                            if (outputStream != null) {
                                capturedImageBitmap.compress(
                                    Bitmap.CompressFormat.JPEG,
                                    100,
                                    outputStream
                                )
                            }
                            outputStream?.flush()
                            outputStream?.close()
                        }
                    }

                    // Update _isImageSavedStateFlow value to true
                    isPhotoSuccessfullySaved(true)
                }
            } catch (exception: Exception) {
                Log.e(TAG, "saveBitmapToDevice() called with: exception = $exception")
                isPhotoSuccessfullySaved(false)
            }
        }
    }


    /**
     * Updates the state flow with the status of whether the photo has been successfully saved or not.
     *
     * @param isSaved Boolean flag indicating if the photo was successfully saved.
     */
    private fun isPhotoSuccessfullySaved(isSaved: Boolean) {
        Log.d(TAG, "isPhotoSuccessfullySaved() called with: isSaved Flag = $isSaved")
        _isImageSavedStateFlow.value = isSaved
    }


    /**
     * Returns the current system time in a formatted string, prepended with "IMG_".
     * The returned string is in the format "IMG_YYYYMMDD_HHMMSS", which represents the current system time.
     *
     * @return A formatted string representing the current system time, prepended with "IMG_".
     */
    private fun generateImageName(): String {
        Log.d(TAG, "generateImageName() called")
        val currentDateTime = Calendar.getInstance().time
        val dateFormat = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault())
        val formattedDate = dateFormat.format(currentDateTime)
        return "IMG_$formattedDate"
    }


    fun overlayDetectionsOnBitmap(bitmap: Bitmap, detections: List<Detection>): Bitmap {
        val overlayBitmap = Bitmap.createBitmap(bitmap.width, bitmap.height, bitmap.config)
        val canvas = Canvas(overlayBitmap)

        // Draw the captured image onto the new bitmap
        canvas.drawBitmap(bitmap, 0f, 0f, null)

        // Draw detections on the canvas (i.e., on top of the captured image)
        detections.forEach { detection ->
            val rect = RectF(
                detection.boundingBox.left,
                detection.boundingBox.top,
                detection.boundingBox.right,
                detection.boundingBox.bottom
            )
            val paint = Paint().apply {
                color = Color.RED
                style = Paint.Style.STROKE
                strokeWidth = 4f
            }
            canvas.drawRect(rect, paint)

        }

        return overlayBitmap
    }
}