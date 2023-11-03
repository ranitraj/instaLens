package com.example.instalens.presentation.home

import android.graphics.RectF
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.instalens.R
import com.example.instalens.data.manager.objectDetection.ObjectDetectionManagerImpl
import com.example.instalens.domain.model.Detection
import com.example.instalens.presentation.common.ImageButton
import com.example.instalens.presentation.home.components.CameraPreview
import com.example.instalens.presentation.home.components.ObjectCounter
import com.example.instalens.presentation.home.components.RequestPermissions
import com.example.instalens.presentation.home.components.ThresholdLevelSlider
import com.example.instalens.presentation.utils.Dimens
import com.example.instalens.utils.CameraFrameAnalyzer
import com.example.instalens.utils.Constants
import com.example.instalens.utils.ImageScalingUtils


@Composable
fun HomeScreen() {
    val context = LocalContext.current
    val viewModel: HomeViewModel = hiltViewModel()

    // Request Permissions
    RequestPermissions()

    // Collect value emitted by 'isImageSavedStateFlow' when image is captured
    val isImageSavedStateFlow by viewModel.isImageSavedStateFlow.collectAsState()

    // State to hold the preview size
    val previewSizeState = remember { mutableStateOf(IntSize(0, 0)) }

    // State to hold RectF coordinates
    val boundingBoxCoordinatesState = remember { mutableStateListOf<RectF>() }

    // State to hold the confidence score updated by Slider
    val confidenceScoreState = remember { mutableFloatStateOf(Constants.INITIAL_CONFIDENCE_SCORE) }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {

        // A state-backed list to store detected objects
        var detections by remember {
            mutableStateOf(emptyList<Detection>())
        }

        // Calling it to automatically re-invoke Composable(s) when state of 'detections' changes
        LaunchedEffect(detections) {}

        // Preparing Image Analyzer
        val cameraFrameAnalyzer =  remember {
            CameraFrameAnalyzer(
                objectDetectionManager = ObjectDetectionManagerImpl(
                    context = context
                ),
                onObjectDetectionResults = {
                    Log.d("RRG", "onObjectDetectionResults == $detections")
                    detections = it

                    // Clear the previous RectFs and add all new ones
                    boundingBoxCoordinatesState.clear()
                    detections.forEach { detection ->
                        boundingBoxCoordinatesState.add(detection.boundingBox)
                    }
                },
                confidenceScoreState = confidenceScoreState
            )
        }


        // TODO: Remove just for logging
        try {
            boundingBoxCoordinatesState.forEach { rect ->
                val scaledRect = ImageScalingUtils.scaleBoundingBox(
                    rect,
                    detections[0].tensorImageWidth,
                    detections[0].tensorImageHeight,
                    previewSizeState.value.width,
                    previewSizeState.value.height
                )

                Log.d("RRG", "Corresponding RectF coordinates for detection: L = ${scaledRect.left}, T = ${scaledRect.top}, R = ${scaledRect.right}, B = ${scaledRect.bottom}")
            }
        } catch (e: IndexOutOfBoundsException) {
            e.printStackTrace()
        }


        // Prepare Camera Controller
        val cameraController = remember {
            viewModel.prepareCameraController(
                context,
                cameraFrameAnalyzer
            )
        }

        // Combined Column for Camera Preview & Bottom UI
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = colorResource(id = R.color.gray_900)),
        ) {
            // Camera Preview Column
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.8f)
            ) {
                CameraPreview(
                    controller =  remember {
                        cameraController
                    },
                    modifier = Modifier.fillMaxSize(),
                    onPreviewSizeChanged = { newSize ->
                        previewSizeState.value = newSize
                    }
                )
            }

            // Bottom column with Capture-Image and Threshold Level Slider
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.2f)
                    .padding(top = Dimens.Padding8dp),
                verticalArrangement = Arrangement.SpaceAround
            ) {
                ImageButton(
                    drawableResourceId = R.drawable.ic_capture,
                    contentDescriptionResourceId = R.string.capture_button_description,
                    modifier = Modifier
                        .size(Dimens.CaptureButtonSize)
                        .clip(CircleShape)
                        .align(Alignment.CenterHorizontally)
                        .clickable {
                            // Capture and Saves Photo
                            viewModel.capturePhoto(
                                context = context,
                                cameraController = cameraController
                            )

                            // Show toast of Save State
                            if (isImageSavedStateFlow) {
                                Toast
                                    .makeText(
                                        context,
                                        R.string.success_image_saved_message,
                                        Toast.LENGTH_SHORT
                                    )
                                    .show()
                            } else {
                                Toast
                                    .makeText(
                                        context,
                                        R.string.error_image_saved_message,
                                        Toast.LENGTH_SHORT
                                    )
                                    .show()
                            }
                        }
                )

                // Threshold Level Slider
                val sliderValue = remember { mutableFloatStateOf(Constants.INITIAL_CONFIDENCE_SCORE) }
                ThresholdLevelSlider(sliderValue) { sliderValue ->
                    confidenceScoreState.floatValue = sliderValue
                }
            }
        }

        // Column with rotate-camera and detected object count Composable (Overlapping UI)
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .zIndex(1f)
                .padding(top = Dimens.Padding32dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                // Rotate Camera Composable
                ImageButton(
                    drawableResourceId = R.drawable.ic_rotate_camera,
                    contentDescriptionResourceId = R.string.rotate_camera_button_description,
                    Modifier
                        .padding(
                            top = Dimens.Padding24dp,
                            start = Dimens.Padding16dp
                        )
                        .size(Dimens.RotateCameraButtonSize)
                        .clickable {
                            cameraController.cameraSelector =
                                viewModel.getSelectedCamera(cameraController)
                        }
                )

                // Detected Object Count Composable
                ObjectCounter(objectCount = detections.size)
            }
        }
    }
}