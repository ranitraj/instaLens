package com.example.instalens.utils

import android.graphics.RectF

/**
 * Utility object for scaling image dimensions and bounding box coordinates. It provides functions
 * to calculate scale factors for resizing images to fit specific dimensions while maintaining
 * aspect ratio, and to adjust bounding box coordinates from a source image size to match a target
 * preview size, useful in applications involving image processing and object detection where the
 * output coordinates need to be mapped onto a different display resolution.
 */
object ImageScalingUtils {

    /**
     * Maps the size of a given source to fit within a target size while maintaining the aspect ratio.
     *
     * @param targetWidth The width of the target size.
     * @param targetHeight The height of the target size.
     * @return The mapped size that maintains the original aspect ratio but fits within the target dimensions.
     */
    fun getScaleFactors(
        targetWidth: Int,
        targetHeight: Int
    ): FloatArray {
        return try {
            val scaleX = (targetWidth.toFloat() / Constants.ORIGINAL_IMAGE_WIDTH)
            val scaleY = (targetHeight.toFloat() / Constants.ORIGINAL_IMAGE_HEIGHT)
            floatArrayOf(scaleX, scaleY)
        } catch(exception: Exception) {
            floatArrayOf(1f, 1f)
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