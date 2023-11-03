package com.example.instalens.utils

/**
 * Holds the application-wide constants used throughout the app for consistent reference.
 * This includes identifiers for Preference Datastore settings, initial values for configuration
 * settings like confidence score, dimensions for model input and original image sizes, and
 * TensorFlow Lite model details.
 */
object Constants {
    // Preference Datastore related
    const val USER_CONFIG_DATASTORE_NAME = "userConfigDatastore"
    const val USER_CONFIG = "userConfig"

    // Values
    const val INITIAL_CONFIDENCE_SCORE = 0.5f
    const val MODEL_INPUT_IMAGE_WIDTH = 384
    const val MODEL_INPUT_IMAGE_HEIGHT = 384
    const val ORIGINAL_IMAGE_WIDTH = 480f
    const val ORIGINAL_IMAGE_HEIGHT = 640f

    // TensorFlow Lite
    const val MODEL_MAX_RESULTS_COUNT = 10
    const val MODEL_PATH = "efficientdet-lite1.tflite"
}