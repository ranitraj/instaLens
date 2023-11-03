package com.example.instalens.presentation.common

import android.graphics.Paint
import android.graphics.RectF
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.core.graphics.alpha
import androidx.core.graphics.blue
import androidx.core.graphics.green
import androidx.core.graphics.red
import com.example.instalens.domain.model.Detection
import kotlin.math.max
import kotlin.math.min
import kotlin.random.Random

/**
 * Composable function to draw a detection box on the canvas.
 * It uses the detection data to draw a rectangle and label text over the detected object on the screen.
 *
 * @param detection The detection data that contains information about the detected object,
 * including its bounding box, label name, and confidence score.
 */
@Composable
fun DrawDetectionBox(detection: Detection) {
    // Retrieve screen dimensions to scale the detection box accordingly
    val screenWidth = LocalContext.current.resources.displayMetrics.widthPixels * 1f
    val screenHeight = LocalContext.current.resources.displayMetrics.heightPixels * 1f

    // Prepare a Paint object with properties for drawing the box and text
    val paint = rememberUpdatedState(Paint().apply {
        style = Paint.Style.STROKE
        strokeWidth = 8f
        color = getColorForLabel(detection.detectedObjectName)      // Assigning a color based on the object label
    })

    val xScale = screenWidth / detection.tensorImageWidth
    val yScale = screenHeight / detection.tensorImageHeight

    // Scale the bounding box from the detection to match the display dimensions
    val scaledBox = RectF(
        detection.boundingBox.left * xScale,
        detection.boundingBox.top * yScale,
        detection.boundingBox.right * xScale,
        detection.boundingBox.bottom * yScale
    ).also {
        // Ensure the bounding box doesn't go outside of the screen dimensions
        it.left = it.left.coerceAtLeast(0f)
        it.top = it.top.coerceAtLeast(0f)
        it.right = it.right.coerceAtMost(screenWidth)
        it.bottom = it.bottom.coerceAtMost(screenHeight)
    }

    // Convert the compose color to Android framework color
    val androidColor = android.graphics.Color.argb(
        (paint.value.color.alpha * 255),
        (paint.value.color.red * 255),
        (paint.value.color.green * 255),
        (paint.value.color.blue * 255)
    )

    // Define the text size in pixels based on density and desired sp size
    val density = LocalDensity.current.density
    val desiredTextSizeInSp = 20
    val pixelSize = desiredTextSizeInSp * density

    // Composable Box to hold the Canvas
    Box(modifier = Modifier.fillMaxSize()) {
        Canvas(
            modifier = Modifier.matchParentSize(),
            onDraw = {
                drawRect(
                    color = Color(paint.value.color),
                    size = Size(scaledBox.width(), scaledBox.height()),
                    topLeft = Offset(scaledBox.left, scaledBox.top),
                    style = Stroke(paint.value.strokeWidth)
                )

                val text =
                    "${detection.detectedObjectName} ${(detection.confidenceScore * 100).toInt()}%"
                drawIntoCanvas { canvas ->
                    canvas.nativeCanvas.drawText(
                        text,
                        scaledBox.left,
                        scaledBox.top - 10,
                        Paint().apply {
                            color = androidColor
                            textSize = pixelSize
                        }
                    )
                }
            }
        )
    }
}

private val labelColorMap = mutableMapOf<String, Int>()

/**
 * Gets a color associated with a particular label. If a color is not already assigned,
 * it generates a random color and associates it with the label for consistent coloring.
 *
 * @param label The label for which a color is required.
 * @return The color associated with the given label.
 */
fun getColorForLabel(label: String): Int {
    return labelColorMap.getOrPut(label) {
        // Generates a random color for the label if it doesn't exist in the map.
        Random.nextInt()
    }
}
