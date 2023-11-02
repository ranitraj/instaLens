package com.example.instalens.utils

object Constants {
    // Preference Datastore related
    const val USER_CONFIG_DATASTORE_NAME = "userConfigDatastore"
    const val USER_CONFIG = "userConfig"

    // Values
    const val INITIAL_CONFIDENCE_SCORE = 0.75f
    const val MODEL_INPUT_IMAGE_WIDTH = 384
    const val MODEL_INPUT_IMAGE_HEIGHT = 384

    // TensorFlow Lite
    const val MODEL_MAX_RESULTS_COUNT = 10
    const val MODEL_PATH = "efficientdet-lite1.tflite"
}