package com.example.instalens.utils

import android.graphics.RectF
import androidx.compose.ui.unit.IntSize

object ImageScalingUtils {

    /**
     * Maps the size of a given source to fit within a target size while maintaining the aspect ratio.
     *
     * @param originalSize The size of the source whose dimensions are to be mapped.
     * @param targetWidth The width of the target size.
     * @param targetHeight The height of the target size.
     * @return The mapped size that maintains the original aspect ratio but fits within the target dimensions.
     */
    fun mapSize(
        originalSize: IntSize,
        targetWidth: Int,
        targetHeight: Int)
    : IntSize {
        val originalAspectRatio = originalSize.width.toFloat() / originalSize.height
        val targetAspectRatio = targetWidth.toFloat() / targetHeight

        return if (originalAspectRatio > targetAspectRatio) {
            IntSize((originalSize.height * targetAspectRatio).toInt(), originalSize.height)
        } else {
            IntSize(originalSize.width, (originalSize.width / targetAspectRatio).toInt())
        }
    }

    /**
     * Scales the given bounding box coordinates from their original dimensions to fit within the dimensions
     * of the camera preview, while preserving the relative positions of the bounding box within the original space.
     *
     * @param boundingBox The original bounding box coordinates in the form of RectF (left, top, right, bottom).
     * @param originalWidth The width of the original space where the bounding box coordinates were defined.
     * @param originalHeight The height of the original space where the bounding box coordinates were defined.
     * @param cameraPreviewWidth The width of the camera preview space where the bounding box should be scaled to.
     * @param cameraPreviewHeight The height of the camera preview space where the bounding box should be scaled to.
     * @return A RectF containing the scaled bounding box coordinates corresponding to the camera preview dimensions.
     */
    fun scaleBoundingBox(
        boundingBox: RectF,
        originalWidth: Int,
        originalHeight: Int,
        cameraPreviewWidth: Int,
        cameraPreviewHeight: Int
    ): RectF {
        val scaledLeft = boundingBox.left * (cameraPreviewWidth.toFloat() / originalWidth)
        val scaledTop = boundingBox.top * (cameraPreviewHeight.toFloat() / originalHeight)
        val scaledRight = boundingBox.right * (cameraPreviewWidth.toFloat() / originalWidth)
        val scaledBottom = boundingBox.bottom * (cameraPreviewHeight.toFloat() / originalHeight)
        return RectF(scaledLeft, scaledTop, scaledRight, scaledBottom)
    }
}