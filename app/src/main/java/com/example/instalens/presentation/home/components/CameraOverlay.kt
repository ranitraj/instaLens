package com.example.instalens.presentation.home.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.instalens.domain.model.Detection
import com.example.instalens.presentation.common.DrawDetectionBox

@Composable
fun CameraOverlay(detections: List<Detection>) {
    Box(modifier = Modifier.fillMaxSize()) {
        // Your CameraX Preview here

        detections.forEach { detection ->
            DrawDetectionBox(detection)
        }
    }
}