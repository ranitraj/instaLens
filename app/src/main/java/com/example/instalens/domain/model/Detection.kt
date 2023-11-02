package com.example.instalens.domain.model

import android.graphics.RectF

/**
 * Represents a detected object in an image or frame.
 *
 * @param boundingBox The rectangle boundary of the detected object in the image.
 * @param detectedObjectName The name or label of the detected object.
 * @param confidenceScore The confidence score or probability (ranging between 0 and 1) indicating the accuracy of the detection.
 */
data class Detection(
    val boundingBox: RectF,
    val detectedObjectName: String,
    val confidenceScore: Float,
    val tensorImageHeight: Int,
    val tensorImageWidth: Int,
)