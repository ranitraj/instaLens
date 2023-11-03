package com.example.instalens.utils

import android.graphics.Bitmap
import android.graphics.Matrix
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import androidx.compose.runtime.State
import com.example.instalens.domain.manager.objectDetection.ObjectDetectionManager
import com.example.instalens.domain.model.Detection
import com.example.instalens.domain.usecases.detection.DetectObjectUseCase
import javax.inject.Inject

/**
 * This analyzer processes camera frames to detect objects, using a custom implementation that
 * interfaces with an object detection model provided by [ObjectDetectionManager].
 *
 * Frames are selectively analyzed at a specified rate (currently once per second assuming a
 * standard camera frame rate of 60fps), controlled by a frame skip counter to balance performance
 * and responsiveness. The rotation of each frame is corrected to ensure the detection algorithm
 * receives images in the correct orientation.
 *
 * Detection results, which include the objects found and their confidence scores, are passed back
 * through the [onObjectDetectionResults] callback, along with any other relevant metadata.
 *
 * @property onObjectDetectionResults A callback function that processes the list of [Detection]
 * objects returned by the object detection algorithm for each analyzed frame.
 * @property confidenceScoreState A state holding the threshold for the confidence score,
 * used to filter results by the object detection manager.
 *
 * @constructor Creates an instance of the analyzer with injected dependencies necessary
 * for object detection operations.
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
            // Rotating the image by transforming it via Matrix using rotationDegrees
            val rotatedImageMatrix: Matrix =
                Matrix().apply {
                    postRotate(image.imageInfo.rotationDegrees.toFloat())
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

            // Obtaining results via objectDetectionManager in Domain Layer
            val objectDetectionResults = objectDetectionManager.detectObjectsInCurrentFrame(
                bitmap = rotatedBitmap,
                image.imageInfo.rotationDegrees,
                confidenceThreshold = confidenceScoreState.value
            )
            onObjectDetectionResults(objectDetectionResults)
        }
        frameSkipCounter++

        // Fully processed the frame
        image.close()
    }
}