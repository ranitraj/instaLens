package com.example.instalens.domain.usecases.detection

import android.graphics.Bitmap
import com.example.instalens.domain.manager.objectDetection.ObjectDetectionManager
import com.example.instalens.domain.model.Detection

class DetectObjectUseCase(
    private val objectDetectionManager: ObjectDetectionManager
) {
    /**
     * UseCase responsible for executing the object detection process.
     *
     * This function delegates the object detection task to the provided
     * [ObjectDetectionManager] and returns the detected objects.
     *
     * @param bitmap The input image in which objects are to be detected.
     * @param rotation The rotation value of the image to adjust its orientation.
     * @param confidenceThreshold The confidence score for filtering out results from model.
     * @return List of detected objects represented by the [Detection] class.
     */
    fun execute(
        bitmap: Bitmap,
        rotation: Int,
        confidenceThreshold: Float
    ): List<Detection> {
        return objectDetectionManager.detectObjectsInCurrentFrame(
            bitmap = bitmap,
            rotation,
            confidenceThreshold
        )
    }
}