package com.example.instalens.utils

import android.util.Log
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import androidx.compose.runtime.State
import com.example.instalens.domain.manager.objectDetection.ObjectDetectionManager
import com.example.instalens.domain.model.Detection
import com.example.instalens.domain.usecases.detection.DetectObjectUseCase
import javax.inject.Inject

/**
 * A custom analyzer for processing camera frames and detecting objects within them.
 *
 * This analyzer utilizes the [DetectObjectUseCase] to detect objects within the camera frames.
 * The frames are processed at a rate of 1 frame per-second to optimize performance. Once objects
 * are detected, the results are then communicated back via the [onObjectDetectionResults] callback.
 *
 * @property onObjectDetectionResults Callback to report detected objects from the processed frames.
 *
 * @constructor Injects the dependencies required for this analyzer.
 */
class CameraFrameAnalyzer @Inject constructor(
    private val objectDetectionManager: ObjectDetectionManager,
    private val onObjectDetectionResults: (List<Detection>) -> Unit,
    private val confidenceScoreState: State<Float>
): ImageAnalysis.Analyzer {
    private var frameSkipCounter = 0

    override fun analyze(image: ImageProxy) {
        // Analyze only 1 frame every second
        if (frameSkipCounter % 60 == 0) {
            val rotationDegrees = image.imageInfo.rotationDegrees
            val bitmap = image.toBitmap()

            // Obtaining results via objectDetectionManager in Domain Layer
            val objectDetectionResults = objectDetectionManager.detectObjectsInCurrentFrame(
                bitmap = bitmap,
                rotationDegrees,
                confidenceThreshold = confidenceScoreState.value
            )
            onObjectDetectionResults(objectDetectionResults)
        }
        frameSkipCounter++

        // Fully processed the frame
        image.close()
    }
}