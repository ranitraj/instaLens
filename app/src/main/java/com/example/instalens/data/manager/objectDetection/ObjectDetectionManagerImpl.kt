package com.example.instalens.data.manager.objectDetection

import android.graphics.Bitmap
import com.example.instalens.domain.manager.objectDetection.ObjectDetectionManager
import org.tensorflow.lite.task.vision.detector.Detection
import javax.inject.Inject

class ObjectDetectionManagerImpl @Inject constructor(

): ObjectDetectionManager {

    /**
     * Detects objects within the provided bitmap image.
     *
     * @param bitmap The input image in which objects are to be detected.
     * @param rotation The rotation value of the image to adjust its orientation.
     * @return List of detected objects represented by the [Detection] class.
     */
    override fun detectObjectsInCurrentFrame(
        bitmap: Bitmap,
        rotation: Int
    ): List<Detection> {
        TODO("Not yet implemented")
    }
}