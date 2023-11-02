package com.example.instalens.domain.manager.objectDetection

import android.graphics.Bitmap
import org.tensorflow.lite.task.vision.detector.Detection

/**
 * Interface responsible for managing object detection operations.
 */
interface ObjectDetectionManager {
    fun detectObjectsInCurrentFrame(
        bitmap: Bitmap,
        rotation: Int
    ): List<Detection>
}