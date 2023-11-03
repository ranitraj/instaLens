package com.example.instalens.domain.model

import android.graphics.RectF

/**
 * Represents a detected object within an image or frame.
 *
 * @property boundingBox The rectangle boundary that encompasses the detected object within the image.
 * @property detectedObjectName The name or label assigned to the detected object.
 * @property confidenceScore A value between 0 and 1 representing the model's confidence in the detection's accuracy.
 * @property tensorImageHeight The height of the image (in pixels) that was processed by the tensor model to detect objects.
 * @property tensorImageWidth The width of the image (in pixels) that was processed by the tensor model to detect objects.
 */
data class Detection(
    val boundingBox: RectF,
    val detectedObjectName: String,
    val confidenceScore: Float,
    val tensorImageHeight: Int,
    val tensorImageWidth: Int,
)