package com.example.instalens.presentation.home.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.instalens.domain.model.Detection
import com.example.instalens.presentation.common.DrawDetectionBox

/**
 * Composable function that creates an overlay for the camera feed to display bounding boxes around detected objects.
 * It places each detection box on top of the camera preview based on the detection data provided.
 *
 * @param detections A list of [Detection] objects that contain the bounding box information and metadata
 *                   for each detected object in the camera feed.
 */
@Composable
fun CameraOverlay(detections: List<Detection>) {
    Box(modifier = Modifier.fillMaxSize()) {
        detections.forEach { detection ->
            DrawDetectionBox(detection)
        }
    }
}