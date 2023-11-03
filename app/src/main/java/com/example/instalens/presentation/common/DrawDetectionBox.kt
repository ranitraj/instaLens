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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import androidx.core.graphics.alpha
import androidx.core.graphics.blue
import androidx.core.graphics.green
import androidx.core.graphics.red
import com.example.instalens.domain.model.Detection
import kotlin.math.max
import kotlin.random.Random

@Composable
fun DrawDetectionBox(detection: Detection) {
    val paint = rememberUpdatedState(Paint().apply {
        style = Paint.Style.STROKE
        strokeWidth = 3f
        color = getColorForLabel(detection.detectedObjectName)
    })

    val scaleFactor = max(
        LocalContext.current.resources.displayMetrics.widthPixels * 1f / detection.tensorImageWidth,
        LocalContext.current.resources.displayMetrics.heightPixels * 1f / detection.tensorImageHeight
    )

    val scaledBox = RectF(
        detection.boundingBox.left * scaleFactor,
        detection.boundingBox.top * scaleFactor,
        detection.boundingBox.right * scaleFactor,
        detection.boundingBox.bottom * scaleFactor
    )

    val androidColor = android.graphics.Color.argb(
        (paint.value.color.alpha * 255),
        (paint.value.color.red * 255),
        (paint.value.color.green * 255),
        (paint.value.color.blue * 255)
    )

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
                val textStyle = TextStyle(color = Color(paint.value.color), fontSize = 28.sp)
                drawIntoCanvas { canvas ->
                    canvas.nativeCanvas.drawText(
                        text,
                        scaledBox.left,
                        scaledBox.top,
                        Paint().apply {
                            color = androidColor
                            textSize = textStyle.fontSize.value
                        }
                    )
                }
            }
        )
    }
}

private val labelColorMap = mutableMapOf<String, Int>()
fun getColorForLabel(label: String): Int {
    return labelColorMap.getOrPut(label) {
        // Generates a random color for the label if it doesn't exist in the map.
        Random.nextInt()
    }
}
