package com.example.instalens.domain.manager.objectDetection

import android.graphics.Bitmap
import com.example.instalens.domain.model.Detection

/**
 * Interface responsible for managing object detection operations.
 */
interface ObjectDetectionManager {
    fun detectObjectsInCurrentFrame(
        bitmap: Bitmap,
        rotation: Int,
        confidenceThreshold: Float
    ): List<Detection>
}