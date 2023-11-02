package com.example.instalens.utils.extensions

import android.graphics.Bitmap
import java.lang.IllegalArgumentException

/**
 * The EfficientDet Lite1 V1 model expects image in the size of 384 * 384 px.
 * Thus, this extension method center crops the bitmap into the desired size and returns it.
 *
 * @param outputWidth - desired width of image
 * @param outputHeight - desired height of image
 *
 * @return cropper bitmap of desired size
 */
fun Bitmap.cropImage(
    outputWidth: Int,
    outputHeight: Int
): Bitmap {
    val xStart = (width - outputWidth) / 2
    val yStart = (height - outputHeight) / 2

    if (xStart < 0 || yStart < 0 || outputWidth > width || outputHeight > height) {
        throw IllegalArgumentException("Invalid arguments for Center Cropping!")
    }
    return Bitmap.createBitmap(
        this,
        xStart,
        yStart,
        outputWidth,
        outputHeight
    )
}