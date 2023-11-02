package com.example.instalens.utils

import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.example.instalens.domain.manager.objectDetection.ObjectDetectionManager
import com.example.instalens.domain.model.Detection
import com.example.instalens.domain.usecases.detection.DetectObjectUseCase
import com.example.instalens.utils.extensions.cropImage
import javax.inject.Inject

/**
 * A custom analyzer for processing camera frames and detecting objects within them.
 *
 * This analyzer utilizes the [DetectObjectUseCase] to detect objects within the camera frames.
 * The frames are processed at a rate of 1 frame per-second to optimize performance. Once objects
 * are detected, the results are then communicated back via the [onObjectDetectionResults] callback.
 *
 * @property detectObjectUseCase UseCase responsible for detecting objects within a given bitmap.
 * @property onObjectDetectionResults Callback to report detected objects from the processed frames.
 *
 * @constructor Injects the dependencies required for this analyzer.
 */
class CameraFrameAnalyzer @Inject constructor(
    private val objectDetectionManager: ObjectDetectionManager,
    private val onObjectDetectionResults: (List<Detection>) -> Unit
): ImageAnalysis.Analyzer {
    private var frameSkipCounter = 0

    override fun analyze(image: ImageProxy) {
        // Analyze only 1 frame every 2-seconds
        if (frameSkipCounter % 120 == 0) {
            val rotationDegrees = image.imageInfo.rotationDegrees
            val bitmap = image.toBitmap()

            // Obtaining results via Use-Case
            // TODO: Replace constant confidence score with dynamic value
            val objectDetectionResults = objectDetectionManager.detectObjectsInCurrentFrame(
                bitmap = bitmap,
                rotationDegrees,
                Constants.INITIAL_CONFIDENCE_SCORE
            )
            onObjectDetectionResults(objectDetectionResults)
        }
        frameSkipCounter++

        // Fully processed the frame
        image.close()
    }
}