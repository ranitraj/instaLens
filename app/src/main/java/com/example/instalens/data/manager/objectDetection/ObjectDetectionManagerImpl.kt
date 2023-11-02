package com.example.instalens.data.manager.objectDetection

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import android.view.Surface
import com.example.instalens.domain.manager.objectDetection.ObjectDetectionManager
import com.example.instalens.domain.model.Detection
import com.example.instalens.utils.Constants
import org.tensorflow.lite.support.image.ImageProcessor
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.task.core.BaseOptions
import org.tensorflow.lite.task.core.vision.ImageProcessingOptions
import org.tensorflow.lite.task.vision.detector.ObjectDetector
import javax.inject.Inject

class ObjectDetectionManagerImpl @Inject constructor(
    private val context: Context
): ObjectDetectionManager {
    private var detector: ObjectDetector? = null

    /**
     * Detects objects within the provided bitmap image.
     *
     * @param bitmap The input image in which objects are to be detected.
     * @param rotation The rotation value of the image to adjust its orientation.
     * @param confidenceThreshold The confidence score for filtering out results from model.
     * @return List of detected objects represented by the [Detection] class.
     */
    override fun detectObjectsInCurrentFrame(
        bitmap: Bitmap,
        rotation: Int,
        confidenceThreshold: Float
    ): List<Detection> {
        if (detector == null) {
            initializeDetector(confidenceThreshold)
        }

        val tfLiteImageProcessingOptions = initializeImageProcessingOptions(rotation)
        val tfLiteImageProcessor: ImageProcessor = ImageProcessor.Builder().build()
        val tensorImage: TensorImage = tfLiteImageProcessor.process(
            TensorImage.fromBitmap(bitmap)
        )

        val detectionResults = detector?.detect(
            tensorImage,
            tfLiteImageProcessingOptions
        )

        // Map detected objects to 'Detection' and filter by confidence threshold
        return detectionResults?.mapNotNull { detectedObject ->
            Log.d(
                "RRG",
                "detectObjectsInCurrentFrame() called with: detectedObject = $detectedObject"
            )
            if ((detectedObject.categories.firstOrNull()?.score ?: 0f) >= confidenceThreshold) {
                Detection(
                    boundingBox = detectedObject.boundingBox,
                    detectedObjectName = detectedObject.categories.firstOrNull()?.label ?: "",
                    confidenceScore = detectedObject.categories.firstOrNull()?.score ?: 0f
                )
            } else null
        }?.take(Constants.MODEL_MAX_RESULTS_COUNT) ?: emptyList()
    }

    /**
     * Initializes the TensorFlow Lite Object Detector with the given confidence threshold.
     *
     * @param confidenceThreshold The minimum confidence score required for a detected object to be considered.
     */
    private fun initializeDetector(confidenceThreshold: Float) {
        val baseOptions = BaseOptions.builder()
            .setNumThreads(2)
            .build()

        val options = ObjectDetector.ObjectDetectorOptions.builder()
            .setBaseOptions(baseOptions)
            .setMaxResults(Constants.MODEL_MAX_RESULTS_COUNT)
            .setScoreThreshold(confidenceThreshold)
            .build()

        try {
            detector = ObjectDetector.createFromFileAndOptions(
                context,
                Constants.MODEL_PATH,
                options
            )
        } catch (exception: IllegalStateException) {
            exception.printStackTrace()
        }
    }

    /**
     * Builds and returns the TfLite ImageProcessingOptions
     *
     * @param rotation
     * @return ImageProcessingOptions
     */
    private fun initializeImageProcessingOptions(rotation: Int): ImageProcessingOptions {
        return ImageProcessingOptions.builder()
            .setOrientation(getOrientationFromRotation(rotation))
            .build()
    }

    /**
     * Returns the orientation of image from the rotation using the
     * TfLite ImageProcessingOptions.Orientation used to internally make use of image axes as an Image metadata
     *
     * @param rotation
     * @return ImageProcessingOptions.Orientation
     */
    private fun getOrientationFromRotation(rotation: Int): ImageProcessingOptions.Orientation {
        return when(rotation) {
            Surface.ROTATION_270 -> ImageProcessingOptions.Orientation.BOTTOM_RIGHT
            Surface.ROTATION_90 -> ImageProcessingOptions.Orientation.TOP_LEFT
            Surface.ROTATION_180 -> ImageProcessingOptions.Orientation.RIGHT_BOTTOM
            else -> ImageProcessingOptions.Orientation.RIGHT_TOP
        }
    }
}